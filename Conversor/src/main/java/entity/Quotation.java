package entity;

import entity.domain.TypeQuotation;

import java.util.Date;

public class Quotation {
    private Integer idCurrency;
    private Date dateQuotation;
    private TypeQuotation type;
    private String Currency;
    private Double purchaseRate;
    private Double saleRate;
    private Double purchaseParity;
    private Double saleParity;

    public Date getDateQuotation() {
        return (Date)dateQuotation.clone();
    }

    public void setDateQuotation(Date dateQuotation) {
        this.dateQuotation = (Date) dateQuotation.clone();
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public TypeQuotation getType() {
        return type;
    }

    public void setType(TypeQuotation type) {
        this.type = type;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public Double getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(Double purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public Double getSaleRate() {
        return saleRate;
    }

    public void setSaleRate(Double saleRate) {
        this.saleRate = saleRate;
    }

    public Double getPurchaseParity() {
        return purchaseParity;
    }

    public void setPurchaseParity(Double purchaseParity) {
        this.purchaseParity = purchaseParity;
    }

    public Double getSaleParity() {
        return saleParity;
    }

    public void setSaleParity(Double saleParity) {
        this.saleParity = saleParity;
    }
}
