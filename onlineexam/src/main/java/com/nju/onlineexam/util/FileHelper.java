package com.nju.onlineexam.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHelper {

    private static final String fileRootDir = "upload";

    public static synchronized void saveFile(String fileName, InputStream iStream){

        File rootDir = new File(fileRootDir);
        if( ! (rootDir.exists() && rootDir.isDirectory()) ){
            boolean result = rootDir.mkdir();
            if( ! result ){
                throw new RuntimeException("create dir fail");
            }
        }

        Path newFile = Paths.get(fileRootDir,fileName);
        try {
            Files.copy(iStream,newFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("save file fail");
        }

    }

    public static Path openFile(String fileName){
        return Paths.get(fileRootDir,fileName);
    }

}
