package com.cocus.fileuploader.service;

import com.cocus.fileuploader.model.FileEntity;
import com.cocus.fileuploader.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
