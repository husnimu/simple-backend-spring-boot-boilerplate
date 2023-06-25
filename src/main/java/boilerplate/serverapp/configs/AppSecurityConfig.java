package boilerplate.serverapp.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
  // private AppUserDetailService appUserDetailService;
  private PasswordEncoder passwordEncoder;

  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // auth.userDetailsService(appUserDetailService).passwordEncoder(passwordEncoder);
  }

  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .disable()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/login")
        .permitAll()
        // .anyRequest()
        // .authenticated()
        .anyRequest()
        .permitAll()
        .and()
        .httpBasic();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
