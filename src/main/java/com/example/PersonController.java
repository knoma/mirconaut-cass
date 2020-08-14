package com.example;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Maybe;

import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;

@Controller("/person")
public class PersonController {

    private PersonDAO personDAO;

    @Inject
    public PersonController(CqlSession session) {
        PersonMapper personMapper = new PersonMapperBuilder(session).build();
        this.personDAO = personMapper.personDao(CqlIdentifier.fromCql("cass_drop"));
    }

    @Get(uri = "/{personId}", produces = MediaType.APPLICATION_JSON)
    public Maybe<Person> get(UUID personId) {
        return Maybe.fromCompletionStage(personDAO.getById(personId))
                .onErrorComplete();
    }

    @Delete(uri = "/{personId}", produces = MediaType.APPLICATION_JSON)
    public @NonNull Maybe<Map<String, Long>> delete(UUID personId) {
        personDAO.delete(personId);
        return Maybe.fromCompletionStage(personDAO.getCount())
                .map(a -> Map.of("count", a)).onErrorComplete();
    }

    @Get(uri = "/all", produces = MediaType.APPLICATION_JSON)
    public Maybe<Iterable<Person>> getAll() {
        return Maybe.fromCompletionStage(personDAO.getAll())
                .map(a -> a.currentPage()).onErrorComplete();
    }

    @Get(uri = "/count", produces = MediaType.APPLICATION_JSON)
    public Maybe<Map<String, Long>> getCount() {
        return Maybe.fromCompletionStage(personDAO.getCount())
                .map(a -> Map.of("count", a)).onErrorComplete();
    }

    @Post(uri = "/", produces = MediaType.APPLICATION_JSON)
    public @NonNull Maybe<Void> save(Person person) {
        return Maybe.fromCompletionStage(personDAO.saveAsync(person))
                .onErrorComplete();
    }
}