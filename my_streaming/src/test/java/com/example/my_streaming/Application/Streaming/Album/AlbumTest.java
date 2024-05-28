package com.example.my_streaming.Application.Streaming.Album;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    private Album album;

    @BeforeEach
    void setUp() {
        album = new Album();
    }

    @Test
    @DisplayName("Should test getters and setters")
     void testGettersAndSetters() {
        Long expectedId = 123L;
        String expectedName = "Album Name";

        album.setId(expectedId);
        album.setName(expectedName);

        assertEquals(expectedId, album.getId());
        assertEquals(expectedName, album.getName());
    }

}