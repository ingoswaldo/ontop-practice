/**
 * @author : Oswaldo Montes
 * @created : December 27, 2023
 **/
package com.koombea.web.app.ontoppractice.shared.utils;

import com.github.jasminb.jsonapi.JSONAPIDocument;
import com.github.jasminb.jsonapi.models.errors.Error;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;

import java.util.Collections;

public class ErrorResponseUtil {

    public static JSONAPIDocument createSingletonErrorDocument(ErrorResponse exception) {
        return JSONAPIDocument.createErrorDocument(Collections.singleton(createErrorFromErrorResponse(exception)));
    }

    public static JSONAPIDocument createSingletonErrorDocument(Exception exception, String httpStatusCode) {
        return JSONAPIDocument.createErrorDocument(Collections.singleton(createErrorFromException(exception, httpStatusCode)));
    }

    private static Error createErrorFromException(Exception exception, String httpStatusCode) {
        return createError(httpStatusCode, HttpStatus.valueOf(httpStatusCode).toString(), exception.getMessage());
    }

    private static Error createErrorFromErrorResponse(ErrorResponse exception) {
        return createError(
                String.valueOf(exception.getStatusCode().value()),
                exception.getBody().getTitle(),
                exception.getBody().getDetail()
        );
    }

    private static Error createError(String httpStatus, String title, String details) {
        Error error = new Error();
        error.setStatus(httpStatus);
        error.setTitle(title);
        error.setDetail(details);

        return error;
    }
}
