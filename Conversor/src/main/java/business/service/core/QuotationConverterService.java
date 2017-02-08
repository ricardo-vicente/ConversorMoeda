package business.service.core;

import business.service.IQuotationConverterService;
import business.service.component.IQuotationProvader;
import business.service.exceptions.InvalidCurrency;
import business.service.exceptions.InvalidDate;
import business.service.exceptions.InvalidValue;
import entity.Quotation;
import entity.domain.TypeQuotation;
import org.springframework.stereotype.Service;
import util.DateUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class QuotationConverterService implements IQuotationConverterService {

    @Resource
    IQuotationProvader quotationProvader;

    public BigDecimal currencyQuotation(String from, String to, Number value, String quotation){
        Date dateQuotation = DateUtil.fromDateString(quotation, "dd/MM/yyyy");
        validateParameters(from, to, value, dateQuotation);
        dateQuotation = getValidBusinessDate(dateQuotation);

        List<Quotation> quotationList = quotationProvader.getQuotationOfDate(dateQuotation);
        Quotation fromQuotation = null;
        Quotation toQuotation = null;

        for(Quotation quotationAux :   quotationList){
            if(quotationAux.getCurrency().equals(from)){
                fromQuotation = quotationAux;
            }
            if(quotationAux.getCurrency().equals(to)){
                toQuotation = quotationAux;
            }
        }

        BigDecimal valueBig = BigDecimal.valueOf(value.doubleValue());
        valueBig = valueBig.setScale(2, BigDecimal.ROUND_HALF_UP);

        if(fromQuotation != null && toQuotation != null){
           return convertCurrency(valueBig, fromQuotation, toQuotation);
        }else{
            if("BLR".equals(from) && toQuotation != null){
                return valueBig.divide(BigDecimal.valueOf(toQuotation.getSaleRate()), BigDecimal.ROUND_HALF_UP);
            }else if("BLR".equals(to) && fromQuotation != null){
                return valueBig.multiply(BigDecimal.valueOf(fromQuotation.getPurchaseRate()));
            }else {
                throw new InvalidCurrency("Moeda informada não disponível nesta data");
            }
        }
    }

    private void validateParameters(String from, String to, Number value, Date quotation){
        validateCurrency(from);
        validateCurrency(to);

        if(from.equals(to)){
            throw new InvalidCurrency("Moeda não podem ser iguais");
        }

        if(value.doubleValue() < 0){
            throw new InvalidValue("Valor informado não é válido");
        }
        if(quotation == null){
            throw new InvalidDate("Data de cotação formato inválido");
        }
    }

    private void validateCurrency(String currency){
        if(currency == null || currency.isEmpty() || currency.length() != 3){
            throw new InvalidCurrency("Moeda não informada ou invalida");
        }
    }

    /**
     * Realizar a conversão do valor informado em uma determinada moeda, para outra.
     * @param value Valor a ser convertido
     * @param from Moeda de origem
     * @param to Moeda destino
     * @return Valor convertido
     */
    private BigDecimal convertCurrency(BigDecimal value, Quotation from, Quotation to){
        BigDecimal doralValueFrom;
        BigDecimal returnvalue;
        if(TypeQuotation.A.equals(from.getType())){
            doralValueFrom = value.divide(BigDecimal.valueOf(from.getPurchaseParity()), BigDecimal.ROUND_HALF_UP);
        }else {
            doralValueFrom = value.multiply(BigDecimal.valueOf(from.getPurchaseParity()));
        }

        if(TypeQuotation.A.equals(to.getType())){
            returnvalue =  doralValueFrom.multiply(BigDecimal.valueOf(to.getSaleParity()));
        }else {
            returnvalue =  doralValueFrom.divide(BigDecimal.valueOf(to.getSaleParity()), BigDecimal.ROUND_HALF_UP);
        }
        return returnvalue.setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * Verifica se a data fornecida é final de semana
     * @param date Data a ser verificada
     * @return Data em dia útil
     */
    private Date getValidBusinessDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //Verifica se é sábado
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            return calendar.getTime();
        }

        //Verifica se é domingo
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            calendar.add(Calendar.DAY_OF_MONTH, -2);
            return calendar.getTime();
        }
        return date;
    }
}
