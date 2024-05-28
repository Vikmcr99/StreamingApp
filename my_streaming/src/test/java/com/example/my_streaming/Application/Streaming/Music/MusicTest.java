package com.example.my_streaming.Application.Streaming.Music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MusicTest {

    private Music music;

    @BeforeEach
    void setUp() {
        music = new Music();
    }

    @Test
    @DisplayName("Should test getters and setters")
    void testGettersAndSetters() {
        Long expectedId = 123L;
        String expectedName = "Music";
        double expectedDuration = 4.5;

        music.setId(expectedId);
        music.setName(expectedName);
        music.setDuration(expectedDuration);

        assertEquals(expectedId, music.getId());
        assertEquals(expectedName, music.getName());
        assertEquals(expectedDuration, music.getDuration());
    }
}