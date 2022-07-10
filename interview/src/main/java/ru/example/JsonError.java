package ru.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 1 - Wrong login or password
 * 2 - Wrong option in menu
 * 101 - Animal not found
 * 102 - Animal with this name already exists
 * 103 - Incorrect kind
 * 105 - Sex should be only "m" or "f"
 */
public class JsonError {
    private int codeError;
    private String message;
    ObjectMapper objectMapper = new ObjectMapper();

    public JsonError(){}
    public JsonError(int codeError, String message) {
        this.codeError = codeError;
        this.message = message;
        try {
            System.out.println(objectMapper.writeValueAsString(this));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public int getCodeError() {
        return codeError;
    }

    public void setCodeError(int codeError) {
        this.codeError = codeError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
