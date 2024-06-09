package com.cocus.fileuploader.util;

import java.util.HashMap;
import java.util.Map;

 public class Operations {

    public static char getMostFrequentChar(String line) {
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
