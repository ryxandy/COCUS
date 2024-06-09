package com.cocus.fileuploader.service;

import com.cocus.fileuploader.model.FileEntity;
import com.cocus.fileuploader.repository.FileRepository;
import com.cocus.fileuploader.util.RandomLineDetailsResponse;
import com.cocus.fileuploader.util.SimpleLineResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileService fileService;

    private FileEntity fileEntity;

    @BeforeEach
    public void setUp() {
        fileEntity = new FileEntity();
        fileEntity.setId(1L);
        fileEntity.setFileName("test.txt");
        fileEntity.setContent("Line1\nLine2\nLine3\nLine4\nLine5");
        fileEntity.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void testSaveFile() {
        when(fileRepository.save(any(FileEntity.class))).thenReturn(fileEntity);

        FileEntity savedFile = fileService.saveFile("test.txt", Arrays.asList("Line1", "Line2", "Line3", "Line4", "Line5"));

        assertNotNull(savedFile);
        assertEquals("test.txt", savedFile.getFileName());
        assertEquals("Line1\nLine2\nLine3\nLine4\nLine5", savedFile.getContent());
        verify(fileRepository, times(1)).save(any(FileEntity.class));
    }

    @Test
    public void testGetRandomLine() {
        when(fileRepository.findById(1L)).thenReturn(Optional.of(fileEntity));

        Object randomLine = fileService.getRandomLine(1L, "text/plain");
        assertNotNull(randomLine);
        assertTrue(randomLine instanceof String);
    }

    @Test
    public void testGetRandomLineJson() {
        when(fileRepository.findById(1L)).thenReturn(Optional.of(fileEntity));

        Object randomLine = fileService.getRandomLine(1L, "application/json");
        assertNotNull(randomLine);
        assertTrue(randomLine instanceof SimpleLineResponse);
    }

    @Test
    public void testGetRandomLineXml() {
        when(fileRepository.findById(1L)).thenReturn(Optional.of(fileEntity));

        Object randomLine = fileService.getRandomLine(1L, "application/xml");
        assertNotNull(randomLine);
        assertTrue(randomLine instanceof RandomLineDetailsResponse);
    }

    @Test
    public void testGetRandomLineBackwards() {
        String randomLineBackwards = fileService.getRandomLineBackwardsFromAnyFile();
        assertNotNull(randomLineBackwards);
        assertTrue(true);
    }

    @Test
    public void testGetLongest100Lines() {
        when(fileRepository.findAll()).thenReturn(Collections.singletonList(fileEntity));

        List<String> longest100Lines = fileService.getLongest100Lines();
        assertNotNull(longest100Lines);
        assertEquals(5, longest100Lines.size());
    }

    @Test
    public void testGet20LongestLinesOfFileChooseLatest() {
        when(fileRepository.findTopByOrderByCreatedAtDesc()).thenReturn(fileEntity);

        List<String> longest20Lines = fileService.get20LongestLinesOfFile(true);
        assertNotNull(longest20Lines);
        assertEquals(5, longest20Lines.size());
    }

    @Test
    public void testGet20LongestLinesOfFileChooseRandom() {
        when(fileRepository.findAll()).thenReturn(Collections.singletonList(fileEntity));

        List<String> longest20Lines = fileService.get20LongestLinesOfFile(false);
        assertNotNull(longest20Lines);
        assertEquals(5, longest20Lines.size());
    }

    @Test
    public void testGetRandomLineBackwardsFromAnyFile() {
        when(fileRepository.findAll()).thenReturn(Collections.singletonList(fileEntity));

        String randomLineBackwards = fileService.getRandomLineBackwardsFromAnyFile();
        assertNotNull(randomLineBackwards);
        assertTrue(true);
    }
}
