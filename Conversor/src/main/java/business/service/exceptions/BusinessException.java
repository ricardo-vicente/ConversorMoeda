package business.service.exceptions;

public abstract class BusinessException extends RuntimeException {
    public BusinessException(String msg){
        super(msg);
    }
}
