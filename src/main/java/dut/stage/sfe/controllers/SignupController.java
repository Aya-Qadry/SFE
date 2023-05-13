package dut.stage.sfe.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dut.stage.sfe.model.DeliveryAddress;
import dut.stage.sfe.model.Request;
import dut.stage.sfe.model.Roles;
import dut.stage.sfe.model.User;
import dut.stage.sfe.services.DeliveryAddressServicesImpl;
import dut.stage.sfe.services.RequestServicesImpl;
import dut.stage.sfe.services.UserServicesImpl;

@Controller
// @RequestMapping("/avonfruits/users/auth")
public class SignupController {
    
    @Autowired
    private RequestServicesImpl requestServicesImpl;
 
    @Autowired
    private DeliveryAddressServicesImpl deliveryAddressServicesImpl ; 

    @GetMapping("/signup")
    public String getSignup(Model model){
        Request request = new Request();
        model.addAttribute("request", request);
        DeliveryAddress deliveryAddress = new DeliveryAddress() ;
        model.addAttribute("deliveryaddress", deliveryAddress);
        return "signup"; 
    }

    @PostMapping(value="/signup")
    public void submitSignup(@ModelAttribute("request") Request request , @ModelAttribute("deliveryaddress") DeliveryAddress deliveryAddress,  Model model) {
    
    Request requestbyEmail = requestServicesImpl.findByEmailaddress(request.getEmailaddress());
    Request requestbyPhone = requestServicesImpl.findByPhonenumber(request.getPhonenumber());

    // Request request2 = new Request() ; 

    if (request.getFirstname()==null || request.getFirstname().isBlank()){
        model.addAttribute("message", "Fill the first name field.");
        // return "signup";
    }
    if (request.getLastname()==null || request.getLastname().isBlank()){
        model.addAttribute("message", "Fill the last name field.");
        // return "signup";
    }
    if (request.getEmailaddress()==null || request.getEmailaddress().isBlank()){
        model.addAttribute("message", "Fill the email field.");
        // return "signup";
    }
    if (request.getPhonenumber()==null || request.getPhonenumber().isBlank()){
        model.addAttribute("message", "Fill the phone field.");
        // return "signup";
    }
    if (request.getPassword()==null || request.getPassword().isBlank()){
        model.addAttribute("message", "Fill the password field.");
        // return "signup";
    }
    String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";  
        Pattern pattern = Pattern.compile(regex);   
        Matcher matcher = pattern.matcher(request.getEmailaddress());  
        if(!matcher.matches()){  
            String message="Please use an email like:\n example@gmail.exp";
            model.addAttribute("message", message);
        // return "signup";

        }

        Pattern pattern1=Pattern.compile("^(05|06|07|08)[0-9]{8}");
         Matcher matcher1=pattern1.matcher(request.getPhonenumber());
         if (!matcher1.matches()){
            model.addAttribute("message", "Please use a valid phone number");
        // return "signup";

         }
         Pattern pattern2=Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z]).{6,20}$");
         Matcher matcher2=pattern2.matcher(request.getPassword());
         if (!matcher2.matches()){
            model.addAttribute("message", "The password must contain more than\n6 characters");
        // return "signup";

         }
        if(requestbyEmail!=null){
            model.addAttribute("message", "Someone is using this email.");
        // return "signup";

        }
        if(requestbyPhone!=null){
            model.addAttribute("message", "Someone is using this phone number.");
        // return "signup";
        
        }
        else{
            try {
                
                requestServicesImpl.saveRequest(request);
                
              } catch (Exception e) {
                  e.printStackTrace();
              }
            //   return "login";
        }
    }
}
