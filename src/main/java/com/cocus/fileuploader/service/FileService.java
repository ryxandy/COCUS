package com.cocus.fileuploader.service;

import com.cocus.fileuploader.model.FileEntity;
import com.cocus.fileuploader.repository.FileRepository;
import com.cocus.fileuploader.util.RandomLineDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        Optional<FileEntity> file = fileRepository.findById(fileId);
        if (file.isPresent()) {
            List<String> lines = Arrays.asList(file.get().getContent().split("\n"));
            Random random = new Random();
            int index = random.nextInt(lines.size());
            String randomLine = lines.get(index);
            char mostFrequentChar = getMostFrequentChar(randomLine);

            if (acceptHeader != null && acceptHeader.startsWith("application/")) {
                return new RandomLineDetailsResponse(index + 1, file.get().getFileName(), randomLine, mostFrequentChar);
            } else {
                return randomLine;
            }
        }
        return null;
    }

    private char getMostFrequentChar(String line) {
        int[] counts = new int[256]; // Basic ASCII character set
        for (char c : line.toCharArray()) {
            counts[c]++;
        }
        int max = 0;
        char result = ' ';
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > max) {
                max = counts[i];
                result = (char) i;
            }
        }
        return result;
    }
}
