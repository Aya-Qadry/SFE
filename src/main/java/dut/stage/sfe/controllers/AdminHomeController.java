package dut.stage.sfe.controllers;

import java.io.IOException;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import dut.stage.sfe.dao.UserRepository;
import dut.stage.sfe.model.User;
import dut.stage.sfe.services.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/adminhome/user")
public class AdminHomeController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userServicesImpl;

    @Autowired
    OrderServicesImpl orderServicesImpl;

    @Autowired
    UserServicesImpl servicesImpl;

    @Autowired
    ProductServicesImpl productServicesImpl;

    @Autowired
    RequestServicesImpl requestServicesImpl;

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String getDasboard(Model model) { 
        return getOnepage(model, 1);
    }

    @GetMapping("/dashboard/{pageNumber}")
    public String getOnepage(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<User> page = servicesImpl.findPage(currentPage);
        int totalPages = page.getTotalPages();
        // total number of all the items in the table
        Long totalItems = page.getTotalElements();
        List<User> users = page.getContent();

        List<Integer> ordersData = dashboardService.OrdersByMonth();
        List<Integer> customersData = dashboardService.CustomerByMonth();
        List<String> months = Arrays.stream(Month.values())
                .map(Enum::toString)
                .collect(Collectors.toList());

        model.addAttribute("ordersData", ordersData);
        model.addAttribute("customersData", customersData);
        model.addAttribute("months", months);

        model.addAttribute("totalorders", orderServicesImpl.getTotalOrders());
        model.addAttribute("totalproducts", productServicesImpl.getTotalProducts());
        model.addAttribute("totalrequests", requestServicesImpl.getTotalRequests());
        model.addAttribute("totalusers", servicesImpl.getTotalUsers());

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("listusers", users);

        model.addAttribute("productCategoryPercentages", dashboardService.getProductCategoryPercentages());

        return "/dashboard";
    }

    @GetMapping("/settings")
    public String settings(Model model, Authentication auth) {
        MyUserDetails details = (MyUserDetails) auth.getPrincipal();
        User user;
        user = details.getUser();
        model.addAttribute("user", user);
        return "settings";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("newpassword") String newpassword,
            @RequestParam("oldpassword") String oldpassword, Authentication auth, Model model) {
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        if (passwordEncoder.matches(oldpassword, user.getPassword())) {
            user.setPassword(newpassword);
            userServicesImpl.save(user);
            return "redirect:/avonfruits/users/auth/login";
        } else {
            model.addAttribute("error", "incorrect old password");
            return "resetPassword";
        }
    }

    @GetMapping("/resetPassword")
    public String reset() {
        return "resetPassword";
    }

    @PostMapping("/updateUser")
    public String updateUser(@Valid User user2, BindingResult userResult, Authentication auth,
            @RequestParam(value = "image", required = false) MultipartFile multipartFile)
            throws IOException {
        MyUserDetails details = (MyUserDetails) auth.getPrincipal();
        User user = details.getUser();

        if (userResult.hasErrors()) {
            System.out.println("errors " + userResult.getAllErrors());
            return "settings";
        }

        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user2.setPhotos(fileName);
            String uploadDir = "users-photos/" + user.getUser_id();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            // No new image uploaded, retain the existing one
            user2.setPhotos(user.getPhotos());
        }

        User savedUser = servicesImpl.updateSettings(user.getUser_id(), user2);

        return "redirect:/avonfruits/users/auth/login";
    }
}
