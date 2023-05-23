package dut.stage.sfe.services;

import java.util.List;
import java.util.Optional;

import dut.stage.sfe.model.Order;

public interface OrderServices {
    List<Order> findAllOrders();
    void deleteById(int id);
    Order findById(int id);
    Long getTotalOrders();
    // Order findById(int id);
}
