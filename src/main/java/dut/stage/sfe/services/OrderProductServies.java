package dut.stage.sfe.services;

import java.util.List;

import dut.stage.sfe.model.OrderProducts;

public interface OrderProductServies {
    // List<OrderProducts> findByOrder_id(int order_id);
    void deleteByOrderid(int order_id);
}
