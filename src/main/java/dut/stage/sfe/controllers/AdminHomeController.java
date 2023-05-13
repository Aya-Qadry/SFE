package dut.stage.sfe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dut.stage.sfe.model.User;
import dut.stage.sfe.services.MyUserDetails;
import dut.stage.sfe.services.UserDetailsServiceImpl;
import dut.stage.sfe.services.UserServicesImpl;

@Controller
@RequestMapping("/adminhome/user")
public class AdminHomeController {
    

    @Autowired
    PasswordEncoder passwordEncoder ;
    
    @Autowired
    UserServicesImpl userServicesImpl ; 
    @GetMapping("/settings")
    public String settings (){
        return "settings";
    }
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("newpassword") String newpassword , @RequestParam("oldpassword") String oldpassword ,Authentication auth){
        MyUserDetails userDetails =  (MyUserDetails) auth.getPrincipal();
        User user = userDetails.getUser() ; 
        
        if(passwordEncoder.matches( oldpassword , user.getPassword())){
            user.setPassword(newpassword);
            userServicesImpl.add(user);
            return "redirect:/avonfruits/users/auth/login";
        }else{
            System.out.println("incorrect old password");
            return "settings";
        }
    }
}
