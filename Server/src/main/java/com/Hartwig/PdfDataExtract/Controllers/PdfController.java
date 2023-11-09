package com.Hartwig.PdfDataExtract.Controllers;

import com.Hartwig.PdfDataExtract.Services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    public PdfService pdfService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPdf(@RequestParam("file")MultipartFile file){
        try{
            ResponseEntity<String> body = getStringResponseEntity(file);
            if (body != null) return body;

            File temporaryPdf = pdfService.salvarPdfTemporariamente(file);
            return  ResponseEntity.ok("PDF recebido e armazenado temporariamente com sucesso, " + file.getOriginalFilename());
        }
        catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao armazenar o PDF temporariamente.");
        }
    }


    private static ResponseEntity<String> getStringResponseEntity(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Arquivo vazio.");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("application/pdf")) {
            return ResponseEntity.badRequest().body("Arquivo deve ser um PDF.");
        }
        long sizeInBytes = file.getSize();
        long sizeInKilobytes = sizeInBytes / 1024;
        if (sizeInKilobytes > 1024) { // 1MB
            return ResponseEntity.badRequest().body("Tamanho máximo do arquivo é 1MB.");
        }
        return null;
    }
}
