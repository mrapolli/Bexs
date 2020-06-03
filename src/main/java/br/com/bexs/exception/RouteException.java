package br.com.bexs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RouteException extends RuntimeException {

    public RouteException(String msg) {
        super(msg);
    }

}
