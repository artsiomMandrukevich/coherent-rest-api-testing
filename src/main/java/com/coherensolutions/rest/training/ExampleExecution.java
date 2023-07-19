package com.coherensolutions.rest.training;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;


public class ExampleExecution {

    @SneakyThrows
    public static void main(String[] args) {


        List<Boolean> booleans = new ArrayList<>();
        booleans.add(true);
        booleans.add(true);
        booleans.add(true);
        booleans.add(true);

        System.out.println(booleans.contains(false));

   }
}
