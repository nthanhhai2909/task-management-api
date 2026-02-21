package io.github.nthanhhai2909.taskmanagement.interfaces.restful.common;

import io.github.nthanhhai2909.taskmanagement.internal.application.exception.DomainRuleViolationException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ErrorResponse {
    private int code;
    private String message;

    public static ErrorResponse of(int code, String message) {
        return new ErrorResponse(code, message);
    }

    public static ErrorResponse badRequest(String message) {
        return ErrorResponse.of(400000, message);
    }

    public static ErrorResponse from(DomainRuleViolationException ex) {
        return ErrorResponse.of(400001, ex.description());
    }

    public static ErrorResponse from(IllegalArgumentException ex) {
        return ErrorResponse.of(400002, ex.getMessage());
    }

    public static ErrorResponse from(Exception ex) {
        return ErrorResponse.of(500001, ex.getMessage());
    }
}
