package dut.stage.sfe.config ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import dut.stage.sfe.services.LoginSuccess;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
   
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    private LoginSuccess loginSuccess ; 

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider() ;
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
     
    @Bean
    public SecurityFilterChain configure(HttpSecurity https) throws Exception{
        https   
                .csrf((csrf)->csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/css/**","/js/**","/images/**","/signup/**","/avonfruits/users/auth/**" ).permitAll()
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/avonfruits/users/auth/login")
                        .usernameParameter("emailaddress")
                        .passwordParameter("password")
                        .successHandler(loginSuccess)
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/do_logout")
                        .logoutSuccessUrl("/login"))
                .authenticationProvider(authenticationProvider());
        return https.build();
    }
}