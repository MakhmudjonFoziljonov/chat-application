package com.chat.userservice.utils;

import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Random;

public class BASE64DecodedMultipartFile implements MultipartFile {

    Random random = new Random();
    private final byte[] imgContent;
    private final String header;

    public BASE64DecodedMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    @Override
    public @NonNull String getName() {
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        return System.currentTimeMillis() + (random.nextInt() * 10000) + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() {
        return imgContent;
    }

    @Override
    public @NonNull InputStream getInputStream() {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(@NonNull File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }

    public static MultipartFile base64ToMultipart(String base64) {
        String[] baseStr = base64.split(",");

        byte[] b = java.util.Base64.getDecoder().decode(baseStr[1]);

        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += (byte) 256;
            }
        }
        return new BASE64DecodedMultipartFile(b, baseStr[0]);
    }
}