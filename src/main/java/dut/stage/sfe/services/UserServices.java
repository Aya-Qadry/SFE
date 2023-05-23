package dut.stage.sfe.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dut.stage.sfe.model.User;
import dut.stage.sfe.model.Vendor;

public interface UserServices {
    
    User findByEmailaddress(String emailaddress);
    User findByPhonenumber(String phonenumber);
    // void CreateUser(User user);
    // User updateUser(User user);
    void deleteById(int id);
    User findById(int id);
    User updateUser(int id , User vendor);
    void saveVendor (User vendor , String password);
    User addUser(User user , int role_id , String password , int vendor_id);
    List<User> findAllUsers();
    Long getTotalUsers();
    Page<User> findPage(int pageNumber);
}