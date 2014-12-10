package com.example.mozan;

import org.json.JSONObject;

public class ServerResponse {
    private boolean hasError;
    private String message;
    private JSONObject response;

    public ServerResponse(boolean hasError, String message, JSONObject response) {
        this.hasError = hasError;
        this.message = message;
        this.response = response;
    }

    public ServerResponse(boolean hasError, String message) {
        this.hasError = hasError;
        this.message = message;
    }

    public boolean hasError() {
        return hasError;
    }

    public String getMessage() {
        return message;
    }

    public JSONObject getResponse() {
        return response;
    }
}
