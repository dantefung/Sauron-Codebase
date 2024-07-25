package com.dantefung.tika;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DocReader {

    public static String readDocFile(String filePath) {
        try (FileInputStream stream = new FileInputStream(new File(filePath))) {
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler(); // 用于获取文档文本
            Metadata metadata = new Metadata(); // 用于获取文档元数据

            // 解析文档
            parser.parse(stream, handler, metadata);

            return handler.toString();
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String filePath = "d:/test.doc";
        String content = readDocFile(filePath);
        if (content != null) {
            System.out.println(content);
        } else {
            System.out.println("Failed to read the document.");
        }
    }
}