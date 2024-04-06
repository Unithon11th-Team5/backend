package unithon.uniletter.common.error;

public class BaseException extends RuntimeException {
    private final String message;

    public BaseException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
