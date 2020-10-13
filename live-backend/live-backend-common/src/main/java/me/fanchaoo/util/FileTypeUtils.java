package me.fanchaoo.util;

import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.enums.EnumFileType;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FileTypeUtils {

    public static boolean checkFileType(InputStream inputStream, String... specifiedTypes) throws IOException {

        boolean valid = false;
        byte[] buffer = new byte[10];
        inputStream.read(buffer);

        // 获取当前文件的真实类型
        String currentFileType = getTrueFileType(bytesToHexFileTypeString(buffer));
        log.info("文件类型：" + currentFileType);

        for (String specifiedType : specifiedTypes) {
            // 指定文件类型中是否匹配当前文件类型
            if (specifiedType.equalsIgnoreCase(currentFileType)) {
                valid = true;
                return valid;
            }
        }

        return valid;
    }

    private static String getTrueFileType(String s) {
        for (EnumFileType fileTypeEnum : EnumFileType.values()) {
            if (s.startsWith(fileTypeEnum.getValue())) {
                return fileTypeEnum.toString();
            }
        }
        return null;
    }

    private static String bytesToHexFileTypeString(byte[] buffer) {
        StringBuilder hexFileTypeStr = new StringBuilder();
        for (byte b : buffer) {
            String hexString = Integer.toHexString(b & 0xFF);
            if (hexString.length() < 2) {
                hexFileTypeStr.append("0");
            }
            hexFileTypeStr.append(hexString);
        }
        return hexFileTypeStr.toString();
    }

    public static String getSuffix(String fileName) {

        if (StringUtils.isBlank(fileName)) {
            return "";
        }

        int index = fileName.lastIndexOf(".");
        if (index < 0) {
            return "";
        }

        return fileName.substring(index);
    }

}
