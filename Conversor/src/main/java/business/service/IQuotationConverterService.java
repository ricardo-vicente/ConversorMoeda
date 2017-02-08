package business.service;

import java.math.BigDecimal;

public interface IQuotationConverterService {
    public BigDecimal currencyQuotation(String from, String to, Number value, String quotation);
}
