//package com.mainacad.config;

//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
//@Profile({"jsp", "rest", "test"})
//public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    // fixed user
//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        PasswordEncoder encoder =
//                PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//        auth.inMemoryAuthentication()
//                .withUser("alex").password(encoder.encode("123456")).roles("USER", "ADMIN");
//    }
//
//    // fixed user
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests().antMatchers("/", "/**").permitAll();
//    }

//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests().antMatchers("/user/auth").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/wrong-login").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("registration").permitAll()
//                .and()
//                .authorizeRequests().antMatchers("/**").hasAnyRole("USER");
//    }
//}
