package dut.stage.sfe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dut.stage.sfe.dao.RoleRepository;
import dut.stage.sfe.model.DeliveryAddress;
import dut.stage.sfe.model.Role;
import dut.stage.sfe.model.User;
import dut.stage.sfe.services.DeliveryAddressServicesImpl;
import dut.stage.sfe.services.EmailService;
import dut.stage.sfe.services.RoleServicesImpl;
import dut.stage.sfe.services.UserServicesImpl;
import dut.stage.sfe.services.VendorServices;

@Controller
@RequestMapping("/adminhome/users")
public class UsersController {
    
    @Autowired
    RoleServicesImpl rolesServicesImpl; 

    @Autowired
    UserServicesImpl userServicesImpl ; 

    @Autowired
    DeliveryAddressServicesImpl deliveryAddressServicesImpl ; 

    @Autowired
    EmailService emailService ; 

    

    @GetMapping("/addUser")
    public String addUserForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        List<Role> roles = rolesServicesImpl.findAll();
        model.addAttribute("roles", roles);
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        model.addAttribute("deliveryAddress", deliveryAddress);
        model.addAttribute("vendors", userServicesImpl.getUsersByRole("VENDOR"));
        return "adduser";
    }

    @PostMapping("/addUser")
    public String adduser(@ModelAttribute("user") User user , DeliveryAddress deliveryAddress , @RequestParam("roleId") int role_id , @RequestParam("vendorId") int vendor_id ){
        String to = user.getEmailaddress();
        String password = userServicesImpl.generateRandomPassword() ; 
        String body = "Hello \n Please set the following code in the password verification portal :  "+password+"\n then proceed to set your own password ";
        emailService.SendEmail(to, body);
        
        userServicesImpl.addUser(user, role_id , password , vendor_id);
        user.setUser_id(user.getUser_id());
        System.out.println("hello"+user.getUser_id());
        deliveryAddressServicesImpl.saveDeliveryAddress(deliveryAddress, user);
        return "redirect:/adminhome";
    }

    @GetMapping("/listusers")
    public String list (Model model){
        model.addAttribute("userlist", userServicesImpl.findAllUsers());
        return "listusers";
    }

    @GetMapping("/deleteUser/{id}")
    public String delete(@PathVariable("id") int id ){
        userServicesImpl.deleteById(id);
        return "redirect:/adminhome/users/listusers";
    }

    @GetMapping("/showUser/{id}")
    @ResponseBody
    public User showuser(@PathVariable("id") int id){
        return userServicesImpl.findById(id);
    }

    @GetMapping("/updateUser/{id}")
    public String updateForm(@PathVariable("id") int id , Model model){
        User user = userServicesImpl.findById(id); 
        DeliveryAddress deliveryAddress = deliveryAddressServicesImpl.findByUser_Id(user.getUser_id());
        // System.out.println("+++++++++++++++++++"+deliveryAddress.getCity());
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        model.addAttribute("deliveryAddress", deliveryAddress);
        return "updateuser";
    }

    @PostMapping("/updateUser/{id}" )
    public String updateUser(@PathVariable("id") int id , @ModelAttribute("user") User user , @ModelAttribute("deliveryAddress") DeliveryAddress deliveryAddress){
        userServicesImpl.updateUser(id, user);
        user.setUser_id(id);
        deliveryAddressServicesImpl.updateDeliveryAddress(deliveryAddress, user);
        return "redirect:/adminhome/users/listusers" ;
    }


}
