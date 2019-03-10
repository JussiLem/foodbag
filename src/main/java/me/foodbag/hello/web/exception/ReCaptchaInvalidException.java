package me.foodbag.hello.web.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReCaptchaInvalidException extends RuntimeException {

    private static final long serialVersionUID = -1L;


    public ReCaptchaInvalidException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ReCaptchaInvalidException(final String message) {
        super(message);
    }

    public ReCaptchaInvalidException(final Throwable cause) {
        super(cause);
    }
}
