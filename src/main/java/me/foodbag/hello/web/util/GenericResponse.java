package me.foodbag.hello.web.util

import java.util.stream.Collectors

import lombok.Getter
import lombok.Setter
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError

@Setter
@Getter
class GenericResponse {
    private var message: String? = null
    private var error: String? = null

    constructor(message: String) : super() {
        this.message = message
    }

    constructor(message: String, error: String) : super() {
        this.message = message
        this.error = error
    }

    constructor(allErrors: List<ObjectError>, error: String) {
        this.error = error
        val temp = allErrors.stream()
                .map<String> { e ->
                    if (e is FieldError) {
                        return@allErrors.stream()
                                .map("{\"field\":\""
                                        + e.field
                                        + "\",\"defaultMessage\":\""
                                        + e.getDefaultMessage()
                                        + "\"}")
                    } else {
                        return@allErrors.stream()
                                .map("{\"object\":\""
                                        + e.objectName
                                        + "\",\"defaultMessage\":\""
                                        + e.defaultMessage
                                        + "\"}")
                    }
                }
                .collect<String, *>(Collectors.joining(","))
        this.message = "[$temp]"
    }

}
