package dut.stage.sfe.services;

import java.util.Collection;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import dut.stage.sfe.model.Roles;
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

    private Roles role; 
    public MyUserDetails(User user) {
        System.out.println("Calling userDetailsService() method");
        this.user=user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();        
        // int roleId = ((MyUserDetails) authentication.getPrincipal()).user.getRole().getId();
        // authorities.add(new SimpleGrantedAuthority(role.getName()));
        int roleId = user.getRole().getId()  ;
        if(roleId == 1 ){
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }else if(roleId == 2 ){
            authorities.add(new SimpleGrantedAuthority("VENDOR"));
        }else if(roleId == 3)  {
            authorities.add(new SimpleGrantedAuthority("CUSTOMER"));
        }
        System.out.println("----------------------"+authorities);
        return authorities ; 
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
