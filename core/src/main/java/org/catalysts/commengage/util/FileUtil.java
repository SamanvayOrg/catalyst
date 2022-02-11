package org.catalysts.commengage.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {
    public static String readFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileUtil().getClass().getResourceAsStream(fileName)));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append(System.getProperty("line.separator"));
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
