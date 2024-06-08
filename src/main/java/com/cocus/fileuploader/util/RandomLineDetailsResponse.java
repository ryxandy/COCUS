package com.cocus.fileuploader.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RandomLineDetailsResponse {
    private int lineNumber;
    private String fileName;
    private String line;
    private char mostFrequentChar;

}

