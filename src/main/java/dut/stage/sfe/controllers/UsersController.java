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
import dut.stage.sfe.dao.CustomerRepository;
import dut.stage.sfe.dao.OrderProductRepository;
import dut.stage.sfe.dao.OrderRepository;
import dut.stage.sfe.dao.UserRepository;
import dut.stage.sfe.model.Customers;
import dut.stage.sfe.model.DeliveryAddress;
import dut.stage.sfe.model.Mail;
import dut.stage.sfe.model.Order;
import dut.stage.sfe.model.Role;
import dut.stage.sfe.model.User;
import dut.stage.sfe.services.CustomerServicesImpl;
import dut.stage.sfe.services.DeliveryAddressServicesImpl;
import dut.stage.sfe.services.EmailService;
import dut.stage.sfe.services.RoleServicesImpl;
import dut.stage.sfe.services.UserServicesImpl;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/adminhome/users")
public class UsersController {

    @Autowired
    RoleServicesImpl rolesServicesImpl;

    @Autowired
    UserServicesImpl userServicesImpl;

    @Autowired
    DeliveryAddressServicesImpl deliveryAddressServicesImpl;

    @Autowired
    EmailService emailService;

    @Autowired
    CustomerServicesImpl customerServicesImpl;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/addUser")
    public String addUserForm(Model model) {
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
    public String adduser(@Valid @ModelAttribute("user") User user, BindingResult result,
            @Valid DeliveryAddress deliveryAddress, BindingResult result1,
            @RequestParam("roleId") int role_id, @RequestParam(value = "vendorId", required = false) Integer vendor_id,
            Model model) {
        List<Role> roles = rolesServicesImpl.findAll();
        model.addAttribute("user", user);
        model.addAttribute("deliveryAddress", deliveryAddress);
        model.addAttribute("roleId", role_id);
        model.addAttribute("roles", roles);
        model.addAttribute("vendors", userServicesImpl.getUsersByRole("VENDOR"));

        String password = userServicesImpl.generateRandomPassword();
        user.setPassword(password);

        System.out.println("vendors" + vendor_id);

        if (vendor_id == null) {
            vendor_id = 0;
        }

        if (result.hasErrors() || result1.hasErrors()) {
            return "adduser";
        }
        User existingEmail = userServicesImpl.findByEmailaddress(user.getEmailaddress());
        if (existingEmail != null) {
            model.addAttribute("email", "This email already exists");
            return "adduser";
        }

        System.out.println("vendors" + vendor_id);
        User existingPhone = userServicesImpl.findByPhonenumber(user.getPhonenumber());
        if (existingPhone != null) {
            model.addAttribute("phone", "This phone number already exists");
            return "adduser";
        }

        User existingCin = userServicesImpl.findByCin(user.getCin());
        if (existingCin != null) {
            model.addAttribute("cin", "This cin already exists");
            return "adduser";
        }

        Mail mail = new Mail();

        mail.setFrom("ayaqadry04@gmail.com");
        mail.setTo(user.getEmailaddress());
        mail.setSubject("AvonFruits account");
        mail.setTemplate("addEmail.html");

        Map<String, Object> prop = new HashMap<String, Object>();
        prop.put("fullname", user.getFirstname() + ' ' + user.getLastname());
        prop.put("password", password);
        prop.put("signature", "Aya Qadry");
        prop.put("location", "Meknes");
        // prop.put("logo","src/main/resources/static/images/logo.png");
        mail.setProperties(prop);

        userServicesImpl.addUser(user, role_id, password, vendor_id);
        user.setUser_id(user.getUser_id());
        deliveryAddressServicesImpl.saveDeliveryAddress(deliveryAddress, user);

        try {
            emailService.sendHtmlMessage(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return "redirect:/adminhome/users/listusers";
    }

    @GetMapping("/listusers")
    public String showUsers(Model model) {
        return getOnePage(model, 1);
    }

    @GetMapping("/listusers/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage) {
        // List<User> users = userServicesImpl.findAllUsers() ;

        Page<User> page = userServicesImpl.findPage(currentPage);
        int totalPages = page.getTotalPages();
        // total number of all the items in the table
        Long totalItems = page.getTotalElements();
        List<User> users = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("userlist", users);
        return "listusers";
    }

    @GetMapping("/deleteUser/{id}")
    @Transactional
    public String delete(@PathVariable("id") int id, Model model) {
        User user = userServicesImpl.findById(id);
        if (user.getRoles().toString().contains("CUSTOMER")) {
            Customers customer = customerRepository.findByUserid(user);
            List<Order> customersOrder = orderRepository.findByCustomer(customer);
            for (Order order : customersOrder) {
                orderProductRepository.deleteByOrderid(order.getOrder_id());
            }
            orderRepository.deleteAll(customersOrder);
            customerRepository.delete(customer);
        }
        if (user.getRoles().toString().contains("VENDOR")) {
            List<Customers> vendorsCustomers = customerRepository.findByVendorid(user);
            for (Customers customers : vendorsCustomers) {
                List<Order> customersOrder = orderRepository.findByCustomer(customers);
                for (Order order : customersOrder) {
                    orderProductRepository.deleteByOrderid(order.getOrder_id());
                }
                orderRepository.deleteAll(customersOrder);
                customerRepository.delete(customers);
                userServicesImpl.deleteById(customers.getUserid().getUser_id());
            }
        }
        userServicesImpl.deleteById(id);
        return "redirect:/adminhome/users/listusers";
    }

    @GetMapping("/getUserErrors/{id}")
    @ResponseBody
    public String getUserInfo(@PathVariable("id") int id) {
        String error = "";
        User user = userServicesImpl.findById(id);
        System.out.println("roles " + user.getRoles().toString());

        if (user.getRoles().toString().contains("CUSTOMER")) {
            Customers customer = customerRepository.findByUserid(user);
            List<Order> customersOrder = orderRepository.findByCustomer(customer);
            if (!customersOrder.isEmpty()) {
                error = "This customer has orders , deleting it would result in deleting orders";
            }
        }

        if (user.getRoles().toString().contains("VENDOR")) {
            List<Customers> vendorsCustomers = customerRepository.findByVendorid(user);
            if (!vendorsCustomers.isEmpty()) {
                error = "This vendor has customer , deleting it would result in deleting their customers and orders";
            }
            for (Customers customers : vendorsCustomers) {
                System.out.println("customer" + customers.getUserid().getUser_id());
            }
        }
        return error;
    }

    @GetMapping("/search")
    @ResponseBody
    public List<User> searchUsers(@RequestParam("query") String query) {
        // Call your service method to perform the search and return the filtered data
        List<User> filteredData = userServicesImpl.searchUsers(query);
        return filteredData;
    }

    @GetMapping("/showUser/{id}")
    @ResponseBody
    public User showuser(@PathVariable("id") int id) {
        return userServicesImpl.findById(id);
    }

    @GetMapping("/updateUser/{id}")
    public String updateForm(@PathVariable("id") int id, Model model) {
        User user = userServicesImpl.findById(id);
        DeliveryAddress deliveryAddress = deliveryAddressServicesImpl.findByUser_Id(user.getUser_id());
        // System.out.println("+++++++++++++++++++"+deliveryAddress.getCity());
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        if (deliveryAddress != null) {
            model.addAttribute("deliveryAddress", deliveryAddress);
            model.addAttribute("add", true);
        }
        return "updateuser";
    }

    @PostMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") int id,
            @Valid @ModelAttribute("user") User user, BindingResult userResult,
            @Valid @ModelAttribute("deliveryAddress") DeliveryAddress deliveryAddress, BindingResult addressResult,
            Model model) {

        DeliveryAddress deliveryAddress1 = deliveryAddressServicesImpl.findByUser_Id(id);

        String phoneNumber = user.getPhonenumber();
        User existingUser = userServicesImpl.findByPhonenumber(phoneNumber);
        if (existingUser != null && existingUser.getUser_id() != id) {
            model.addAttribute("phone", "Phone number belongs to another user");
            if (deliveryAddress1 == null) {
                model.addAttribute("user", user);
                model.addAttribute("id", id);
                return "updateuser";
            }
            if (deliveryAddress1 != null ) {
                model.addAttribute("user", user);
                model.addAttribute("id", id);
                model.addAttribute("deliveryAddress", deliveryAddress);
                model.addAttribute("add", true);
                return "updateuser";
            }
        }

        String cin = user.getCin() ; 
        User existingCinUser= userServicesImpl.findByCin(cin);
        if (existingCinUser != null && existingCinUser.getUser_id() != id) {
            model.addAttribute("cin", "Cin belongs to another user");
            if (deliveryAddress1 == null) {
                model.addAttribute("user", user);
                model.addAttribute("id", id);
                return "updateuser";
            }
            if (deliveryAddress1 != null ) {
                model.addAttribute("user", user);
                model.addAttribute("id", id);
                model.addAttribute("deliveryAddress", deliveryAddress);
                model.addAttribute("add", true);
                return "updateuser";
            }
        }

        if (userResult.hasErrors()) {
            if (deliveryAddress1 == null) {
                model.addAttribute("user", user);
                model.addAttribute("id", id);
                return "updateuser";
            }
            if (deliveryAddress1 != null || addressResult.hasErrors()) {
                model.addAttribute("user", user);
                model.addAttribute("id", id);
                model.addAttribute("deliveryAddress", deliveryAddress);
                model.addAttribute("add", true);
                return "updateuser";
            }
        }


        if (deliveryAddress1 != null && addressResult.hasErrors()) {
            System.out.println("testing " + deliveryAddress1.getCity());
            model.addAttribute("user", user);
            model.addAttribute("id", id);
            model.addAttribute("deliveryAddress", deliveryAddress);
            model.addAttribute("add", true);
            return "updateuser";
        } 

        if (deliveryAddress != null) {
            if (deliveryAddress.getCity()==null&& deliveryAddress.getZipcode()==null && deliveryAddress.getAddressline()==null) {
                deliveryAddress = null; 
            }
        }
        userServicesImpl.updateUser(id, user);
        user.setUser_id(id);

        if (deliveryAddress != null) {
            deliveryAddressServicesImpl.updateDeliveryAddress(deliveryAddress, user);
        }

        return "redirect:/adminhome/users/listusers";
    }

}
