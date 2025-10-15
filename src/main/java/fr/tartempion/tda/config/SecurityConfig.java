package fr.tartempion.tda.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
  @Value("${APP_REDACTEUR_USER:redacteur}")
  private String user;
  @Value("${APP_REDACTEUR_PASS:password}")
  private String pass;
  @Value("${APP_REDACTEUR_PASS_BCRYPT:}")
  private String passBcrypt;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf
                    // On ignore les endpoints SockJS/WebSocket pour ne pas exiger un token CSRF dessus
                    .ignoringRequestMatchers("/ws/**")
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/",
                            "/login",
                            "/favicon.ico",
                            "/css/**",
                            "/images/**",
                            "/js/**",
                            "/webjars/**",
                            "/ws/**",
                            "/actuator/health").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutSuccessUrl("/")
            );
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public UserDetailsService users() {
    String hash = (passBcrypt == null || passBcrypt.isBlank()) ? "{bcrypt}" + new BCryptPasswordEncoder().encode(pass) : passBcrypt;
    return new InMemoryUserDetailsManager(User.withUsername(user).password(hash).roles("REDACTEUR").build());
  }
}
