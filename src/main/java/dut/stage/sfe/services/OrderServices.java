package dut.stage.sfe.services;

import java.util.List;
import java.util.Optional;

import dut.stage.sfe.model.Order;

public interface OrderServices {
    List<Order> findAllOrders();
    void deleteById(int id);
    Optional<Order> findById(int id);
}
