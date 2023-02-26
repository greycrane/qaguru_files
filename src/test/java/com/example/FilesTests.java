package com.example;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.*;

public class FilesTests {
    ClassLoader cl = FilesTests.class.getClassLoader();

    @Test
    void checkZippedFiles() throws Exception {
        try (
                InputStream source = cl.getResourceAsStream("samples.zip");
                ZipInputStream zis = new ZipInputStream(source);
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith("pdf")) {
                    PDF pdfFile = new PDF(zis);
                    assertThat(pdfFile.text).contains("Самоучитель Java");
                } else if (entry.getName().endsWith(".xls")) {
                    XLS xlsFile = new XLS(zis);
                    assertThat(xlsFile.excel.getSheetAt(0).getRow(0)
                            .getCell(0).getStringCellValue().contains("Auth Service"));
                } else if (entry.getName().endsWith(".csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> csvFile = csvReader.readAll();
                    assertThat(csvFile.get(1)[1]).contains("QA");
                }
            }
        }
    }
}