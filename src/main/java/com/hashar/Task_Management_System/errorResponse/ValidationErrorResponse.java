package com.hashar.Task_Management_System.errorResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ValidationErrorResponse {
    private String FieldName;
    private String errorMessage;
}
