package business.service.component;

import entity.Quotation;
import entity.domain.TypeQuotation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuotationListCreator {
    private String content;
    public QuotationListCreator(String content){
        this.content = content;
    }

    public List<Quotation> create(){
        List<Quotation> quotationList = new ArrayList<Quotation>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Scanner scan = new Scanner(content);
            scan.useDelimiter(";|\\n");

            Quotation quotation;
            while (scan.hasNext()){
                quotation = new Quotation();
                quotation.setDateQuotation(sdf.parse(scan.next()));
                quotation.setIdCurrency(scan.nextInt());
                quotation.setType(TypeQuotation.valueOf(scan.next()));
                quotation.setCurrency(scan.next());
                quotation.setPurchaseRate(Double.valueOf(scan.next().replace(",", ".")));
                quotation.setSaleRate(Double.valueOf(scan.next().replace(",", ".")));
                quotation.setPurchaseParity(Double.valueOf(scan.next().replace(",", ".")));
                quotation.setSaleParity(Double.valueOf(scan.next().replace(",", ".")));
                quotationList.add(quotation);
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return quotationList;
    }
}
