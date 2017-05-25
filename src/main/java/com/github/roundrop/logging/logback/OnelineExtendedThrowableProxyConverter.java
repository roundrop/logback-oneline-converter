package com.github.roundrop.logging.logback;

import ch.qos.logback.classic.pattern.ExtendedThrowableProxyConverter;
import ch.qos.logback.classic.spi.IThrowableProxy;

public class OnelineExtendedThrowableProxyConverter extends ExtendedThrowableProxyConverter {
    @Override
    protected String throwableProxyToString(IThrowableProxy tp) {
        final String string = super.throwableProxyToString(tp);
        return Oneliner.toOnline(string);
    }
}
