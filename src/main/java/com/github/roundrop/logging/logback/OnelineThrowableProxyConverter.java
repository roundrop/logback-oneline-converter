package com.github.roundrop.logging.logback;

import ch.qos.logback.classic.pattern.ThrowableProxyConverter;
import ch.qos.logback.classic.spi.IThrowableProxy;

public class OnelineThrowableProxyConverter extends ThrowableProxyConverter {
    @Override
    protected String throwableProxyToString(IThrowableProxy tp) {
        final String string = super.throwableProxyToString(tp);
        return Oneliner.toOnline(string);
    }
}
