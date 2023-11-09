package com.Hartwig.PdfDataExtract.Controllers;

import com.Hartwig.PdfDataExtract.Models.Boleto;
import com.Hartwig.PdfDataExtract.Services.BoletoService;
import com.Hartwig.PdfDataExtract.Services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/boleto")
public class BoletoController {

    @Autowired
    BoletoService boletoService;
    @Autowired
    PdfService pdfService;

    @GetMapping("/{fileName}/{filePassword}")
    public ResponseEntity<List<Boleto>> getArrayOfBoletosFromBarCode(@PathVariable("fileName")String fileName, @PathVariable("filePassword") String filePassword) throws FileNotFoundException {


        return ResponseEntity.status(HttpStatus.OK).body(boletoService.getBoletoFromBarCode(pdfService.extractStrings(pdfService.convertPdfToString(pdfService.getPdfTemporariamente(fileName), filePassword))));
    }

}
