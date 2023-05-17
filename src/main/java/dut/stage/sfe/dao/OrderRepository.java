package dut.stage.sfe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{
    
}
