package com.server.Dotori.domain.board.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Service {

    List<String> uploadFile(List<MultipartFile> multipartFile);

    void deleteFile(String fileName);
}
