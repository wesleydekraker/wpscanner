package tools.wesley.wpscanner.controllers;

public class HttpException extends Exception {
    public HttpException(String message, Throwable cause) {
		super(message, cause);
	}
}