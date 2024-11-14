package com.airtel.buyer.airtelboe.exception;

import com.airtel.buyer.airtelboe.dto.common.BoeResponse;
import org.springframework.http.HttpStatus;

public class BoeException extends RuntimeException {
    BoeResponse<?> boeResponse;

    HttpStatus httpStatus;

    public BoeException(String message, BoeResponse<?> onboardingResponse, HttpStatus httpStatus) {
        super(message);
        this.boeResponse = onboardingResponse;
        this.httpStatus = httpStatus;
    }

}
