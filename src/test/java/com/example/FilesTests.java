package com.example;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.*;

public class FilesTests {
    ClassLoader cl = FilesTests.class.getClassLoader();

    @Test
    void checkZippedFiles() throws Exception {
        try (
                InputStream source = cl.getResourceAsStream("test.zip");
                ZipInputStream zis = new ZipInputStream(source);
        ) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith("pdf")) {
                    PDF pdfFile = new PDF(zis);
                    assertThat(pdfFile.text).contains("...continued from page 1");
                } else if (entry.getName().endsWith(".xls")) {
                    XLS xlsFile = new XLS(zis);
                    assertThat(xlsFile.excel.getSheetAt(0).getRow(3)
                            .getCell(4).getStringCellValue().contains("France"));
                } else if (entry.getName().endsWith(".csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> csvFile = csvReader.readAll();
                    assertThat(csvFile.get(0)[2]).contains("London");
                }
            }
        }
    }
}