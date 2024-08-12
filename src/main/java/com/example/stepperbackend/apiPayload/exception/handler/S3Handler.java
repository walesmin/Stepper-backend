package com.example.stepperbackend.apiPayload.exception.handler;

import com.example.stepperbackend.apiPayload.code.BaseErrorCode;
import com.example.stepperbackend.apiPayload.exception.GeneralException;

public class S3Handler extends GeneralException {

    public S3Handler(BaseErrorCode code) {
        super(code);
    }

}
