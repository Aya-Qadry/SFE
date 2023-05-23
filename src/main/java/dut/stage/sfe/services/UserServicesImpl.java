package dut.stage.sfe.services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    private RoleRepository roleRepo ; 

    @Autowired
    private DeliveryAddressServicesImpl deliveryAddressServicesImpl ; 

    @Autowired
    private CustomerServicesImpl customerServicesImpl ; 

    public String generateRandomPassword() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialCharacters = "!@#$%&*()_+-=[]|,./?><";

        String allChars = upperCaseLetters + lowerCaseLetters + numbers + specialCharacters;

        Random random = new Random();

        // Generate a random password with a length of 12 characters
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return password.toString();
    }

    @Override
    public void saveVendor(User vendor , String password) {
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
        if(deliveryAddressServicesImpl.findByUser_Id(id) != null){
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
        user.setEmailaddress(vendor.getEmailaddress());
        user.setPhotos(vendor.getPhotos());

       return repository.save(user);
    }

    public List<User> getUsersByRole(String rolename) {
        return repository.findByRoleName(rolename);
    }

    @Override
    public User addUser(User user , int role_id , String password , int vendor_id) {
        user.setPassword(password);
        Role selectedRole = roleRepo.findById(role_id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid Role ID"));
        user.addRole(selectedRole);
        if(selectedRole.getName().equals("CUSTOMER")){
            repository.save(user);
            Customers customer = new Customers(user);
            customer.setVendor_id(findById(vendor_id));
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
        Pageable pageable = PageRequest.of(pageNumber-1,6);
        return repository.findAll(pageable);
    }


}
