package business.service.component;

import business.service.exceptions.InvalidDate;
import entity.Quotation;
import entity.domain.TypeQuotation;
import org.springframework.stereotype.Component;
import util.IOUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class QuotationBCB implements IQuotationProvider{
    public static final String URL_BCB_COTACAO = "http://www4.bcb.gov.br/Download/fechamento/";

    /**
     * Obtém lista de cotação em uma determinada data
     * @param date Data da cotação
     * @return Lista de cotação
     */
    public List<Quotation> getQuotationOfDate(Date date){
        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
            String content = IOUtil.readTextFileFormURL(URL_BCB_COTACAO + sd.format(date) + ".csv");
            return new QuotationListCreator(content).create();
        }catch (IOException e){
            throw new InvalidDate("Não existe cotação para data informada");
        }
    }
}
