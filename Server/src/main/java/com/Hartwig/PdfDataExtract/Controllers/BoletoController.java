package com.Hartwig.PdfDataExtract.Controllers;

import com.Hartwig.PdfDataExtract.Models.Boleto;
import com.Hartwig.PdfDataExtract.Services.BoletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boleto")
public class BoletoController {

    @Autowired
    BoletoService boletoService;

    @GetMapping("/teste")
    public ResponseEntity<Boleto> teste(@RequestParam("codigodebarras") String codigoDeBarras){


        return ResponseEntity.status(HttpStatus.OK).body(boletoService.getBoletoFromBarCode(codigoDeBarras));
    }

}
