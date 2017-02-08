package business.service.exceptions;

public class InvalidCurrency extends BusinessException {
    public InvalidCurrency(String msg){
        super(msg);
    }
}
