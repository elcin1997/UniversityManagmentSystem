package org.example.universitymanagementsystem.export;

import org.apache.poi.ss.usermodel.Sheet;
import org.example.universitymanagementsystem.dto.CourseDTO;
import org.example.universitymanagementsystem.util.DateUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseExportHandler extends AbstractExportHandler {
    @Override
    public void writeContent(Sheet sheet, List<?> content) {
        var rowId = 0;

        for (int i = 0; i < content.size(); i++) {
            rowId = i + 1;
            CourseDTO course = (CourseDTO) content.get(i);
            var row = sheet.createRow(rowId);

            row.createCell(0).setCellValue(course.getId());
            row.createCell(1).setCellValue(course.getName());
            row.createCell(2).setCellValue(course.getCapacity());
            row.createCell(3).setCellValue(course.getCode());
            row.createCell(4).setCellValue(course.getCredit());
            row.createCell(5).setCellValue(DateUtil.toDateTimeString(course.getCreatedAt()));
            row.createCell(6).setCellValue(DateUtil.toDateTimeString(course.getModifiedAt()));
        }
    }
}
