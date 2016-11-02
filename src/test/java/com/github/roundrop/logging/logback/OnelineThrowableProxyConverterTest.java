package com.github.roundrop.logging.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.CoreConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class OnelineThrowableProxyConverterTest {

    private final OnelineThrowableProxyConverter converter = new OnelineThrowableProxyConverter();

    private final LoggingEvent event = new LoggingEvent();

    @Before
    public void setUp() throws Exception {
        converter.start();
    }

    @After
    public void tearDown() throws Exception {
        converter.stop();
    }

    @Test
    public void noStackTrace() throws Exception {
        String s = converter.convert(event);
        assertThat(s, is(""));
    }

    @Test
    public void withStackTrace() throws Exception {
        event.setThrowableProxy(new ThrowableProxy(new RuntimeException()));
        String s = converter.convert(event);
        assertThat(s, startsWith("java.lang.RuntimeException: null\\n    at com."));
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
        ruleRegistry.put("ex1L", OnelineThrowableProxyConverter.class.getCanonicalName());

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{yyyy/MM/dd HH:mm:ss:SSS}\\t%-5level\\t%msg\\t%ex1L");
        encoder.start();

        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<ILoggingEvent>();
        appender.setContext(context);
        appender.setEncoder(encoder);
        appender.start();
        logger.addAppender(appender);

        logger.error("error", new RuntimeException("foo"));
    }

}