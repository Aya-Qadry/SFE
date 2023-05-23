package dut.stage.sfe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Customers;

@Repository
public interface CustomerRepository extends JpaRepository<Customers,Integer>{
    
}
