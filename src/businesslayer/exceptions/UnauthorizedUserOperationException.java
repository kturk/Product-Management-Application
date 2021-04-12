package businesslayer.exceptions;

public class UnauthorizedUserOperationException extends Exception{

    public UnauthorizedUserOperationException() {
        super("Unauthorized User Operation");
    }

    public UnauthorizedUserOperationException(String message){
        super(message);
    }
}
