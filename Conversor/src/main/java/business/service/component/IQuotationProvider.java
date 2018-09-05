package business.service.component;

import entity.Quotation;

import java.util.Date;
import java.util.List;

public interface IQuotationProvider {
    public List<Quotation> getQuotationOfDate(Date date);
}
