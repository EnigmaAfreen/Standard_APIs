package com.airtel.buyer.airtelboe.util.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelUtil {

    public static String downloadExcel(List<String> headerTextList, List<Map<String, String>> rowMapList, String sheetName,
                                       HttpServletResponse httpServletResponse, String uploadTempDirectory) {
        log.info("Inside ::: ExcelDownloadUtility ::: downloadExcel @Param List<String> headerTextList,\n" +
                "@Param List<Map<String, String>> rowMapList, @Param OutputStream outputStream");
        try {
//            String sheetName = "ExportExcel";
            //  String fileName = "D:\\ExportExcel.xlsx";

//            Boolean isPathCreated =
//                    createPathIfNotExist(EnvConstantUtils.getEnvConstant("UPLOAD_TMP_DIRECTORY") + "exportedExcel" +
//                            EnvConstantUtils.getEnvConstant("FILE_SEPARATOR"));
//            String fileName =
//                    EnvConstantUtils.getEnvConstant("UPLOAD_TMP_DIRECTORY") + "exportedExcel" +
//                            EnvConstantUtils.getEnvConstant("FILE_SEPARATOR") + sheetName + ".xlsx";

            Boolean isPathCreated = createPathIfNotExist(uploadTempDirectory + "exportedExcel/");
//            String fileName = new SimpleDateFormat("ddMMyyhhmmss").format(new Date()) + sheetName + ".xlsx";
            String fileName = sheetName + ".xlsx";

            // Create a Workbook
            Workbook workbook = new SXSSFWorkbook(100); // new HSSFWorkbook() for generating `.xls` file

            // Create a Sheet
            Sheet sheet = workbook.createSheet(sheetName);

            // Create a Font for styling header cells
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Create a Row
            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);

            // Create cells // adding values to header
            for (int i = 0; i < headerTextList.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headerTextList.get(i));
                cell.setCellStyle(headerCellStyle);
            }

            // adding values of table
            int rowNum = 1;

            for (Map<String, String> rowMap : rowMapList) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
                int cellNum = 0;
                for (String colText : headerTextList) {
                    row.createCell(cellNum++).setCellValue(rowMap.get(colText));
                }
            }

            // Resize all columns to fit the content size
//            for (int i = 0; i < headerTextList.size(); i++) {
//                sheet.autoSizeColumn(i);
//            }

            // Fileoutput stream
            FileOutputStream fileOut = new FileOutputStream(uploadTempDirectory + "exportedExcel/" + fileName);
            workbook.write(fileOut);
            fileOut.close();

            // Closing the workbook
            workbook.close();

            // downloading file
//            FileInputStream fis;
//            byte[] b;
//            try {
//                fis = new FileInputStream(fileName);
//                int n;
//                while ((n = fis.available()) > 0) {
//                    b = new byte[n];
//                    int result = fis.read(b);
//                    outputStream.write(b, 0, b.length);
//                    if (result == -1)
//                        break;
//                }
//            } catch (IOException e) {
//                log.info("Exception raised ::: ExcelDownloadUtility ::: downloadExcel : " + e.getMessage());
//                e.printStackTrace();
//            }
//            try {
//                outputStream.flush();
//            } catch (Exception e) {
//                log.info("Exception raised ::: ExcelDownloadUtility ::: downloadExcel : " + e.getMessage());
//                e.printStackTrace();
//            }

            File file = new File(uploadTempDirectory + "exportedExcel/" + fileName);
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            httpServletResponse.setContentType("application/xlsx");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            ServletOutputStream out = httpServletResponse.getOutputStream();
            byte[] bytes = IOUtils.toByteArray(in);
            out.write(bytes);
            out.close();
            out.flush();

        } catch (Exception e) {
            log.info("Exception raised ::: ExcelDownloadUtility ::: downloadExcel : " + e.getMessage());
            e.printStackTrace();
            log.info("Exit from ::: ExcelDownloadUtility ::: downloadExcel");
            return "n";
        }
        log.info("Exit from ::: ExcelDownloadUtility ::: downloadExcel");
        return "y";
    }

    public static Boolean createPathIfNotExist(String filePath) {
        log.info("Inside ::: ExcelDownloadUtility ::: createPathIfNotExist :: @Param String filePath : " +
                filePath);
        log.info("create path if not exist:::" + filePath);
        Boolean result = Boolean.FALSE;
        Path path = Paths.get(filePath);
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                result = Boolean.TRUE;
            } catch (IOException e) {
                //fail to create directory
                e.printStackTrace();
            }
        } else {
            result = Boolean.TRUE;
        }
        log.info("Exit from ::: ExcelDownloadUtility ::: createPathIfNotExist");
        return result;
    }

}
