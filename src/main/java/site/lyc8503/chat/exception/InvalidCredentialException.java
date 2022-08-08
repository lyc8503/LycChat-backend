package site.lyc8503.chat.exception;

public class InvalidCredentialException extends BaseException{
    public InvalidCredentialException(String message) {
        super(400, message, 400);
    }
}
