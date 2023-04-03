package com.onix.hcmustour.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    FileProperties properties;

    @EventListener
    public void init(ApplicationReadyEvent event) {

        // initialize Firebase

        try {

            ClassPathResource serviceAccount = new ClassPathResource("serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .setStorageBucket(properties.getBucketName())
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (Exception ex) {

            ex.printStackTrace();

        }
    }

    public String getImageUrl(String name) {
        return String.format(properties.getImageUrl(), name);
    }

    public String save(MultipartFile file) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket();

        String name = generateFileName(file.getOriginalFilename());

        bucket.create(name, file.getBytes(), file.getContentType());

        return name;
    }

    public String save(BufferedImage bufferedImage, String originalFileName) throws IOException {

        byte[] bytes = getByteArrays(bufferedImage, getExtension(originalFileName));

        Bucket bucket = StorageClient.getInstance().bucket();

        String name = generateFileName(originalFileName);

        bucket.create(name, bytes);

        return name;
    }

    public void delete(String name) throws IOException {

        Bucket bucket = StorageClient.getInstance().bucket();

        if (StringUtils.isEmpty(name)) {
            throw new IOException("invalid file name");
        }

        Blob blob = bucket.get(name);

        if (blob == null) {
            throw new IOException("file not found");
        }

        blob.delete();
    }

    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }

    private String getFileName(String originalFileName) {
        if (originalFileName == null) {
            return null;
        } else {
            int extensionPos = originalFileName.lastIndexOf(46);
            return extensionPos == -1 ? originalFileName : originalFileName.substring(0, extensionPos);
        }
    }

    private String generateFileName(String originalFileName) {
        String fileName = getFileName(originalFileName);
        String fileExtension = getExtension(originalFileName);
        String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString();
        return String.format("%s_%s_%s.%s", fileName, formattedDateTime, uuid, fileExtension);
    }

    private byte[] getByteArrays(BufferedImage bufferedImage, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, format, baos);
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            baos.close();
        }
    }
}
