package com.coherensolutions.rest.training.http;

import org.apache.http.client.methods.HttpPost;

public class MyHttpDelete extends HttpPost {

    public MyHttpDelete(String url){
        super(url);
    }

    @Override
    public String getMethod() {
        return "DELETE";
    }

}
