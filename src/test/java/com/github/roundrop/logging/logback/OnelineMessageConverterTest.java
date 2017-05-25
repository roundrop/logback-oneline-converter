package com.github.roundrop.logging.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.CoreConstants;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;


public class OnelineMessageConverterTest {

    private final OnelineMessageConverter converter = new OnelineMessageConverter();

    private final LoggingEvent event = new LoggingEvent();

    @Test
    public void noLineFeed() throws Exception {
        event.setMessage("foo,bar, baz");
        String s = converter.convert(event);
        assertThat(s, is("foo,bar, baz"));
    }

    @Test
    public void withLF() throws Exception {
        event.setMessage("foo\nbar\nbaz");
        String s = converter.convert(event);
        assertThat(s, is("foo\\nbar\\nbaz"));
    }

    @Test
    public void withCRLF() throws Exception {
        event.setMessage("foo\r\nbar\r\nbaz");
        String s = converter.convert(event);
        assertThat(s, is("foo\\nbar\\nbaz"));
    }

    @Test
    public void withCR() throws Exception {
        event.setMessage("foo\rbar\rbaz");
        String s = converter.convert(event);
        assertThat(s, is("foo\\nbar\\nbaz"));
    }

    @Test
    public void log() throws Exception {
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        LoggerContext context = logger.getLoggerContext();
        context.reset();

        Map<String, String> ruleRegistry = (Map) context.getObject(CoreConstants.PATTERN_RULE_REGISTRY);
        if (ruleRegistry == null) {
            ruleRegistry = new HashMap<String, String>();
            context.putObject(CoreConstants.PATTERN_RULE_REGISTRY, ruleRegistry);
        }
        ruleRegistry.put("msg1L", OnelineMessageConverter.class.getCanonicalName());

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{yyyy/MM/dd HH:mm:ss:SSS}\\t%-5level\\t%msg1L");
        encoder.start();

        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<ILoggingEvent>();
        appender.setContext(context);
        appender.setEncoder(encoder);
        appender.start();
        logger.addAppender(appender);

        logger.info(
            "[ORM] SQL : SELECT\n" +
            "    t1.*\n" +
            "FROM\n" +
            "    tbl1 t1\n" +
            "INNER JOIN tbl2 t2 ON t1.xxx_id = t2.xxx_id\n" +
            "WHERE\n" +
            "    t1.xxx_id in (1, 2, 3)\n" +
            "AND\n" +
            "    t2.xxx_code in (11, 12)\n" +
            "ORDER BY\n" +
            "    xxx_id");
    }

}