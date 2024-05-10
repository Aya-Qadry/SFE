package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Order;
import dut.stage.sfe.model.OrderProducts;
import dut.stage.sfe.model.Stock;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProducts, Integer> {
    // List<OrderProducts> findByOrder_id(int order_id);
    void deleteByOrderid(int order_id);

    // List<OrderProducts> findByOrderid(int order_id);
    List<OrderProducts> findByOrderid(int order_id);

    List<OrderProducts> findByOrder(Order o);

    List<OrderProducts> findByStock(Stock stock);

    void deleteByStock(Stock stock);

}
