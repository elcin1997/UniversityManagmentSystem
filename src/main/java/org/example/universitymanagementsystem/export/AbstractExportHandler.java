package org.example.universitymanagementsystem.export;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract class AbstractExportHandler {

    public InputStream export(String[] headers, List<?> content) {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            workbook.setCompressTempFiles(true);
            int sheetIndex = 1;
            int contentIndex = 0;
            int contentCount = content.size();

            while (contentIndex < contentCount) {
                var sheet = workbook.createSheet("sheet" + sheetIndex);
                sheet.setRandomAccessWindowSize(1000);
                var header = sheet.createRow(0);

                var headerStyle = workbook.createCellStyle();
                headerStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                for (int col = 0; col < headers.length; col++) {
                    var cell = header.createCell(col);
                    cell.setCellValue(headers[col]);

                    sheet.setColumnWidth(col, (5) * 255);

                    if (!headers[col].isEmpty()) {
                        cell.setCellStyle(headerStyle);
                        sheet.setColumnWidth(col, (headers[col].length() + 3) * 255);
                    }
                }

                sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, headers.length - 1));
                sheet.createFreezePane(0, 1);

                int maxRowsPerSheet = 400000;
                int endIndex = Math.min(contentIndex + maxRowsPerSheet - 1, contentCount);
                writeContent(sheet, content.subList(contentIndex, endIndex));
                contentIndex = endIndex;
                sheetIndex++;
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
        }
    }

    public abstract void writeContent(Sheet sheet, List<?> content);
}
