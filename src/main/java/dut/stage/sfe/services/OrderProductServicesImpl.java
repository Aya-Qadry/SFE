package dut.stage.sfe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.OrderProductRepository;
import dut.stage.sfe.model.OrderProducts;
import dut.stage.sfe.model.Stock;

@Service
public class OrderProductServicesImpl implements OrderProductServies{

    @Autowired
    OrderProductRepository repository ;

    // @Override
    // public List<OrderProducts> findByOrder_id(int order_id) {
    //     return repository.findByOrder_id(order_id);
    // }

    @Override
    public void deleteByOrderid(int order_id) {
        repository.deleteByOrderid(order_id);
    }

    @Override
    public List<OrderProducts> findByOrderid(int order_id) {
        return repository.findByOrderid(order_id);
    }

    @Override
    public List<OrderProducts> findByStock(Stock stock) {
        return repository.findByStock(stock);
    }

    @Override
    public List<OrderProducts> findAll() {
        return repository.findAll() ; 
    }

    @Override
    public void deleteByStock(Stock stock) {
        repository.deleteByStock(stock);
    }
    


}
