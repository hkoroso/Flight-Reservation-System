package com.example.airlinereservationsystem.util.security;

import com.example.airlinereservationsystem.util.constant.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;
    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Autowired
    JwtRequestFilter jwtRequestFilter;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/bookFlight/booking/{flightId}").permitAll()
                .antMatchers("/flights/list").permitAll()
                .antMatchers("/searchFlight").permitAll()
                .antMatchers("/registration-form/index").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/form_assets/**").permitAll()
                .antMatchers("/flight_assets/**").permitAll()
                .antMatchers("/flight/**").permitAll()
                .antMatchers("/styles.css").permitAll()
                .antMatchers("/script.js").permitAll()
                .antMatchers("/flights").permitAll()
                .antMatchers("/flights/{id}").permitAll()
                .antMatchers("/flights/airline/{code}").permitAll()
                .antMatchers("/flights/{id}/instances").permitAll()
                .antMatchers("/flights/instances").permitAll()
                .antMatchers("/flights/instances/{id}").permitAll()
                .antMatchers("/admin/flights").permitAll()
                .antMatchers("/admin/flights/{id}/instances").permitAll()
                .antMatchers("/flights/{id}/instances/{instanceId}").permitAll()
                .antMatchers("/admin/flights/{id}").permitAll()

                .antMatchers("/admin/add-role").hasAnyAuthority(Roles.ROLE_USER.toString())
                .antMatchers("/airports", "/airlines").permitAll()
                .antMatchers("/addresses").permitAll()
                .antMatchers("/admin/add-role").hasAnyAuthority(Roles.ROLE_ADMIN.toString())
                .antMatchers("/addresses/*").permitAll()
                .antMatchers("/admin/airlines/{airlineCode}").permitAll()
                .antMatchers("/admin/airports/{airportCode}").permitAll()
                .antMatchers("/airports/{airportCode}/*").permitAll()
                .antMatchers("/airports/*", "/airlines/*").permitAll()
                .antMatchers("/users").permitAll()

                .antMatchers(HttpMethod.POST, "/reservations").hasAnyAuthority(Roles.ROLE_AGENT.toString(), Roles.ROLE_USER.toString() )
                .antMatchers(HttpMethod.POST, "/reservations/confirm").hasAnyAuthority(Roles.ROLE_AGENT.toString(), Roles.ROLE_USER.toString() )
                .antMatchers(HttpMethod.GET, "/reservations/").hasAnyAuthority(Roles.ROLE_AGENT.toString(), Roles.ROLE_USER.toString() )
                .antMatchers(HttpMethod.GET,"/reservations/{id}").hasAnyAuthority(Roles.ROLE_AGENT.toString(), Roles.ROLE_USER.toString() )
                .antMatchers(HttpMethod.DELETE,"/reservations/{id}").hasAnyAuthority(Roles.ROLE_AGENT.toString(), Roles.ROLE_USER.toString() )
                .antMatchers(HttpMethod.GET,"/reservations/tickets/{id}").hasAnyAuthority(Roles.ROLE_AGENT.toString(), Roles.ROLE_USER.toString() )

                .antMatchers("/airports", "/airlines").hasAnyAuthority(Roles.ROLE_USER.toString()).
                antMatchers("/airports", "/airlines","/airlines/{code}").permitAll().
                antMatchers("/login").permitAll().
                anyRequest().authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
