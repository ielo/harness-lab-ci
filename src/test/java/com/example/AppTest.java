package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void greetingIncludesName() {
        assertEquals("Hello, Harness!", App.greeting("Harness"));
    }

    @Test
    void healthReportsUp() {
        assertEquals("{\"status\":\"UP\"}", App.healthJson());
    }

    @Test
    void nameFromQueryReadsNameParameter() {
        assertEquals("Rafael", App.nameFromQuery("name=Rafael"));
    }

    @Test
    void nameFromQueryDecodesEncodedSpaces() {
        assertEquals("Ana Maria", App.nameFromQuery("name=Ana%20Maria"));
    }

    @Test
    void nameFromQueryFallsBackToDefault() {
        assertEquals("Harness", App.nameFromQuery(null));
        assertEquals("Harness", App.nameFromQuery("other=1"));
    }
}
