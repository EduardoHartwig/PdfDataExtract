package com.Hartwig.PdfDataExtract.Services;


import com.Hartwig.PdfDataExtract.Models.Boleto;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class BoletoService {

    public List<Boleto> getBoletoFromBarCode(List<String> codigoDeBarras){
        List<Boleto> boletos = new ArrayList<>();

        for (String s : codigoDeBarras){
            Boleto boleto = new Boleto();
            boleto.setCodigoDeBarras(s);
            String dias = "";
            String valor = "";

            if(s.length() >= 54){
                dias = s.substring(40,44);
                if (dias.charAt(0) == '1'){
                    StringBuilder builder = new StringBuilder(dias);
                    builder.insert(1, '0');
                    boleto.setDataDeVencimento(convertDaysToDate(builder.toString()));
                }else{
                    boleto.setDataDeVencimento(convertDaysToDate(dias));
                }

                valor = s.substring(49, 54);
                StringBuilder builder = new StringBuilder(valor);
                builder.insert(3, ',');
                boleto.setValorDoBoleto("R$" + builder.toString());
            }
            boletos.add(boleto);
        }


        return boletos;
    }


    private String convertDaysToDate(String days) {
        Calendar dataBase = Calendar.getInstance();
        dataBase.set(1997, Calendar.OCTOBER, 7);
        dataBase.add(Calendar.DATE, Integer.parseInt(days));

        Date dataVencimento = dataBase.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return dateFormat.format(dataVencimento);
    }

}
