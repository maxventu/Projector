package com.itechart.kalenik.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    @Override public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }
}
