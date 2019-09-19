package pl.maciejem.repositoryinfo.controller.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public final class ApiException {
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

    public ApiException(final HttpStatus status, final String message, final String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}