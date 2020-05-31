package main.java.accountFile.utils;

import java.io.*;
import java.util.List;

public class FileManager {
    public static String read(File file) throws IOException {
        StringBuilder stringBuilder=new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
             stringBuilder.append(currentLine);
        }
        reader.close();
        return stringBuilder.toString();
    }
    public static void write(List<?> list, File file) throws IOException {
        StringBuilder stringBuilder=new StringBuilder();
        for (Object o : list) {
            stringBuilder.append(o);
        }
        String str=stringBuilder.toString();
        BufferedWriter writer=new BufferedWriter(new FileWriter(file));
        writer.write(str);
        writer.close();
        
        
    }
}
