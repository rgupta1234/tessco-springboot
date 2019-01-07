package com.tessco.mass_upload.configurations;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ActuatorSecurity extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity request) throws Exception
    {
        request.requestMatcher(EndpointRequest.to("shutdown"))
                .authorizeRequests()
                .anyRequest()
                .hasRole("ADMIN")
                .and().httpBasic();
        request.csrf().disable();
    }
}
