package com.Hartwig.PdfDataExtract.Services;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PdfService {


    public File salvarPdfTemporariamente(MultipartFile file) throws IOException{
        String tempDirectory = System.getProperty("java.io.tmpdir");
        File pdfDirectory = new File(tempDirectory + "pdfs");

        if (!pdfDirectory.exists()){
            boolean directoryCreated = pdfDirectory.mkdirs();
            if(!directoryCreated){
                throw new IOException("Não foi possível criar o diretório para armazenar os arquivos PDF temporários.");
            }
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new IllegalArgumentException("Nenhum arquivo foi enviado na solicitação.");
        }

        File pdfFile = new File(pdfDirectory, fileName);
        file.transferTo(pdfFile);

        return pdfFile;
    }

    public String convertPdfToString(File pdfFile, String filePassword){
        String content = null;
        try(PDDocument document = Loader.loadPDF(pdfFile, filePassword)) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            content = pdfTextStripper.getText(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }


    public File getPdfTemporariamente(String fileName) throws FileNotFoundException {
        String tempDirectory = System.getProperty("java.io.tmpdir");
        File pdfDirectory = new File(tempDirectory + "pdfs");
        File pdfFile = new File(pdfDirectory, fileName);

        if (pdfFile.exists() && !pdfFile.isDirectory()) {
            return pdfFile;
        } else {
            throw new FileNotFoundException("O arquivo " + fileName + " não foi encontrado.");
        }
    }



    public  List<String> extractStrings(String input) {
        List<String> result = new ArrayList<>();


        String regex = "03399";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            int startIndex = matcher.start();
            int endIndex = Math.min(startIndex + 54, input.length());
            String extractedString = input.substring(startIndex, endIndex);
            result.add(extractedString);
        }

        return result;
    }

}
