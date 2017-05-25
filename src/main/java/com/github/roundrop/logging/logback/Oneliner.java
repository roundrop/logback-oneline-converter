package com.github.roundrop.logging.logback;

import java.util.regex.Pattern;

public class Oneliner {
    private static Pattern sp = Pattern.compile("\r\n|[\n\r]");
    private static Pattern tb = Pattern.compile("\t", Pattern.LITERAL);

    public static String toOnline(String src) {
        return tb.matcher(sp.matcher(src).replaceAll("\\\\n")).replaceAll("    ");
    }
}
