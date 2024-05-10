package dut.stage.sfe.services;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.CustomerRepository;
import dut.stage.sfe.dao.RoleRepository;
import dut.stage.sfe.dao.UserRepository;
import dut.stage.sfe.model.Customers;
import dut.stage.sfe.model.Role;
import dut.stage.sfe.model.User;
import jakarta.transaction.Transactional;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private DeliveryAddressServicesImpl deliveryAddressServicesImpl;

    @Autowired
    private CustomerServicesImpl customerServicesImpl;

    @Autowired
    CustomerRepository customerRepository;

    public String generateRandomPassword() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialCharacters = "!@#$%&*()_+-=[]|,./?><";

        StringBuilder password = new StringBuilder();

        Random random = new Random(); 

        password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
        password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));
        int remainingLength = 8 - 4;
        for (int i = 0; i < remainingLength; i++) {
            String allChars = upperCaseLetters + lowerCaseLetters + numbers + specialCharacters;
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        List<Character> passwordChars = password.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        Collections.shuffle(passwordChars);
        password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }

    @Override
    public void saveVendor(User vendor, String password) {
        vendor.setPassword(password);
        Role roleUser = roleRepo.findById(2).orElse(null);
        vendor.addRole(roleUser);
        repository.save(vendor);
    }

    public User findByEmailaddress(String emailaddress) {
        User ou = repository.findByEmailaddress(emailaddress);
        return ou;
    }

    @Override
    public User findByPhonenumber(String phonenumber) {
        User us = repository.findByPhonenumber(phonenumber);
        return us;
    }

    @Override
    public User findById(int id) {
        User userid = repository.findById(id);
        return userid;
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        if (deliveryAddressServicesImpl.findByUser_Id(id) != null) {
            System.out.println("found address");
            deliveryAddressServicesImpl.deleteByUserid(id);
        }
        repository.deleteById(id);
    } 

    @Override
    public User updateUser(int id, User vendor) {
        User user = repository.findById(id);
        user.setFirstname(vendor.getFirstname());
        user.setLastname(vendor.getLastname());
        user.setPhonenumber(vendor.getPhonenumber());
        user.setCin(vendor.getCin());
        user.setGender(vendor.getGender());
        user.setEmailaddress(vendor.getEmailaddress());
        user.setPhotos(vendor.getPhotos());
        System.out.println("bruh" + user.getRoles());
        if (user.getRoles().toString().contains("CUSTOMER")) { 

            Customers customer = customerRepository.findByUserid(user); 
            customer.setFirstname(vendor.getFirstname());
            customer.setLastname(vendor.getLastname());
            customer.setPhonenumber(vendor.getPhonenumber());
            customer.setCin(vendor.getCin());
            customer.setEmailaddress(vendor.getEmailaddress());
        }

        return repository.save(user);
    }
    @Override
    public User updateSettings(int id, User user1) {
        User user = repository.findById(id);
        user.setFirstname(user1.getFirstname());
        user.setLastname(user1.getLastname());
        user.setPhonenumber(user1.getPhonenumber());
        user.setCin(user1.getCin());
        user.setEmailaddress(user1.getEmailaddress());
        // user.setPhotos(user1.getPhotos()); 
        return repository.save(user);
    }

    public List<User> getUsersByRole(String rolename) {
        return repository.findByRoleName(rolename);
    }

    @Override
    public User addUser(User user, int role_id, String password, int vendor_id) {
        // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder() ;
        user.setPassword(password);
        Role selectedRole = roleRepo.findById(role_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Role ID"));
        user.addRole(selectedRole);
        if (selectedRole.getName().equals("CUSTOMER")) {
            repository.save(user);
            System.out.println("heeeeeeeee");
            Customers customer = new Customers(user);
            customer.setVendorid(findById(vendor_id));
            customerServicesImpl.addCustomer(customer, user);
        }
        return repository.save(user);
    }

    @Override
    public List<User> findAllUsers() { 
        return repository.findAll();
    }

    @Override
    public Long getTotalUsers() {
        return repository.count();
    }

    @Override
    public Page<User> findPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 6);
        return repository.findAll(pageable);
    }

    @Override
    public Page<User> findVendorsPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 6);
        return repository.findByRoleName("VENDOR", pageable);
    }

    public List<User> searchUsers(String query) {
        return repository.searchByFieldsIgnoreCase(query);
    }

    public List<User> searchVendors(String query) {
        return repository.searchVendorsByFieldsIgnoreCase(query);
    }

    @Override
    public User findByCin(String cin) {
        return repository.findByCin(cin);
    }

}
