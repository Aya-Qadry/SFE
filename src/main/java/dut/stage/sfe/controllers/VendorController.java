package dut.stage.sfe.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dut.stage.sfe.model.DeliveryAddress;
import dut.stage.sfe.model.Mail;
import dut.stage.sfe.model.User;
import dut.stage.sfe.services.DeliveryAddressServicesImpl;
import dut.stage.sfe.services.EmailService;
import dut.stage.sfe.services.UserServices;
import dut.stage.sfe.services.UserServicesImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/adminhome")
public class VendorController {

    @Autowired
    UserServicesImpl userServicesImpl;

    @Autowired
    DeliveryAddressServicesImpl addressServices;

    @Autowired
    EmailService emailService;

    @GetMapping("/list")
    public String viewListVendors(Model model) {
        return getOnePage(model, 1);
    }

    @GetMapping("/list/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<User> page = userServicesImpl.findVendorsPage(currentPage);
        int totalPages = page.getTotalPages();
        int totalItems = page.getNumberOfElements();
        List<User> vendors = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("listVendors", vendors);

        return "list";
    }

    @GetMapping("/vendorform")
    public String vendorForm(Model model) {
        User vendor = new User();
        model.addAttribute("vendor", vendor);
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        model.addAttribute("deliveryAddress", deliveryAddress);
        return "add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("vendor") User vendor, BindingResult result,
            @Valid @ModelAttribute("deliveryAddress") DeliveryAddress deliveryAddress, BindingResult result2,
            Model model) {
        String password = userServicesImpl.generateRandomPassword();
        vendor.setPassword(password);
        model.addAttribute("vendor", vendor);
        model.addAttribute("deliveryAddress", deliveryAddress);

        if (result.hasErrors() || result2.hasErrors()) {
            return "add";
        }
        User existingEmail = userServicesImpl.findByEmailaddress(vendor.getEmailaddress());
        if (existingEmail != null) {
            model.addAttribute("email", "This email already exists");
            return "add";
        }

        User existingPhone = userServicesImpl.findByPhonenumber(vendor.getPhonenumber());
        if (existingPhone != null) {
            model.addAttribute("phone", "This phone number already exists");
            return "add";
        }

        User existingCin = userServicesImpl.findByCin(vendor.getCin());
        if (existingCin != null) {
            model.addAttribute("cin", "This cin already exists");
            return "add";
        }

        Mail mail = new Mail();

        mail.setFrom("ayaqadry04@gmail.com");
        mail.setTo(vendor.getEmailaddress());
        mail.setSubject("AvonFruits account");
        mail.setTemplate("addEmail.html");

        Map<String, Object> prop = new HashMap<String, Object>();
        prop.put("fullname", vendor.getFirstname() + ' ' + vendor.getLastname());
        prop.put("password", password);
        mail.setProperties(prop);

        userServicesImpl.saveVendor(vendor, password);
        addressServices.saveDeliveryAddress(deliveryAddress, vendor);

        try {
            emailService.sendHtmlMessage(mail);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "redirect:/adminhome/list";
    }

    @GetMapping("/updateform/{id}")
    public String updateForm(@PathVariable("id") int id, Model model) {
        User vendor = userServicesImpl.findById(id);
        DeliveryAddress deliveryAddress = addressServices.findByUser_Id(vendor.getUser_id());
        model.addAttribute("vendor", vendor);
        model.addAttribute("id", id);
        if (deliveryAddress != null) {
            model.addAttribute("deliveryAddress", deliveryAddress);
            model.addAttribute("add", true);
        }
        return "update";
    }

    @PostMapping("/updateVendor/{id}")
    public String updateVendor(@PathVariable("id") int id, 
            @Valid @ModelAttribute("vendor") User user,BindingResult resultUser,
            @Valid @ModelAttribute("deliveryAddress") DeliveryAddress deliveryAddress, BindingResult addResult,
            Model model) {
        DeliveryAddress deliveryAddress1 = addressServices.findByUser_Id(id);

        String phoneNumber = user.getPhonenumber();
        User existingUser = userServicesImpl.findByPhonenumber(phoneNumber);
        if (existingUser != null && existingUser.getUser_id() != id) {
            model.addAttribute("phone", "Phone number belongs to another user");
            if (deliveryAddress1 == null) {
                model.addAttribute("vendor", user);
                model.addAttribute("id", id);
                System.out.println("here?");
                return "update";
            }
            if (deliveryAddress1 != null ) {
                System.out.println("testing " + deliveryAddress1.getCity());
                model.addAttribute("vendor", user);
                model.addAttribute("id", id);
                model.addAttribute("deliveryAddress", deliveryAddress);
                model.addAttribute("add", true);
                return "update";
            }
        }

        String cin = user.getCin() ; 
        User existingCinUser= userServicesImpl.findByCin(cin);
        if (existingCinUser != null && existingCinUser.getUser_id() != id) {
            model.addAttribute("cin", "Cin belongs to another user");
            if (deliveryAddress1 == null) {
                model.addAttribute("vendor", user);
                model.addAttribute("id", id);
                return "update";
            }
            if (deliveryAddress1 != null ) {
                System.out.println("testing " + deliveryAddress1.getCity());
                model.addAttribute("vendor", user);
                model.addAttribute("id", id);
                model.addAttribute("deliveryAddress", deliveryAddress);
                model.addAttribute("add", true);
                return "update";
            }
        }

        if (resultUser.hasErrors()) {
            if (deliveryAddress1 == null) {
                model.addAttribute("vendor", user);
                model.addAttribute("id", id);
                System.out.println("here?");
                return "update";
            }
            if (deliveryAddress1 != null || addResult.hasErrors()) {
                System.out.println("testing " + deliveryAddress1.getCity());
                model.addAttribute("vendor", user);
                model.addAttribute("id", id);
                model.addAttribute("deliveryAddress", deliveryAddress);
                model.addAttribute("add", true);
                return "update";
            }
        }
        if (deliveryAddress1 != null && addResult.hasErrors()) {
            System.out.println("testing " + deliveryAddress1.getCity());
            model.addAttribute("vendor", user);
            model.addAttribute("id", id);
            model.addAttribute("deliveryAddress", deliveryAddress);
            model.addAttribute("add", true);
            return "update";
        }

        if (deliveryAddress != null) {
            if (deliveryAddress.getCity() == null && deliveryAddress.getZipcode() == null
                    && deliveryAddress.getAddressline() == null) {
                deliveryAddress = null;
            }
        }

        userServicesImpl.updateUser(id, user);
        user.setUser_id(user.getUser_id());
        if (deliveryAddress != null) {
            addressServices.updateDeliveryAddress(deliveryAddress, user);
        }

        return "redirect:/adminhome/list";
    }

    @GetMapping("/deleteVendor/{id}")
    public String deleteVendor(@PathVariable("id") int id) {
        userServicesImpl.deleteById(id);
        return "redirect:/adminhome/list";
    }

    @GetMapping("/showVendor/{id}")
    @ResponseBody
    public User showVendor(@PathVariable("id") int id) {
        return userServicesImpl.findById(id);
    }

    @GetMapping("/vendors/search")
    @ResponseBody
    public List<User> searchUsers(@RequestParam("query") String query) {
        // Call your service method to perform the search and return the filtered data
        List<User> filteredData = userServicesImpl.searchVendors(query);
        return filteredData;
    }

}
