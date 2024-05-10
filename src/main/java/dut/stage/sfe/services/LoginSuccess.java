package dut.stage.sfe.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//a class that encapsulates logic and states , will be autodetected through class path scanning , spring would 
//create an instance = > a managed bean
@Component
public class LoginSuccess extends SavedRequestAwareAuthenticationSuccessHandler {

  
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        // TODO Auto-generated method stub
        //In the case of an authentication request with username and password, this would be the username. 
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal() ; 
             
        String redirecturl = request.getContextPath();
        
        if(userDetails.getAuthorities().toString().contains("ADMIN")){
             redirecturl = "/adminhome/user/dashboard";
        }else if(userDetails.getAuthorities().toString().contains("VENDOR")){
            redirecturl = "/vendorhome";
         }else if(userDetails.getAuthorities().toString().contains("CUSTOMER")){
            redirecturl = "/customer";
        } 
 
        response.sendRedirect(redirecturl);
    }
    
}
