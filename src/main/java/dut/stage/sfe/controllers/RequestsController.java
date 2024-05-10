package dut.stage.sfe.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dut.stage.sfe.dao.RequestRepository;
import dut.stage.sfe.model.Mail;
import dut.stage.sfe.model.Request;
import dut.stage.sfe.model.Request;
import dut.stage.sfe.model.User;
import dut.stage.sfe.services.EmailService;
import dut.stage.sfe.services.RequestServicesImpl; 
import dut.stage.sfe.services.UserDetailsServiceImpl;
import jakarta.mail.MessagingException;

@Controller
@RequestMapping("/adminhome")
public class RequestsController {
    
    @Autowired
    RequestServicesImpl requestServicesImpl ; 

    @Autowired
    RequestRepository repository;

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl ; 
    
    @Autowired
    EmailService emailService ; 

    @GetMapping("/requests")
    public String getRequests(Model model){
        return getOnePage(model, 1);
    } 

    @GetMapping("/requests/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage) { 

        Page<Request> page = requestServicesImpl.findPage(currentPage);
        int totalPages = page.getTotalPages();
        // total number of all the items in the table
        Long totalItems = page.getTotalElements();
        List<Request> Requests = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("requestlist", Requests);
        return "requests";
    }

    @GetMapping("/requests/search")
    @ResponseBody
    public List<Request> searchRequest(@RequestParam("query") String query) {
        List<Request> filteredData = requestServicesImpl.searchRequest(query);
        return filteredData;
    }


    @GetMapping("/deleteRequest/{id}")
    public String deleteVendor(@PathVariable("id") int id){
        requestServicesImpl.deleteById(id);
        return "redirect:/adminhome/requests";
    }

    @GetMapping("/enableRequest/{id}")
    public String enableUserRequest(@PathVariable("id") int id) {
        Request vendor = requestServicesImpl.findById(id); 

        Mail mail = new Mail();
        mail.setFrom("ayaqadry04@gmail.com");
        mail.setTo(vendor.getEmailaddress());
        mail.setSubject("AvonFruits account");
        mail.setTemplate("enableEmail.html");

        Map<String, Object> prop = new HashMap<String, Object>();
        prop.put("fullname", vendor.getFirstname() + ' ' + vendor.getLastname()); 
        // prop.put("logo","src/main/resources/static/images/logo.png");
        mail.setProperties(prop);

        try {
            emailService.sendHtmlMessage(mail);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        userDetailsServiceImpl.enableUserRequest(id);
        return "redirect:/adminhome/requests"; 
    }
    
    @GetMapping("/showRequest/{id}")
    @ResponseBody
    public Request showRequest(@PathVariable("id") int id) {
        return repository.findById(id);
    }
}
