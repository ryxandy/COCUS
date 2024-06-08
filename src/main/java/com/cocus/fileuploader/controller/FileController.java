package com.cocus.fileuploader.controller;


import com.cocus.fileuploader.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        List<String> lines = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.toList());
        fileService.saveFile(file.getOriginalFilename(), lines);
        return "File uploaded successfully: " + file.getOriginalFilename();
    }
}
