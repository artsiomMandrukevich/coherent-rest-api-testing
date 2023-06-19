package com.coherensolutions.rest.training;

import com.coherensolutions.rest.training.http.Token;
import lombok.SneakyThrows;

public class ExampleExecution {

    @SneakyThrows
    public static void main(String[] args) {

        Token token = Token.getInstance();
        System.out.println("write token " + token.getWriteToken());
        System.out.println("read token " + token.getReadToken());

    }
}
