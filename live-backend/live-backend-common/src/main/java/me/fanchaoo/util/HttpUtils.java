package me.fanchaoo.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Slf4j
public class HttpUtils {

    static {
        Unirest.setConcurrency(2000, 200);
    }

    public static byte[] getBytes(String url) {

        HttpResponse<InputStream> inputStreamHttpResponse = null;
        try {
            inputStreamHttpResponse = Unirest.get(url).asBinary();
            InputStream inputStream = inputStreamHttpResponse.getBody();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(inputStream, baos);

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getJSON(String url) {
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = Unirest.get(url).asString();
        } catch (UnirestException e) {
            log.error(e.getMessage(), e);
        }

        if (httpResponse != null && httpResponse.getStatus() == HttpStatus.SC_OK) {
            return httpResponse.getBody();
        }

        return null;
    }

    public static String postQuery(String url, String body) {
        HttpResponse<String> httpResponse;
        int count = 0;
        while (count < 3) {
            count++;
            try {
                httpResponse = Unirest.post(url)
                        .header("content-type", "application/xml")
                        .body(body)
                        .asString();
                return httpResponse.getBody();
            } catch (UnirestException e) {
                log.error("Unirest http请求失败！", e);
            }

            Long sleepTime = (count - 1) * 2000 + 1000L;
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                log.error("重试异常", e);
            }
        }

        return null;
    }


}
