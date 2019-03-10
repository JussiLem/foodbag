package me.foodbag.hello.captcha;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"success", "challenge_ts", "hostname", "error-codes"})
public class GoogleResponse {

  @JsonProperty("success")
  private boolean success;

  @JsonProperty("challenge_ts")
  private String challengeTs;

  @JsonProperty("hostname")
  private String hostname;

  @JsonProperty("error-codes")
  private ErrorCode[] errorCodes;

  enum ErrorCode {
    MISSING_SECRET,
    INVALID_SECRET,
    MISSING_RESPONSE,
    INVALID_RESPONSE;

    private static Map<String, ErrorCode> errorCodeMap = new HashMap<>(4);

    static {
      errorCodeMap.put("missing-input-secret", MISSING_SECRET);
      errorCodeMap.put("invalid-input-secret", INVALID_SECRET);
      errorCodeMap.put("missing-input-response", MISSING_RESPONSE);
      errorCodeMap.put("invalid-input-response", INVALID_RESPONSE);
    }

    @JsonCreator
    public static ErrorCode forValue(final String value) {
      return errorCodeMap.get(value.toLowerCase());
    }
  }

  @JsonIgnore
  boolean hasClientError() {
    final ErrorCode[] errors = getErrorCodes();
    if (errors == null) {
      return false;
    }
    for (final ErrorCode error : errors) {
      switch (error) {
        case INVALID_RESPONSE:
        case MISSING_RESPONSE:
          return true;
        default:
          break;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "GoogleResponse{"
        + "success="
        + success
        + ", challengeTs='"
        + challengeTs
        + '\''
        + ", hostname='"
        + hostname
        + '\''
        + ", errorCodes="
        + Arrays.toString(errorCodes)
        + '}';
  }
}
