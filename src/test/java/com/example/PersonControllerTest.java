package com.example;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import io.micronaut.http.client.annotation.*;
import javax.inject.Inject;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class PersonControllerTest {

    @Inject
    @Client("/")
    RxHttpClient client;

    @Test
    public void testIndex() throws Exception {
        assertEquals(HttpStatus.OK, client.toBlocking().exchange("/person/count").status());
    }
}
