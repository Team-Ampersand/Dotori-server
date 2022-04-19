package com.server.Dotori.domain.board.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    String uploadFile(MultipartFile multipartFile);

    void deleteFile(String fileName);
}
