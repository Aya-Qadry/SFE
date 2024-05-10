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
    UserServicesImpl userServicesImpl;

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    // @PostMapping(value = "/login")
    // public String submit(Model model, @ModelAttribute("user") User user) {

    //     User test = userServicesImpl.findByEmailaddress(user.getEmailaddress());
    //     // List<User> u = userServicesImpl.findByRoles_Name("ADMIN");
    //     System.out.println("-----------------------------------------------");
    //     // System.out.println(u);
    //     if (user.getEmailaddress() == null || user.getEmailaddress().isBlank()) {
    //         model.addAttribute("message", "Fill the email field.");
    //         return "login";

    //     }
    //     if (user.getPassword() == null || user.getPassword().isBlank()) {
    //         model.addAttribute("message", "Fill the password field.");
    //         return "login";

    //     }
    //     if (test == null) {
    //         model.addAttribute("message", "User does not exist.");
    //         return "login";

    //     }

    //     // if(!test.getPassword().equals(user.getPassword())) {
    //     // model.addAttribute("message", "Incorrect password.");
    //     // return "login";

    //     // }
    //     return "login";
    // }

}
