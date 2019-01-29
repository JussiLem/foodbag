package me.foodbag.hello.web.exception;

import lombok.ToString;

@ToString
public class FoodBagException extends RuntimeException {

    public FoodBagException() { super(); }

    public FoodBagException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FoodBagException(final String message) {super(message);}

    public FoodBagException(final Throwable cause) {super(cause);}

}
