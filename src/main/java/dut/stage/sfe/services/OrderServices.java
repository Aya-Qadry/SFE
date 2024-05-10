package dut.stage.sfe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import dut.stage.sfe.model.Customers;
import dut.stage.sfe.model.Order;
import dut.stage.sfe.model.User;

public interface OrderServices {
    List<Order> findAllOrders();

    void deleteById(int id);

    Order findById(int id);

    Long getTotalOrders();
    // Order findById(int id);

    Page<Order> findPage(int pageNumber);

    List<Order> searchOrders(String query);

}
