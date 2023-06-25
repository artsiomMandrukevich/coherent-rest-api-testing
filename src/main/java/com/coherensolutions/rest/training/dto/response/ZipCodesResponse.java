package com.coherensolutions.rest.training.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ZipCodesResponse {

    public List<String> zipCodes;

    public ZipCodesResponse(List<String> zipCodes) {
        this.zipCodes = zipCodes;
    }

}
