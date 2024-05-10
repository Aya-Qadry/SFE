package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Category;
import dut.stage.sfe.model.Customers;
import dut.stage.sfe.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{
    Order findById(int id);

    List<Order> findByCustomer(Customers customer); 

    @Query("SELECT o FROM Order o WHERE LOWER(o.customer.firstname) LIKE %:query% OR LOWER(o.customer.lastname) LIKE %:query% OR LOWER(o.shipping.name) LIKE %:query%") 
    List<Order> searchOrder(String query);
}
