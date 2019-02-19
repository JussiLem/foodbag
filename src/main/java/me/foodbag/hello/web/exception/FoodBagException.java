package me.foodbag.hello.web.exception;

public class FoodBagException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FoodBagException() { super(); }

    public FoodBagException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FoodBagException(final String message) {super(message);}

    public FoodBagException(final Throwable cause) {super(cause);}

}
