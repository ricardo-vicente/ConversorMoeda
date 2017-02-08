package business.service.core;

import business.service.IQuotationConverterService;
import business.service.component.QuotationBCB;
import business.service.exceptions.InvalidCurrency;
import business.service.exceptions.InvalidDate;
import business.service.exceptions.InvalidValue;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class QuotationConverterServiceTest {

    IQuotationConverterService quotationConverter = new QuotationConverterService();

    @Before
    public void setup(){
        ReflectionTestUtils.setField(quotationConverter, "quotationProvader", new QuotationBCB());
    }

    @Test
    public void currencyQuotation(){
        //DOLAR-EURO - QUINTA-FEIRA
        assertTrue(quotationConverter.currencyQuotation("USD", "EUR", 100.00, "20/11/2014").compareTo(BigDecimal.valueOf(79.69)) == 0);
        //DOLAR-EURO - SEXTA-FEIRA
        assertTrue(quotationConverter.currencyQuotation("USD", "EUR", 100.00, "21/11/2014").compareTo(BigDecimal.valueOf(80.50)) == 0);
        //DOLAR-EURO - SABADO
        assertTrue(quotationConverter.currencyQuotation("USD", "EUR", 100.00, "22/11/2014").compareTo(BigDecimal.valueOf(80.50)) == 0);
        //DOLAR-EURO - DOMINGO
        assertTrue(quotationConverter.currencyQuotation("USD", "EUR", 100.00, "23/11/2014").compareTo(BigDecimal.valueOf(80.50)) == 0);
        //EURO - DOLAR - QUINTA-FEIRA
        assertTrue(quotationConverter.currencyQuotation("EUR", "USD", 100.00, "20/11/2014").compareTo(BigDecimal.valueOf(125.46)) == 0);
        //REAL-EURO - QUINTA-FEIRA
        assertTrue(quotationConverter.currencyQuotation("BLR", "EUR", 100.00, "20/11/2014").compareTo(BigDecimal.valueOf(31.31)) == 0);
        //EURO-REAL - QUINTA-FEIRA
        assertTrue(quotationConverter.currencyQuotation("EUR", "BLR", 100.00, "20/11/2014").compareTo(BigDecimal.valueOf(319.17)) == 0);
        //VENEZUELANO-REAL - QUINTA-FEIRA
        assertTrue(quotationConverter.currencyQuotation("VEF", "BLR", 100.00, "20/11/2014").compareTo(BigDecimal.valueOf(40.38)) == 0);
    }

    @Test(expected = InvalidValue.class)
    public void currencyQuotationInvalidValue(){
        IQuotationConverterService quotationConverter = new QuotationConverterService();
        ReflectionTestUtils.setField(quotationConverter, "quotationProvader", new QuotationBCB());
        //DOLAR-EURO - QUINTA-FEIRA
        quotationConverter.currencyQuotation("USD", "EUR", -1, "20/11/2014");
    }

    @Test(expected = InvalidDate.class)
    public void currencyQuotationInvalidDate(){
        //SEM COTAÇÃO
        quotationConverter.currencyQuotation("USD", "EUR", 50, "01/01/2014");
    }

    @Test(expected = InvalidDate.class)
    public void currencyQuotationInvalidDate2(){
        //DATA INVALIDA
        quotationConverter.currencyQuotation("USD", "EUR", 50, "XYZ");
    }

    @Test(expected = InvalidCurrency.class)
      public void currencyQuotationInvalidCurrency1(){
        //FORMATO INVALIDO DE MOEDA
        quotationConverter.currencyQuotation("USDS", "EURA", 50, "20/11/2014");
    }


    @Test(expected = InvalidCurrency.class)
    public void currencyQuotationInvalidCurrency2(){
        //MOEDAS IGUAIS
        quotationConverter.currencyQuotation("USD", "USD", 50, "20/11/2014");
    }


    @Test(expected = InvalidCurrency.class)
    public void currencyQuotationInvalidCurrency3(){
        //MOEDA INEXISTENTE
        quotationConverter.currencyQuotation("USD", "ZZZ", 50, "20/11/2014");
    }
}
