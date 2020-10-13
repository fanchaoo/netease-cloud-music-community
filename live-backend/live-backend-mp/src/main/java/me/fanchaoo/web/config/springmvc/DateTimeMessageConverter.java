package me.fanchaoo.web.config.springmvc;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class DateTimeMessageConverter extends AbstractGenericHttpMessageConverter<DateTime> {


    public DateTimeMessageConverter() {
        super(new MediaType("application", "json"), new MediaType("*", "*"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return DateTime.class.isAssignableFrom(clazz);
    }

    @Override
    protected DateTime readInternal(Class<? extends DateTime> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String temp = StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);
        return dateFormat.parseDateTime(temp);
    }


    @Override
    protected void writeInternal(DateTime dateTime, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String print = dateFormat.print(dateTime);

        outputMessage.getBody().write(print.getBytes());
    }

    @Override
    public DateTime read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String temp = StreamUtils.copyToString(inputMessage.getBody(), StandardCharsets.UTF_8);
        return dateFormat.parseDateTime(temp);
    }
}