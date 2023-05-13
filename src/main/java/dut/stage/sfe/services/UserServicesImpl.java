package dut.stage.sfe.services;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.RoleRepository;
import dut.stage.sfe.dao.UserRepository;
import dut.stage.sfe.model.Roles;
import dut.stage.sfe.model.User;
import dut.stage.sfe.model.Vendor;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository mysql;

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

    public void add(User user) {
        // user.setPassword(generateRandomPassword());
        mysql.save(user);
    }

    public User findByEmailaddress(String emailaddress) {
        User ou = mysql.findByEmailaddress(emailaddress);
        return ou;
    }

    @Override
    public User findByPhonenumber(String phonenumber) {
        User us = mysql.findByPhonenumber(phonenumber);
        return us;
    }

    @Override
    public User findById(int id) {
        User userid = mysql.findById(id);
        return userid;
    }

    // @Override
    // public User updateUser(User user) {
    // return mysql.save(user);
    // }

    @Override
    public void deleteUser(int id) {
        mysql.deleteById(id);
    }

    @Override
    public void updateUser(int id, Vendor vendor) {
        id = vendor.getUser().getUser_id();
        System.out.println("in the update user " + id);

        User user = mysql.findById(id);
        user.setFirstname(vendor.getFirstname());
        user.setLastname(vendor.getLastname());
        user.setPhonenumber(vendor.getPhonenumber());
        user.setCin(vendor.getCin());
        user.setEmailaddress(vendor.getEmailaddress());

        mysql.save(user);
    }

}
