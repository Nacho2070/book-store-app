package com.Bookstore.book_store.web.service.impl;

import com.Bookstore.book_store.web.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service

public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalFilename.substring(originalFilename.indexOf('.')));
        String filePath = path + File.separator + fileName;
        File folder = new File(filePath);

        if(!folder.exists()) {
            folder.mkdir();
        }
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}
