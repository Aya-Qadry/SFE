package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    
    User findByEmailaddress(String emailaddress);
    User findByPhonenumber(String phonenumber);
    User findById(int id);
    void deleteById(int id);
    List<User> findAll() ;
    @Query("SELECT u FROM User u WHERE u.emailaddress = :emailaddress")
    public User getUserbyUsername(@Param("emailaddress") String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findByRoleName(@Param("roleName") String roleName);

}
