package pl.maciejem.repositoryinfo.controller.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.maciejem.repositoryinfo.controller.exception.dto.ApiException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<Object> handleHttpClientErrorException(final HttpClientErrorException ex) {
        final String exceptionMessage = "Bad request to Github API";
        final ApiException apiException = new ApiException(ex.getStatusCode(), ex.getLocalizedMessage(), exceptionMessage);
        return new ResponseEntity<>(apiException, new HttpHeaders(), ex.getStatusCode());
    }

    @ExceptionHandler({HttpServerErrorException.class})
    public ResponseEntity<Object> handleHttpServerErrorException(final HttpServerErrorException ex) {
        final String exceptionMessage = "Github API is not available";
        final ApiException apiException = new ApiException(ex.getStatusCode(), ex.getLocalizedMessage(), exceptionMessage);
        return new ResponseEntity<>(apiException, new HttpHeaders(), ex.getStatusCode());
    }

    @ExceptionHandler({ResourceAccessException.class})
    public ResponseEntity<Object> handleResourceAccessException(final ResourceAccessException ex) {
        final String exceptionMessage = "Github API is not available2";
        final ApiException apiException = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), exceptionMessage);
        return new ResponseEntity<>(apiException, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
