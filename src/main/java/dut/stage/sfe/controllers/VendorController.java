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

import dut.stage.sfe.model.DeliveryAddress;
import dut.stage.sfe.model.User;
import dut.stage.sfe.model.Vendor;
import dut.stage.sfe.services.DeliveryAddressServicesImpl;
import dut.stage.sfe.services.EmailService;
import dut.stage.sfe.services.UserServicesImpl;

@Controller
@RequestMapping("/adminhome")
public class VendorController {
    

    @Autowired
    UserServicesImpl userServices ; 

    @Autowired
    DeliveryAddressServicesImpl addressServices;

    @Autowired
    EmailService emailService ; 


    @GetMapping("/list")
    public String viewListVendors(Model model){
       model.addAttribute("listVendors", userServices.getUsersByRole("VENDOR")) ;
       return "list";
    }

    @GetMapping("/vendorform")
    public String vendorForm(Model model){
        User vendor = new User() ; 
        model.addAttribute("vendor", vendor);
        return "add";
    }

    @PostMapping("/add")
    public String add(User vendor , DeliveryAddress deliveryAddress){
        String to = vendor.getEmailaddress();
        String password = userServices.generateRandomPassword() ; 
        String body = "Hello \n Please set the following code in the password verification portal :  "+password+"\n then proceed to set your own password ";
        emailService.SendEmail(to, body);
        userServices.saveVendor(vendor, password);
        addressServices.saveDeliveryAddress(deliveryAddress,vendor);
        return "redirect:/adminhome";    
    }

    @GetMapping("/updateform/{id}")
    public String updateForm(@PathVariable("id") int id , Model model){
        User vendor = userServices.findById(id);
        DeliveryAddress deliveryAddress = addressServices.findByUser_Id(vendor.getUser_id());
        // System.out.println("+++++++++++++++++++"+deliveryAddress.getCity());
        model.addAttribute("vendor", vendor);
        model.addAttribute("id", id);
        model.addAttribute("deliveryAddress", deliveryAddress);
        return "update";
    }

    @PostMapping("/updateVendor/{id}")
    public String updateVendor(@PathVariable("id") int id , @ModelAttribute("vendor") User user , @ModelAttribute("deliveryAddress") DeliveryAddress deliveryAddress){
        userServices.updateUser(id, user);
        user.setUser_id(user.getUser_id());
        addressServices.updateDeliveryAddress(deliveryAddress, user);
        return "redirect:/adminhome/list" ;
    }

    @GetMapping("/deleteVendor/{id}")
    public String deleteVendor(@PathVariable("id") int id){
        userServices.deleteById(id);
        return "redirect:/adminhome/list";
    }

    @GetMapping("/showVendor/{id}")
    @ResponseBody
    public User showVendor(@PathVariable("id") int id){
        return userServices.findById(id);
    }
    
}
