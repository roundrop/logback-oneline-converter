package com.github.roundrop.logging.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class OnelineMessageConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        final String message = event.getFormattedMessage();
        return Oneliner.toOnline(message);
    }
}
