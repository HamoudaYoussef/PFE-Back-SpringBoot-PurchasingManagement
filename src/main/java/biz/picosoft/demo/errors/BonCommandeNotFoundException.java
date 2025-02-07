package biz.picosoft.demo.errors;

public class BonCommandeNotFoundException extends RuntimeException{
    public BonCommandeNotFoundException(String message) {
        super(message);
    }
}
