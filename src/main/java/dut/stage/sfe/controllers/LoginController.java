package dut.stage.sfe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dut.stage.sfe.model.User;
import dut.stage.sfe.services.UserServicesImpl;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/avonfruits/users/auth")
public class LoginController {

    @Autowired
    UserServicesImpl userServicesImpl ; 

    // @RequestMapping(value="/login", method = RequestMethod.GET)
    // public String displayLogin(Model model){
    //     model.addAttribute("login" , new Login());
    //     return "login",
    // }
    @GetMapping("/login")
    public String getLoginForm(Model model){
        User user = new User() ; 
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping(value="/login")
    public  void submit(Model model , @ModelAttribute("user") User user) {

        User test = userServicesImpl.findByEmailaddress(user.getEmailaddress());
        // List<User> u = userServicesImpl.findByRoles_Name("ADMIN");
        System.out.println("-----------------------------------------------");
        // System.out.println(u);
        if (user.getEmailaddress()==null || user.getEmailaddress().isBlank()){
            model.addAttribute("message", "Fill the email field.");
        }
        if (user.getPassword()==null || user.getPassword().isBlank()){
            model.addAttribute("message", "Fill the password field.");
        }
        if(test == null) {
            model.addAttribute("message", "User does not exist.");
        }
            
        if(!test.getPassword().equals(user.getPassword())) { 
            model.addAttribute("message", "Incorrect password.");
        }
        // return "login";
    }

    @GetMapping("/verification")
    public String passwordPortal(Model model){
        return "passwordportal";
    }
    @PostMapping("/verification")
    public String passwordPortal(@RequestParam("emailaddress") String emailaddress , @RequestParam("code") String code , Model model){
        User user  = userServicesImpl.findByEmailaddress(emailaddress);
         model.addAttribute("id", user.getUser_id());
        if(user==null){
            model.addAttribute("popup", "email is wrong or doesnt exist");
            System.out.println("wrong email address ");
            return "redirect:/avonfruits/users/auth/passwordportal";
        }else{
            if(user.getPassword().equals(code)){
                int id = user.getUser_id();
                return "passwordportal";
            }else{
                System.out.println("wrong password");
                model.addAttribute("popup", "wrong code");
                return "passwordportal";
            }
        }   
    }

    // @PostMapping("/setpassword/{id}")
    // public String passwordSetting(@RequestParam("password") String password , @PathVariable("id") int id){
        
    // }
}
