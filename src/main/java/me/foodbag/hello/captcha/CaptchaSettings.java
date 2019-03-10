package me.foodbag.hello.captcha;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
public class CaptchaSettings {
    private String site;
    private String secret;
}
