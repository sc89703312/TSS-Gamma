package com.nju.onlineexam.util;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileHelper {

    private static final String uploadRootDir = "upload";
    private static final String downloadRootDir = "download";

    public static synchronized void saveUploadFile(String fileName, InputStream iStream){
        saveFileInner(uploadRootDir,fileName,iStream);
    }

    private static void saveFileInner(String dirName,String fileName, InputStream iStream){

        File rootDir = new File(dirName);
        if( ! (rootDir.exists() && rootDir.isDirectory()) ){
            boolean result = rootDir.mkdir();
            if( ! result ){
                throw new RuntimeException("create "+dirName+" dir fail");
            }
        }

        Path newFile = Paths.get(dirName,fileName);
        try {
            Files.copy(iStream,newFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("save file fail");
        }

    }

    public static synchronized void saveDownloadExcel(String fileName, Workbook workbook){
        File rootDir = new File(downloadRootDir);
        if( ! (rootDir.exists() && rootDir.isDirectory()) ){
            boolean result = rootDir.mkdir();
            if( ! result ){
                throw new RuntimeException("create "+downloadRootDir+" dir fail");
            }
        }

        File newFile = Paths.get(downloadRootDir,fileName).toFile();
        try {
            workbook.write(new FileOutputStream(newFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void compressExcels(String compressFileName, List<String> filenames){
        byte[] buffer = new byte[1024*20];

        try {
            File compressFile = Paths.get(downloadRootDir,compressFileName).toFile();
            FileOutputStream fos = new FileOutputStream(compressFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            zos.setMethod(ZipOutputStream.DEFLATED);

            for (String fileName : filenames) {

                System.out.println("File added: " + fileName);
                ZipEntry ze = new ZipEntry(fileName);
                zos.putNextEntry(ze);

                File serverFileName = Paths.get(downloadRootDir,fileName).toFile();
                FileInputStream in = new FileInputStream(serverFileName);

                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                in.close();
            }

            zos.closeEntry();

            zos.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Path openUploadFile(String fileName){
        return Paths.get(uploadRootDir,fileName);
    }
    public static Path openDownloadFile(String fileName){
        return Paths.get(downloadRootDir,fileName);
    }

}
