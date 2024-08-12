package com.example.stepperbackend.apiPayload.exception.handler;

import com.example.stepperbackend.apiPayload.code.BaseErrorCode;
import com.example.stepperbackend.apiPayload.exception.GeneralException;

public class ScrapHandler extends GeneralException {
    public ScrapHandler(BaseErrorCode code) {
        super(code);
    }
}
