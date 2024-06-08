package com.cocus.fileuploader.service;

import com.cocus.fileuploader.model.FileEntity;
import com.cocus.fileuploader.repository.FileRepository;
import com.cocus.fileuploader.util.LineResponse;
import com.cocus.fileuploader.util.LineResponseXML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;


    public FileEntity saveFile(String fileName, List<String> lines) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        String content = String.join("\n", lines); // Juntar as linhas com delimitador de nova linha
        fileEntity.setContent(content);
        return fileRepository.save(fileEntity);
    }

    public Object getRandomLine(Long fileId, String acceptHeader) {
        Optional<FileEntity> optionalFile = fileRepository.findById(fileId);
        if (optionalFile.isPresent()) {
            String[] lines = optionalFile.get().getContent().split("\n");
            Random random = new Random();
            String randomLine = lines[random.nextInt(lines.length)];

            if (MediaType.APPLICATION_JSON_VALUE.equals(acceptHeader)) {
                return new LineResponse(randomLine);
            } else if (MediaType.APPLICATION_XML_VALUE.equals(acceptHeader)) {
                return new LineResponseXML(randomLine);
            } else {
                return randomLine;
            }
        }
        return null;
    }


}
