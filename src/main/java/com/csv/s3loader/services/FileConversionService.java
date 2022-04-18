package com.csv.s3loader.services;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.csv.s3loader.model.Stock;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FileConversionService {


    @Value("${document.destination-conversion}")
    private String destinationConversionFile;

    @Autowired
    S3Service s3Service;

    public void convertToDestination(String filePath, String srcBucketName){

        List<String[]> stocks = new ArrayList<String[]>();

        for(int i = 1;i<=1000;i++){
            stocks.add(new String[]{"Team83"+1,"83"+i, LocalDateTime.now().toString()});
        }
        try {
            writeRecords(stocks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeRecords(List<String[]> lines) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
        try (CSVWriter writer = buildCSVWriter(streamWriter)) {
            writer.writeAll(lines);
            writer.flush();
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(stream.toByteArray().length);
            this.s3Service.uploadChunk(new ByteArrayInputStream(stream.toByteArray()), meta);
        }
    }

    private CSVWriter buildCSVWriter(OutputStreamWriter streamWriter) {
        return new CSVWriter(streamWriter, ',', Character.MIN_VALUE, '"', System.lineSeparator());
    }

}