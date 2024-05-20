package org.unibl.etf.ip.webshop.util;

import org.springframework.context.annotation.Bean;

import java.util.Base64;


public class ImageUtils {

    private static final String stringPrefix = "data:image/jpeg;base64,";
    public static byte[] decodeBase64Image(String base64Image) {

        String base64ImageWithoutPrefix = base64Image;
        if (base64Image.contains(",")) {
            base64ImageWithoutPrefix = base64Image.substring(base64Image.indexOf(",") + 1);
        }

        byte[] imageBytes = Base64.getDecoder().decode(base64ImageWithoutPrefix);
        return imageBytes;
    }

    public static String codeToString(byte[] bytes){
        return stringPrefix + Base64.getEncoder().encodeToString(bytes);
    }
}
