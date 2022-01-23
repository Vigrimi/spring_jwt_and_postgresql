package com.vinnikov.inbox.ru.insideJWTControlEx2022jan12.validation;

import java.util.ArrayList;
import java.util.List;

// обработать исключения при валидации
public class ValidationErrorResponse {

    private List<Violation> violations = new ArrayList<>();

    public ValidationErrorResponse(List<Violation> violations) {
        this.violations = violations;
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }

    public static class Violation {

        private String fieldName;
        private String message;

        public Violation(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}