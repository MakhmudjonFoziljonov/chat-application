package com.chat.userservice.service.sso;

import com.chat.userservice.entity.sso.FileEntity;
import com.chat.userservice.exps.RecordBadRequestException;
import com.chat.userservice.repository.FileInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Base64;

import static com.chat.userservice.utils.BASE64DecodedMultipartFile.base64ToMultipart;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileInfoServiceImpl implements FileInfoService {

    @Value("${filepath}")
    private String filePath;

    private final FileInfoRepository repository;


    public String getExtension(String base64) {
        String pngMagicNumber = "89504E470D0A1A0A";
        String jpegMagicNumber = "FFD8FF";

        String magicNumber = getMagicNumber(base64);

        if (magicNumber.startsWith(pngMagicNumber)) {
            return "png";
        } else if (magicNumber.startsWith(jpegMagicNumber)) {
            return "jpeg";
        }
        return "unknown";
    }

    public static String getMagicNumber(String base64String) {
        byte[] binaryData = Base64.getDecoder().decode(base64String.split(",")[0]);

        StringBuilder magicNumber = new StringBuilder();
        int length = Math.min(binaryData.length, 8);
        for (int i = 0; i < length; i++) {
            magicNumber.append(String.format("%02X", binaryData[i]));
        }

        return magicNumber.toString();
    }

    @SneakyThrows
    public void saveFile(String file, String path, String fileName) {
        MultipartFile multipartFile = base64ToMultipart(file);
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originalFilename);
        String modifiedName = getModifiedName(extension);
        String pathName = getPathName("base/" + path);
        File storeFile = getFile(modifiedName, pathName);
        if (storeFile != null) {
            FileUtils.writeByteArrayToFile(storeFile, multipartFile.getBytes());
        }
        FileEntity info = new FileEntity();
        info.setName(fileName + "." + extension);
        info.setExtension(extension);
        info.setPath(pathName);
        info.setModifiedName(modifiedName);
        info.setUrl("uploads" + File.separator + pathName + modifiedName);
        if (storeFile != null) {
            info.setAbsolutePath(storeFile.getAbsolutePath());
        }
        info.setSize(multipartFile.getSize());
        repository.save(info);
    }

    @SneakyThrows
    public FileEntity downloadFile(String url, String path, String fileName) {
        FileEntity info;

        URI uri = new URI(url);
        URL website = uri.toURL();
        try (InputStream in = website.openStream()) {
            String pathName = getPathName("downloads/" + path);
            String extension = FilenameUtils.getExtension(website.getPath());
            String modifiedName = getModifiedName(extension);
            File storeFile = getFile(modifiedName, pathName);
            if (storeFile != null) {
                Files.copy(in, Paths.get(getFile(modifiedName, pathName).getPath()), StandardCopyOption.REPLACE_EXISTING);
            }

            info = new FileEntity();
            info.setExtension(FilenameUtils.getExtension(website.getPath()));
            info.setName(fileName + "." + extension);
            info.setModifiedName(modifiedName);
            info.setUrl("uploads" + File.separator + pathName + modifiedName);
            info.setPath(pathName);
            if (storeFile != null) {
                info.setSize(storeFile.length());
                info.setAbsolutePath(storeFile.getAbsolutePath());
            }
            repository.save(info);
        } catch (IOException e) {
            log.error("Error while saving file! \nError: {}", e.getMessage());
            throw new RecordBadRequestException("Fayl saqlashda xatolik! \nXatolik: " + e.getMessage());
        }
        return info;
    }

    public static String getModifiedName(String extension) {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(64, 90).withinRange(97, 122).build();
        return pwdGenerator.generate(7) + System.currentTimeMillis() + "." + extension;
    }

    public static String getPathName(String path) {
        LocalDate date = LocalDate.now();
        String separator = File.separator;
        return date.getYear() + separator
                + date.getMonthValue() + separator
                + date.getDayOfMonth() + separator
                + path + separator;
    }

    public File getFile(String name, String path) {
        String separator = File.separator;
        boolean exists = new File(filePath + path + separator).exists();
        if (!exists) {
            new File(filePath + path + separator).mkdirs();
        }
        String modifiedFilePath = filePath + path + separator + name;
        return new File(modifiedFilePath);
    }

    @SneakyThrows
    public String saveFromBase64(String file, String path, String fileName) {
        String extension = getExtension(file);
        String modifiedName = getModifiedName(extension);
        String pathName = getPathName("base/" + path);
        File storeFile = getFile(modifiedName, pathName);
        if (storeFile != null) {
            String base64Data = file.contains(",") ? file.split(",")[1] : file;

            byte[] binaryData = Base64.getDecoder().decode(base64Data);

            try (FileOutputStream fos = new FileOutputStream(storeFile)) {
                fos.write(binaryData);
            }
        }
        FileEntity info = new FileEntity();
        info.setName(fileName + "." + extension);
        info.setExtension(extension);
        info.setPath(pathName);
        info.setModifiedName(modifiedName);
        info.setUrl("uploads" + File.separator + pathName + modifiedName);
        if (storeFile != null) {
            info.setAbsolutePath(storeFile.getAbsolutePath());
            info.setSize(storeFile.length());
        }

        repository.save(info);

        return info.getUrl();
    }
}
