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
public class QuotationBCB implements IQuotationProvader{
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
            List<Quotation> quotationList = new QuotationListCreator(content).create();

            Quotation usdQuotation = null;
            for(Quotation quotation : quotationList){
                if(quotation.getCurrency().equals("USD")){
                    usdQuotation = quotation;
                }
            }

            if(usdQuotation != null) {
                Quotation blrQuotation = new Quotation();
                blrQuotation.setCurrency("BLR");
                blrQuotation.setSaleRate(1.0);
                blrQuotation.setPurchaseRate(1.0);
                blrQuotation.setSaleParity(usdQuotation.getSaleParity());
                blrQuotation.setPurchaseParity(usdQuotation.getSaleParity());
                blrQuotation.setDateQuotation(date);
                blrQuotation.setType(TypeQuotation.A);
                quotationList.add(blrQuotation);
            }
            return quotationList;
        }catch (IOException e){
            throw new InvalidDate("Não existe cotação para data informada");
        }
    }
}
