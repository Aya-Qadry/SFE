package dut.stage.sfe.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import dut.stage.sfe.dao.UserRepository;
import dut.stage.sfe.model.User;
import dut.stage.sfe.services.FileUploadUtil;
import dut.stage.sfe.services.MyUserDetails;
import dut.stage.sfe.services.OrderServicesImpl;
import dut.stage.sfe.services.ProductServicesImpl;
import dut.stage.sfe.services.RequestServicesImpl;
import dut.stage.sfe.services.UserServicesImpl;

@Controller
@RequestMapping("/adminhome/user")
public class AdminHomeController {
    

    @Autowired
    PasswordEncoder passwordEncoder ;
    
    @Autowired
    UserRepository userServicesImpl ; 

    @Autowired
    OrderServicesImpl orderServicesImpl ; 

    @Autowired
    UserServicesImpl servicesImpl ; 

    @Autowired
    ProductServicesImpl productServicesImpl ; 

    @Autowired
    RequestServicesImpl requestServicesImpl ; 
    
    @GetMapping("/dashboard")
    public String getDasboard(Model model){
        // model.addAttribute("totalorders", orderServicesImpl.getTotalOrders());
        // model.addAttribute("totalproducts", productServicesImpl.getTotalProducts());
        // model.addAttribute("totalrequests", requestServicesImpl.getTotalRequests());
        // model.addAttribute("totalusers", servicesImpl.getTotalUsers());
        
        return getOnepage(model, 1);
    }

    @GetMapping("/dashboard/{pageNumber}")
    public String getOnepage(Model model , @PathVariable("pageNumber") int currentPage ){
        Page<User> page = servicesImpl.findPage(currentPage);
        int totalPages = page.getTotalPages() ; 
        //total number of all the items in the table
        Long totalItems = page.getTotalElements() ; 
        List<User> users = page.getContent() ; 
        
        model.addAttribute("totalorders", orderServicesImpl.getTotalOrders());
        model.addAttribute("totalproducts", productServicesImpl.getTotalProducts());
        model.addAttribute("totalrequests", requestServicesImpl.getTotalRequests());
        model.addAttribute("totalusers", servicesImpl.getTotalUsers());

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("listusers", users);
        // model.addAttribute("listusers", servicesImpl.findAllUsers());
        return "/dashboard";
    }

     
    @GetMapping("/settings")
    public String settings (Model model , Authentication auth){
        MyUserDetails details = (MyUserDetails) auth.getPrincipal();
        User user ; 
        user = details.getUser();
        model.addAttribute("user", user);
        return "settings";
    }
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("newpassword") String newpassword , @RequestParam("oldpassword") String oldpassword ,Authentication auth){
        MyUserDetails userDetails =  (MyUserDetails) auth.getPrincipal();
        User user = userDetails.getUser() ; 
        
        if(passwordEncoder.matches( oldpassword , user.getPassword())){
            user.setPassword(newpassword);
            userServicesImpl.save(user);
            return "redirect:/avonfruits/users/auth/login";
        }else{
            System.out.println("incorrect old password");
            return "settings";
        }
    }

    @PostMapping("/updateUser")
    public String updateUser(User user2 , Authentication auth , @RequestParam("image") MultipartFile multipartFile)throws IOException{
        MyUserDetails details = (MyUserDetails) auth.getPrincipal();
        User user = details.getUser();

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user2.setPhotos(fileName);
        System.out.println("photos"+user2.getPhotos());
        User savedUser = servicesImpl.updateUser(user.getUser_id(), user2);
        String uploadDir = "users-photos/" + savedUser.getUser_id();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "redirect:/avonfruits/users/auth/login";
    }
    
    
}
