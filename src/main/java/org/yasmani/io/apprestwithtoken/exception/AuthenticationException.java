package org.yasmani.io.apprestwithtoken.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationException extends RuntimeException{
    Logger logger = LoggerFactory.getLogger(AuthenticationException.class);
    public AuthenticationException(String message) {
        super(message);
        logger.error("the failed was in : {}",message);
    }
}

