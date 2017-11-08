package com.nju.onlineexam.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class ExcelHelper {

    /**
     * 将excel按行转为vo
     * @param iStream   excel的inputstream,调用期间不会关闭
     * @param fileName  excel的文件名,用来判断是xlsx或xls
     * @param startRow  转化的起始行号,0-base
     * @param mapper    转化回调
     * @return  转化后的vo列表
     */
    public <T> List<T> convertToVo(InputStream iStream, String fileName, int startRow , Function<List<String>, T> mapper){
        Workbook workBook = null;

        try{
            // 读取2007版，以.xlsx 结尾
            if(fileName.endsWith("xlsx")){
                workBook = new XSSFWorkbook(iStream);
            }
            // 读取2003版，以.xls 结尾
            else {
                workBook = new HSSFWorkbook(iStream);
            }
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

        if(workBook.getNumberOfSheets()<1){
            throw new RuntimeException("no sheet in excel");
        }

        Sheet sheet = workBook.getSheetAt(0);
        int rowNum = sheet.getPhysicalNumberOfRows();
        if( rowNum <= startRow ){
            throw new RuntimeException("total_row_num="+rowNum+", but start_row="+startRow);
        }

        List<T> voList = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        for(int i = startRow; i<rowNum; i++ ){
            Row row = sheet.getRow(i);
            List<String> cellValues = new ArrayList<>();

            for(int j = 0; j < row.getLastCellNum(); j++){
                Cell cell = row.getCell(j);
                cellValues.add(formatter.formatCellValue(cell));
            }

            voList.add(mapper.apply(cellValues));
        }

        return voList;
    }

    /**
     * 将voList转为excel行
     * @param workBook  带表头的excel模板
     * @param startRow  起始行号
     * @param voList    vo列表
     * @param rowConsumer  将vo转化为行的回调. 更灵活,可以控制cell的样式或者类型.
     */
    public <T> void convertToExcel(XSSFWorkbook workBook, int startRow, List<T> voList, BiConsumer<T,Row> rowConsumer){

        if(startRow < 0){
            throw new RuntimeException("stratRow < 0");
        }
        if(workBook.getNumberOfSheets()<1){
            throw new RuntimeException("no sheet in excel");
        }

        Sheet sheet = workBook.getSheetAt(0);

        for( int i=0; i<voList.size(); i++ ){

            Row row = sheet.createRow(i+startRow);
            rowConsumer.accept(voList.get(i),row);

        }

    }

    /**
     * 将voList转为excel行
     * @param workBook  带表头的excel模板
     * @param startRow  起始行号
     * @param voList    vo列表
     * @param mapper    将vo转化为字符串列表. 方法将这个列表转化为一行
     */
    public <T> void convertToExcel(XSSFWorkbook workBook, int startRow, List<T> voList, Function<T,List<String>> mapper) {

        convertToExcel(workBook,startRow,voList, (vo,row)->{

            List<String> cellValueList = mapper.apply(vo);

            for( int i=0 ; i< cellValueList.size() ; i++ ){
                Cell cell = row.createCell(i);
                cell.setCellValue(cellValueList.get(i));
            }

        });

    }
}
