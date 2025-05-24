package com.company.fileupload_micro.entity;

/**
 * @description
 * @author: RicksonYu
 * @create: 2025年-05月-24日--16:27
 */
public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
