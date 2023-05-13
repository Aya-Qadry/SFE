package dut.stage.sfe.services;

import dut.stage.sfe.model.Roles;
import dut.stage.sfe.model.User;
import dut.stage.sfe.model.Vendor;

public interface UserServices {
    
    User findByEmailaddress(String emailaddress);
    User findByPhonenumber(String phonenumber);
    // void CreateUser(User user);
    // User updateUser(User user);
    void deleteUser(int id);
    User findById(int id);
    void updateUser(int id , Vendor vendor);
}
