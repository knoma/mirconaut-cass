package com.example;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.HttpStatus;

@Client("testHello")
public interface TestHelloClient {

    @Get("https://jsonplaceholder.typicode.com/todos/1")
    public HttpResponse index();
}