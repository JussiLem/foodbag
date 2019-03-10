package me.foodbag.hello.web.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReCaptchaUnavailableException extends RuntimeException {



    public ReCaptchaUnavailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ReCaptchaUnavailableException(final String message) {
        super(message);
    }

    public ReCaptchaUnavailableException(final Throwable cause) {
        super(cause);
    }
}
