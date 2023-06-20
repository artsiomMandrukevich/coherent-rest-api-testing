package com.coherensolutions.rest.training.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ZipCodesResponse {

    public int status;

    public List<String> zipCodes;

    public ZipCodesResponse(int status, List<String> zipCodes) {
        this.status = status;
        this.zipCodes = zipCodes;
    }

}
