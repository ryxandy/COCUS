package com.cocus.fileuploader.service;

import com.cocus.fileuploader.model.FileEntity;
import com.cocus.fileuploader.repository.FileRepository;
import com.cocus.fileuploader.util.Operations;
import com.cocus.fileuploader.util.RandomLineDetailsResponse;
import com.cocus.fileuploader.util.SimpleLineResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;


    public void saveFile(String fileName, List<String> lines) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        String content = String.join("\n", lines); // Juntar as linhas com delimitador de nova linha
        fileEntity.setContent(content);
        fileRepository.save(fileEntity);
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
                char mostFrequentChar = Operations.getMostFrequentChar(randomLine);
                return new RandomLineDetailsResponse(index + 1, file.get().getFileName(), randomLine, mostFrequentChar);
            } else {
                return randomLine;
            }
        }
        return null;
    }

    public String getRandomLineBackwardsFromAnyFile() {
        List<FileEntity> files = fileRepository.findAll();
        if (files.isEmpty()) {
            return "No files available";
        }
        Random random = new Random();
        // Selecionar um arquivo aleatoriamente de todos os arquivos disponíveis
        FileEntity randomFile = files.get(random.nextInt(files.size()));
        // Quebrar o conteúdo do arquivo em linhas
        List<String> lines = Arrays.asList(randomFile.getContent().split("\n"));
        if (lines.isEmpty()) {
            return "The file is empty";
        }
        // Escolher uma linha aleatória
        String randomLine = lines.get(random.nextInt(lines.size()));
        // Retornar a linha ao contrário
        return new StringBuilder(randomLine).reverse().toString();
    }

    public List<String> getLongest100Lines() {
        return fileRepository.findAll().stream()
                .flatMap(file -> Arrays.stream(file.getContent().split("\n")))
                .filter(line -> line != null && !line.isEmpty()) // Filtrar linhas vazias
                .sorted((line1, line2) -> Integer.compare(line2.length(), line1.length())) // Ordenar por comprimento em ordem decrescente
                .limit(100) // Limitar a 100 linhas
                .collect(Collectors.toList());
    }

    public List<String> get20LongestLinesOfFile(boolean chooseLatest) {
        FileEntity file;
        if (chooseLatest) {
            // Seleciona o arquivo mais recente
            file = fileRepository.findTopByOrderByCreatedAtDesc();
        } else {
            // Seleciona um arquivo aleatoriamente
            List<FileEntity> files = fileRepository.findAll();
            file = files.isEmpty() ? null : files.get((int) (Math.random() * files.size()));
        }

        if (file == null) {
            return List.of("No file available");
        }

        return Arrays.stream(file.getContent().split("\n"))
                .filter(line -> line != null && !line.isEmpty())
                .sorted(Comparator.comparingInt(String::length).reversed())
                .limit(20)
                .collect(Collectors.toList());
    }

}
