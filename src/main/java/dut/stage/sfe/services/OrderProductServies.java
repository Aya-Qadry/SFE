package dut.stage.sfe.services;

import java.util.List;

import dut.stage.sfe.model.OrderProducts;
import dut.stage.sfe.model.Stock;

public interface OrderProductServies {
    // List<OrderProducts> findByOrder_id(int order_id);
    void deleteByOrderid(int order_id);

    List<OrderProducts> findByOrderid(int order_id);

    List<OrderProducts> findByStock(Stock stock);

    List<OrderProducts> findAll();

    void deleteByStock(Stock stock);

}
