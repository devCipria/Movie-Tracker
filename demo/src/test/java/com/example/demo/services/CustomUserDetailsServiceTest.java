package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CustomUserDetailsServiceTest {

    @InjectMocks
    CustomUserDetailsService userService;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    final void testGetUser(){
        User user = new User();
        user.setId(100);
        user.setUsername("lily");
        user.setPassword("pass");
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("test@test.com");
        assertNotNull(userDetails);
        assertEquals("lily",userDetails.getUsername());
    }

    @Test
    final void testGetUser_UsernameNotFoundException(){

        when(userRepository.findByUsername(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                ()->{
                    userService.loadUserByUsername("test@test.com");
                }
        );
    }

}