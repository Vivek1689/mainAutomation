package utils.excelUtil;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.util.Date;

public class ExcelUtils {

    public static void main(String [] arg){
        Object[][] bookData = {
                {"Java", "Kathy", 90}
        };

//        createExcel(bookData,"TestFile",System.getProperty("user.dir"),"xls");
//        updateExcel(System.getProperty("user.dir")+"/TestFile.xls",0,0,bookData);
//        System.out.println(getRowCount(System.getProperty("user.dir")+"/TestFile.xls"));
//        System.out.println(colcount(System.getProperty("user.dir")+"/TestFile.xls","TestFile",0));
//        writeEditExcelCell(System.getProperty("user.dir")+"/TestFile.xls","TestFile",1,1,"Shadab");
//        writeEditExcelCell(System.getProperty("user.dir")+"/TestFile.xls","TestFile",8,3,"Shadab");
//        createSheet(System.getProperty("user.dir")+"/TestFile.xls","Shadab");
//        System.out.println(getCellData(System.getProperty("user.dir")+"/TestFile.xls","TestFile",1,1));
//        System.out.println(getCellData(System.getProperty("user.dir")+"/TestFile.xls","Taran",1,1));
//        System.out.println(getLastRow(System.getProperty("user.dir")+"/TestFile.xls","Taran"));
//        System.out.println(getLastRow(System.getProperty("user.dir")+"/TestFile.xls","TestFile"));
//        System.out.println(getRow(System.getProperty("user.dir")+"/TestFile.xls","TestFile",1).getCell(2));
    }

    static int rc, cc;

    public static void createExcel( Object[][] data,String fileName,String filePath,String fileType){
        Workbook workbook =null;
        if(fileType.equalsIgnoreCase("xls"))
             workbook=new HSSFWorkbook();
        else if(fileType.equalsIgnoreCase("xlsx"))
             workbook= new XSSFWorkbook();

        Sheet sheet = workbook.createSheet(fileName);

        int rowCount = 0;

        for (Object[] object : data) {
            Row row = sheet.createRow(rowCount++);

            int columnCount = 0;

            for (Object field : object) {
                Cell cell = row.createCell(columnCount++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }

        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath+"/"+fileName+"."+fileType.toLowerCase())) {
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Sheet createSheet(String excelFilePath,String sheetName){
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            if(sheet==null)
                sheet= workbook.createSheet(sheetName);
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
                workbook.write(outputStream);

            return sheet;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * update the existing data into excel
     *
     * @param excelFilePath
     * @param rowCount
     * @param columnCount
     * @param data
     */
    public static void updateExcel(String excelFilePath, int rowCount, int columnCount, Object[][] data) {
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (Object[] aBook : data) {
                int colNumber = columnCount;
                Row row = sheet.getRow(rowCount);
                Cell cell = row.getCell(colNumber);
                cell.setCellValue(rowCount);
                for (Object field : aBook) {
                    cell = row.getCell(colNumber);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    } else if (field instanceof Date) {
                        cell.setCellValue((Date) field);
                    }
                    colNumber++;
                }
                rowCount++;
            }
            inputStream.close();

            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            outputStream.close();
            System.out.println("written successfully");
        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get no.of rows on worksheet of excelsheet
     *
     * @param excelFilePath : excel file  path
     * @return
     */
    public static int getRowCount(String excelFilePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook=null;
            if(excelFilePath.contains("xls"))
                workbook = new HSSFWorkbook(fileInputStream);
            else if (excelFilePath.contains("xlsx")) {
                workbook = new XSSFWorkbook(fileInputStream);
            }

            Sheet worksheet = workbook.getSheetAt(0);
            rc = worksheet.getLastRowNum();
            // fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rc+1;
    }

    /**
     * // TO get no. of columns in a row
     *
     * @param filePath  : workbook name to be select
     * @param sheetName  : workbook sheet name to be select
     * @param rowNo : workbook sheet row no to be select
     * @return
     */
    static int colcount(String filePath, String sheetName, int rowNo) {
        try {

            FileInputStream fileInputStream = new FileInputStream(filePath);
            Workbook workbook=null;
            if(filePath.contains("xls"))
                workbook = new HSSFWorkbook(fileInputStream);
            else if (filePath.contains("xlsx")) {
                workbook = new XSSFWorkbook(fileInputStream);
            }

            Sheet worksheet = workbook.getSheetAt(0);
            Row row1 = worksheet.getRow(rowNo);

            cc = row1.getPhysicalNumberOfCells();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cc;

    }


    /**
     * Write data to an existing xlsx workbook.
     *
     * @param excelFilePath  : workbook name to be edit
     * @param sheetName  : workbook sheet name to be edit
     * @param rowNo : workbook row no to be edit
     * @param colNo   : workbook column no to be edit
     * @param str     : string to be edit
     * @throws
     */
    public static void writeEditExcelCell(String excelFilePath, String sheetName, int rowNo, int colNo, String str) {
        try {

            FileInputStream fileInputStream = new FileInputStream(excelFilePath);
            Workbook workbook=null;
            if(excelFilePath.contains("xls"))
                workbook = new HSSFWorkbook(fileInputStream);
            else if (excelFilePath.contains("xlsx")) {
                workbook = new XSSFWorkbook(fileInputStream);
            }

            Row row = null;
            Sheet worksheet = workbook.getSheet(sheetName);
            row = worksheet.getRow(rowNo);
            if (row == null) {
                row = worksheet.createRow(rowNo);
            }
            Cell c1 = row.createCell(colNo);
            worksheet.setColumnWidth(colNo, 4200);
            c1.setCellValue(str);

            FileOutputStream fileOut = new FileOutputStream(excelFilePath);

            workbook.write(fileOut);
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCellData(String ExcelPath, String sheetName, int rowno, int colno) {

        Workbook workBook;
        Sheet sheet;
        FileInputStream fis;
        Cell cell;
        String CellData = null;

        try {
            fis = new FileInputStream(new File(ExcelPath));
            workBook = WorkbookFactory.create(fis);
            sheet = workBook.getSheet(sheetName);
            cell = sheet.getRow(rowno).getCell(colno);

            switch (cell.getCellType()) {
                case STRING:
                    CellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    CellData = Double.toString(cell.getNumericCellValue());
                    if (CellData.contains(".o")) {
                        CellData = CellData.substring(0, CellData.length() - 2);
                    }
                    break;
                case BOOLEAN:
                    CellData = String.valueOf(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    CellData = "";
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return CellData;
    }

    public static int getLastRow(String ExcelPath, String sheetName) {

        Workbook workBook;
        Sheet sheet;
        FileInputStream fis;
        int lastRowNumber = 0;

        try {
            fis = new FileInputStream(new File(ExcelPath));
            workBook = WorkbookFactory.create(fis);
            sheet = workBook.getSheet(sheetName);
            lastRowNumber = sheet.getLastRowNum();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lastRowNumber+1;
    }

    public static Row getRow(String ExcelPath, String sheetName, int rowno) {

        Workbook workBook;
        Sheet sheet;
        FileInputStream fis;
        Row row = null;

        try {
            fis = new FileInputStream(new File(ExcelPath));
            workBook = WorkbookFactory.create(fis);
            sheet = workBook.getSheet(sheetName);
            row = sheet.getRow(rowno);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return row;
    }

}
