package com.cocus.fileuploader.controller;


import com.cocus.fileuploader.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/random-line/{fileId}", produces = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Object getRandomLine(@PathVariable Long fileId, @RequestHeader("Accept") String acceptHeader) {
        return fileService.getRandomLine(fileId, acceptHeader);
    }

    @GetMapping("/random-line-backwards")
    public String getRandomLineBackwards() {
        return fileService.getRandomLineBackwardsFromAnyFile();
    }

}
