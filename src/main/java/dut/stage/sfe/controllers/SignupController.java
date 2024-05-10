package dut.stage.sfe.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dut.stage.sfe.model.DeliveryAddress;
import dut.stage.sfe.model.Request;
import dut.stage.sfe.model.User;
import dut.stage.sfe.services.DeliveryAddressServicesImpl;
import dut.stage.sfe.services.RequestServicesImpl;
import dut.stage.sfe.services.UserServicesImpl;
import jakarta.validation.Valid;

@Controller
// @RequestMapping("/avonfruits/users/auth")
public class SignupController {

    @Autowired
    RequestServicesImpl requestServicesImpl;

    @Autowired
    UserServicesImpl userServicesImpl;

    @GetMapping("/signup")
    public String getSignup(Model model) {
        Request request = new Request();
        model.addAttribute("request", request); 
        return "signup";
    }

    @PostMapping(value = "/signup")
    public String submitSignup(@Valid @ModelAttribute("request") Request request, BindingResult result, Model model) {

        Pattern pattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&*()_\\-\\[\\]{}|,./?><]).{8,20}");
        Matcher matcher = pattern.matcher(request.getPassword());
        if (!matcher.find()) { 
            return "signup";
        }
        User existingEmail = userServicesImpl.findByEmailaddress(request.getEmailaddress());
        Request existingReqEmail = requestServicesImpl.findByEmailaddress(request.getEmailaddress());
        if (existingEmail != null ||  existingReqEmail != null) {
            model.addAttribute("email", "This email already exists");
            return "signup";
        } 

        User existingPhone = userServicesImpl.findByPhonenumber(request.getPhonenumber());
        Request existingReqPhone = requestServicesImpl.findByPhonenumber(request.getPhonenumber());
        if(existingPhone != null || existingReqPhone!=null){
            model.addAttribute("phone", "This phone number already exists");
            return "signup";
        }

        User existingCin = userServicesImpl.findByCin(request.getCin());
        Request existingReqCin = requestServicesImpl.findByCin(request.getCin());
        if(existingCin!=null || existingReqCin!=null){
            model.addAttribute("cin", "This cin already exists");
            return "signup";
        }
 
        if (result.hasErrors()) { 
            return "signup";
        } 
        requestServicesImpl.saveRequest(request); 
        return "redirect:/avonfruits/users/auth/login";
    }
}
