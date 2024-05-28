package com.example.my_streaming.Application.Streaming.Band;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BandTest {

    @Test
    void testGettersAndSetters() {
        Band band = new Band();

        Long expectedId = 123L;
        String expectedName = "Nome da Banda";
        String expectedDescription = "Descrição da Banda";

        band.setId(expectedId);
        band.setName(expectedName);
        band.setDescription(expectedDescription);

        assertEquals(expectedId, band.getId());
        assertEquals(expectedName, band.getName());
        assertEquals(expectedDescription, band.getDescription());
    }

}