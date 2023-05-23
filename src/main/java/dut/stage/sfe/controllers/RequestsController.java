package dut.stage.sfe.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dut.stage.sfe.dao.RequestRepository;
import dut.stage.sfe.model.Request;
import dut.stage.sfe.model.Vendor;
import dut.stage.sfe.services.RequestServicesImpl; 
import dut.stage.sfe.services.UserDetailsServiceImpl;

@Controller
@RequestMapping("/adminhome")
public class RequestsController {
    
    @Autowired
    RequestServicesImpl requestServicesImpl ; 

    @Autowired
    RequestRepository repository;


    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl ; 
    
    @GetMapping("/requests")
    public String getRequests(Model model){
        model.addAttribute("requestlist", requestServicesImpl.findAll());
        return "requests";
    }

    @GetMapping("/deleteRequest/{id}")
    public String deleteVendor(@PathVariable("id") int id){
        requestServicesImpl.deleteById(id);
        return "redirect:/adminhome/requests";
    }

    @GetMapping("/enableRequest/{id}")
    public String enableUserRequest(@PathVariable("id") int id) {
        userDetailsServiceImpl.enableUserRequest(id);
        return "redirect:/adminhome/requests"; 
    }
    
    @GetMapping("/showRequest/{id}")
    @ResponseBody
    public Request showRequest(@PathVariable("id") int id) {
        return repository.findById(id);
    }
}
