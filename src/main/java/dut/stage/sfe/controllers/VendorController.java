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

import dut.stage.sfe.model.DeliveryAddress;
import dut.stage.sfe.model.User;
import dut.stage.sfe.model.Vendor;
import dut.stage.sfe.services.DeliveryAddressServicesImpl;
import dut.stage.sfe.services.EmailService;
import dut.stage.sfe.services.UserServicesImpl;
import dut.stage.sfe.services.VendorServicesImpl;

@Controller
@RequestMapping("/adminhome")
public class VendorController {
    
    @Autowired
    VendorServicesImpl vendorServices ; 

    @Autowired
    UserServicesImpl userServices ; 

    @Autowired
    DeliveryAddressServicesImpl addressServices;

    @Autowired
    EmailService emailService ; 

    User user = new User();

    @GetMapping("/list")
    public String viewListVendors(Model model){
       model.addAttribute("listVendors", vendorServices.getAllVendors()) ;
       return "list";
    }

    @GetMapping("/vendorform")
    public String vendorForm(Model model){
        Vendor vendor = new Vendor() ; 
        model.addAttribute("vendor", vendor);
        // DeliveryAddress deliveryAddress = new DeliveryAddress();
        // model.addAttribute("deliveryAddress", deliveryAddress);
        return "add";
    }

    @PostMapping("/add")
    public String saveVendor(@ModelAttribute("vendor") Vendor vendor , @ModelAttribute("deliveryAddress") DeliveryAddress deliveryAddress){
        String to = vendor.getEmailaddress();
        String password = userServices.generateRandomPassword() ; 
        String body = "Hello \n Please set the following code in the password verification portal :  "+password+"\n then proceed to set your own password ";
        emailService.SendEmail(to, body);

        vendorServices.saveVendor(vendor,password);
        System.out.println("in the add method "+vendor.getUser());
        addressServices.saveDeliveryAddress(deliveryAddress,vendor);

        return "redirect:/adminhome";    
    }

    @GetMapping("/updateform/{id}")
    public String updateForm(@PathVariable(value = "id") int id , Model model){
        Vendor vendor = vendorServices.getVendorById(id);
        // DeliveryAddress deliveryAddress = addressServices.findByUser_Id(vendor.getUser().getUser_id());
        // System.out.println("+++++++++++++++++++"+deliveryAddress.getCity());
        model.addAttribute("vendor", vendor);
        model.addAttribute("id", id);
        // model.addAttribute("deliveryAddress", deliveryAddress);
        return "update";
    }

    @PostMapping("/updateVendor/{id}")
    public String updateVendor(@PathVariable("id") int id , @ModelAttribute("vendor") Vendor vendor){
        // Vendor v1 = vendorServices.getVendorById(id); 
        // userServices.updateUser(id);
        vendorServices.updateVendor(id, vendor);
        return "redirect:/adminhome/list" ;
    }

    @GetMapping("/deleteVendor/{id}")
    public String deleteVendor(@PathVariable(value = "id") int id , Model model){
        vendorServices.deleteVendorByid(id);
        return "redirect:/adminhome";
    }

    
}
