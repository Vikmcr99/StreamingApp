package com.example.my_streaming.Application.Streaming.Band;

import com.example.my_streaming.Application.Streaming.Music.Music;
import io.github.resilience4j.retry.Retry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BandRepositoryTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Retry retry;

    @InjectMocks
    private BandRepository bandRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    @DisplayName("Should get music by ID")
//    void getMusicById() {
//        // Mock da música retornada pela chamada da API
//        Music music = new Music();
//        music.setId(1L);
//        music.setName("Example Music");
//
//        // Configuração do comportamento esperado do RestTemplate para a chamada da API
//        when(restTemplate.getForObject(anyString(), eq(Music.class))).thenReturn(music);
//
//        // Chama o método getMusic do BandRepository para obter uma música por ID
//        Music result = bandRepository.getMusic(1L);
//
//        // Verifica se a música retornada corresponde à música esperada
//        assertNotNull(result);
//        assertEquals(1L, result.getId());
//        assertEquals("Example Music", result.getName());
//
//        // Verifica se o RestTemplate foi chamado exatamente uma vez com a URL correta
//        verify(restTemplate, times(1)).getForObject("http://localhost:9090/api/musics/1", Music.class);
//    }
}