package com.sganalyzer.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.InputStream;

public class PdfTextExtractor {
    public static String extract(InputStream inputStream) throws Exception {
        try (PDDocument document = PDDocument.load(inputStream)) {
            return new PDFTextStripper().getText(document);
        }
    }
}
