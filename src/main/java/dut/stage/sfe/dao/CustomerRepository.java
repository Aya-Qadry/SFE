package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Customers;
import dut.stage.sfe.model.User;

@Repository
public interface CustomerRepository extends JpaRepository<Customers,Integer>{
    Customers findByUserid(User user_id);

    List<Customers> findByVendorid(User vendorid);
}
