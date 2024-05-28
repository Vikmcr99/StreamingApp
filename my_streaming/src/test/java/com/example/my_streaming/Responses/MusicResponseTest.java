package com.example.my_streaming.Responses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MusicResponseTest {

    @Test
    void testGettersAndSetters() {
        MusicResponse musicResponse = new MusicResponse();

        Long id = 1L;
        String name = "Song";
        Double duration = 3.5;

        musicResponse.setId(id);
        musicResponse.setName(name);
        musicResponse.setDuration(duration);

        assertEquals(id, musicResponse.getId());
        assertEquals(name, musicResponse.getName());
        assertEquals(duration, musicResponse.getDuration());
    }

}