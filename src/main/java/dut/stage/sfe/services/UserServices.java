package dut.stage.sfe.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dut.stage.sfe.model.User;

public interface UserServices {

    User findByEmailaddress(String emailaddress);

    User findByPhonenumber(String phonenumber);

    void deleteById(int id);

    User findById(int id);

    User findByCin(String cin);

    User updateUser(int id, User vendor);

    void saveVendor(User vendor, String password);

    User addUser(User user, int role_id, String password, int vendor_id);

    List<User> findAllUsers();

    Long getTotalUsers();

    Page<User> findPage(int pageNumber);

    Page<User> findVendorsPage(int pageNumber);

    List<User> searchUsers(String query); 

    List<User> searchVendors(String query); 

    User updateSettings(int id , User user);

}