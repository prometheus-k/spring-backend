package com.adm;

import javax.servlet.http.HttpServletRequest;

public enum ActionType {
    CREATE,
    READ,
    UPDATE,
    DELETE;

    public static ActionType fromHttpRequest(HttpServletRequest request) {
        String method = request.getMethod();
        switch (method) {
            case "POST":
                return CREATE;
            case "GET":
                return READ;
            case "PUT":
            case "PATCH":
                return UPDATE;
            case "DELETE":
                return DELETE;
            default:
                throw new IllegalArgumentException("Invalid HTTP method: " + method);
        }
    }
}