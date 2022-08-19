/*
package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.services.CustomUserDetailsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    CustomUserDetailsService userService;

    UserDetails userDetails;

    final int id = 1;


    @BeforeEach
    void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
        userDetails = new UserDetails() {

            public int getId() {
                return 1;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singleton(new SimpleGrantedAuthority("user"));
            }

            @Override
            public String getPassword() {
                return "pass123";
            }

            @Override
            public String getUsername() {
                return "jim";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };








    }

    @Test

    final void testGetUser(){

        when(userService.loadUserByUsername(anyString())).thenReturn(userDetails);




        User user =userController.getUserById(id);

        assertNotNull(user);
        assertEquals(id,user.getId());
        assertEquals(userDetails.getUsername(),user.getUsername());
        assertEquals(userDetails.getPassword(),user.getPassword());

    }

}
*/