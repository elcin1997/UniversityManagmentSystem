package org.example.universitymanagementsystem.export;

import org.apache.poi.ss.usermodel.Sheet;
import org.example.universitymanagementsystem.dto.StudentDTO;
import org.example.universitymanagementsystem.util.DateUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentExportHandler extends AbstractExportHandler {
    @Override
    public void writeContent(Sheet sheet, List<?> content) {
        var rowId = 0;

        for (int i = 0; i < content.size(); i++) {
            rowId = i + 1;
            StudentDTO student = (StudentDTO) content.get(i);
            var row = sheet.createRow(rowId);

            row.createCell(0).setCellValue(student.getId());
            row.createCell(1).setCellValue(student.getName());
            row.createCell(2).setCellValue(DateUtil.toDateTimeString(student.getCreatedAt()));
            row.createCell(3).setCellValue(DateUtil.toDateTimeString(student.getModifiedAt()));
        }
    }
}
