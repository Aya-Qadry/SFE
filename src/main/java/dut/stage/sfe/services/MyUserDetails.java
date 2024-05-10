package dut.stage.sfe.services;

import java.util.Collection;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dut.stage.sfe.model.Role;
import dut.stage.sfe.model.User;

// @Component
public class MyUserDetails implements UserDetails {


    private User user = new User();
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MyUserDetails(User user) {
        System.out.println("Calling userDetailsService() method");
        this.user=user;
    } 

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
         
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmailaddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true ; 
    }

    @Override
    public boolean isAccountNonLocked() {
        return true ; 
    }

    @Override 
    public boolean isCredentialsNonExpired() {
        return true ; 
    }

    @Override
    public boolean isEnabled() {
        return true ; 
    }
    
}
