package me.fanchaoo.constant;

import java.util.regex.Pattern;

public class HtmlTemplateConstant {

    public static final String AT_TEMPLATE = "<text data-type='USER' style='color: #577C9F'>%s</text>";

    public static final Pattern AT_PATTERN = Pattern.compile("(@[\\u4e00-\\u9fa5_a-zA-Z0-9]+)");

    public static final String TOPIC_TEMPLATE = "<text data-type='TOPIC' style='color: #577C9F'>%s</text>";

    public static final Pattern TOPIC_PATTERN = Pattern.compile("(#[\\u4e00-\\u9fa5_a-zA-Z0-9]+#)");

}
