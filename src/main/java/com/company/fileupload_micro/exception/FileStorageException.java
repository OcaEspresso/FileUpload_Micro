package com.company.fileupload_micro.exception;

/**
 * @description
 * @author: RicksonYu
 * @create: 2025年-05月-24日--16:24
 */
public class FileStorageException extends RuntimeException {

    public FileStorageException(String message)
    {
        super(message);
    }

    public FileStorageException(String message, Throwable cause)
    {
        super(message,cause);

    }
}
