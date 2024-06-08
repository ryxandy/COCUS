package com.cocus.fileuploader.service;

import com.cocus.fileuploader.model.FileEntity;
import com.cocus.fileuploader.repository.FileRepository;
import com.cocus.fileuploader.util.RandomLineDetailsResponse;
import com.cocus.fileuploader.util.SimpleLineResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Optional<FileEntity> file = fileRepository.findById(fileId);
        if (file.isPresent()) {
            List<String> lines = Arrays.asList(file.get().getContent().split("\n"));
            Random random = new Random();
            int index = random.nextInt(lines.size());
            String randomLine = lines.get(index);

            if (acceptHeader != null && acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
                // Retorna a linha em um formato JSON adequado
                return new SimpleLineResponse(randomLine);
            } else if (acceptHeader != null && acceptHeader.startsWith("application/")) {
                // Retorna detalhes completos para outros tipos dentro de application/*
                char mostFrequentChar = getMostFrequentChar(randomLine);
                return new RandomLineDetailsResponse(index + 1, file.get().getFileName(), randomLine, mostFrequentChar);
            } else {
                // Retorna a linha como texto plano para outros cabeçalhos ou falta de cabeçalho
                return randomLine;
            }
        }
        return null;
    }

    private char getMostFrequentChar(String line) {
        Map<Character, Integer> counts = new HashMap<>();
        for (char c : line.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        int max = 0;
        char result = ' ';
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                result = entry.getKey();
            }
        }
        return result;
    }

}
