package com.nju.onlineexam.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHelper {

    private static final String uploadRootDir = "upload";
    private static final String downloadRootDir = "download";

    public static synchronized void saveUploadFile(String fileName, InputStream iStream){

        File rootDir = new File(uploadRootDir);
        if( ! (rootDir.exists() && rootDir.isDirectory()) ){
            boolean result = rootDir.mkdir();
            if( ! result ){
                throw new RuntimeException("create dir fail");
            }
        }

        Path newFile = Paths.get(uploadRootDir,fileName);
        try {
            Files.copy(iStream,newFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("save file fail");
        }

    }

    public static Path openUploadFile(String fileName){
        return Paths.get(uploadRootDir,fileName);
    }

}
