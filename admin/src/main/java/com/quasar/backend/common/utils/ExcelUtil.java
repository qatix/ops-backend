package com.quasar.backend.common.utils;


import com.opencsv.CSVWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;


/**
 * @Author: xionghui
 * @Date: 2018/9/11 下午5:15
 */
public class ExcelUtil {
    public static void generateExcelFile(HttpServletResponse response, String fileName, List<String[]> dataList) throws IOException {
        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
        response.setContentType("application/octet-stream");
        OutputStream outputStream = response.getOutputStream();
        Writer writer = new PrintWriter(outputStream);

        CSVWriter csvWriter = new CSVWriter(writer);
        for (String[] data : dataList
                ) {
            csvWriter.writeNext(data);
        }
        csvWriter.close();
    }
}
