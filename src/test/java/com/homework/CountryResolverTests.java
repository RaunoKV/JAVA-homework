package com.homework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.homework.models.Country;
import com.homework.services.CountryResolver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CountryResolverTests {

    @Autowired
    private CountryResolver resolver;

    @Test
    public void shouldResolveCountry() throws Exception {
        Country resolvedCountry = resolver.resolveCountry("134.201.250.155");

        assertEquals("US", resolvedCountry.getCode());
        assertEquals("United States", resolvedCountry.getName());
	}
}
