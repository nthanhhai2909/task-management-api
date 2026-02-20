package io.github.nthanhhai2909.taskmanagement.interfaces.restful.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ErrorResponse {
    private int code;
    private String message;
}
