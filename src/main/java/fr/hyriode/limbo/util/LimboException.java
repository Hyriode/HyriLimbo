package fr.hyriode.limbo.util;

/**
 * Created by AstFaster
 * on 05/12/2022 at 20:59
 */
public class LimboException extends RuntimeException {

    public LimboException(String message) {
        super(message);
    }

    public LimboException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimboException(Throwable cause) {
        super(cause);
    }

}
