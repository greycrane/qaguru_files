package com.example;

import com.example.model.PersonJson;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonTests {
    ClassLoader cl = JsonTests.class.getClassLoader();

    @Test
    void checkJsonFile() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (
                InputStream inputStream = cl.getResourceAsStream("person.json");
                InputStreamReader reader = new InputStreamReader(inputStream)
            )
        {
            PersonJson person = mapper.readValue(reader, PersonJson.class);
            assertThat(person.person.id).isEqualTo(1);
            assertThat(person.person.firstName).contains("John");
            assertThat(person.person.lastName).contains("Wilson");
            assertThat(person.person.age).isEqualTo(34);
            assertThat(person.person.hasChildren).isTrue();
        }
    }
}
