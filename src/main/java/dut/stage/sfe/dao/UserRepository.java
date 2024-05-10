package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailaddress(String emailaddress);

    User findByPhonenumber(String phonenumber);

    User findById(int id);

    User findByCin(String cin);

    void deleteById(int id);

    List<User> findAll();

    @Query("SELECT u FROM User u WHERE u.emailaddress = :emailaddress")
    public User getUserbyUsername(@Param("emailaddress") String email);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findByRoleName(@Param("roleName") String roleName);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    Page<User> findByRoleName(@Param("roleName") String roleName, Pageable pageable);

    @Query("SELECT u FROM User u WHERE LOWER(u.firstname) LIKE %:query% OR LOWER(u.lastname) LIKE %:query% OR LOWER(u.emailaddress) LIKE %:query%")
    List<User> searchByFieldsIgnoreCase(String query);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.id = 2 AND (LOWER(u.firstname) LIKE %:query% OR LOWER(u.lastname) LIKE %:query% OR LOWER(u.emailaddress) LIKE %:query%)")
    List<User> searchVendorsByFieldsIgnoreCase(String query);

}
 