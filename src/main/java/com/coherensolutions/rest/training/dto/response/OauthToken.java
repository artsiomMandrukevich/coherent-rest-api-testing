package com.coherensolutions.rest.training.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OauthToken {

    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("token_type")
    public String tokenType;

    public String bearer;

    @JsonProperty("expires_in")
    public String expiresIn;

    public String scope;

}
