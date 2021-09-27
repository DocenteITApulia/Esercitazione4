package it.apulia.Esercitazione4.apuliaAirport.accessManagement.security;

import it.apulia.Esercitazione4.apuliaAirport.accessManagement.filter.CustomAuthenticationFilter;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.filter.CustomAuthorizationFilter;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.filter.CustomSpecificUserAuthorizationFilter;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.BookingRepository;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

//altro esempio su https://www.bezkoder.com/spring-boot-jwt-auth-mongodb/

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
//TODO da sistemare i path
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable(); //disabilita il cross site request forgery
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        //http.authorizeRequests().anyRequest().permitAll(); //permette a tutti di accedere
        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**").permitAll();
        //NON AUTENTICATO - registrazione passeggero e info tabelloni
        http.authorizeRequests().antMatchers(POST,"/agencymng/passengers/newregistration").permitAll();
        http.authorizeRequests().antMatchers(GET,"/utils/tabellone/**").permitAll();

        //PERMESSI ADMIN
        http.authorizeRequests().antMatchers( "/api/utenti/**", "/api/roles/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers( "/agencymng/passengers/listall","/flights/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/agencymng/passengers/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/agencymng/passengers/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/agencymng/passengers/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/agencymng/bookings/listall").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/agencymng/bookings/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/agencymng/bookings/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/agencymng/bookings/searchbydate").hasAnyAuthority("ROLE_ADMIN");

        //TODO fare check su questa, eventualmente decommentare, dovrebbe bastare l'authenticated sotto
        //http.authorizeRequests().antMatchers(GET, "/agencymng/bookings/**").hasAnyAuthority("ROLE_USER");
        //http.authorizeRequests().antMatchers(GET, "/agencymng/passengers/**").hasAnyAuthority("ROLE_USER");
        //POST dovrebbero potera fare tutti gli autenticati

        http.authorizeRequests().anyRequest().authenticated(); //verificare poi accesso per utenti se non lo specifico
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public FilterRegistrationBean<CustomSpecificUserAuthorizationFilter> authorizationCustomFilter() {
        FilterRegistrationBean<CustomSpecificUserAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CustomSpecificUserAuthorizationFilter(passengerRepository,bookingRepository));

        registrationBean.addUrlPatterns("/agencymng//bookings/personal/**");

        return registrationBean;

    }

    //creato perchè serve come parametro al filtro custom
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
