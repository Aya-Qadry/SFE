package dut.stage.sfe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.OrderRepository;
import dut.stage.sfe.model.Order;
import jakarta.transaction.Transactional;

@Service
public class OrderServicesImpl implements OrderServices {

    @Autowired
    OrderRepository repository;

    @Autowired
    OrderProductServicesImpl orderProductServicesImpl;

    @Override
    public List<Order> findAllOrders() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        orderProductServicesImpl.deleteByOrderid(id);
        repository.deleteById(id);
    }

    @Override
    public Order findById(int id) {
       return repository.findById(id) ; 
    }

    @Override
    public Long getTotalOrders() {
        return repository.count();
    }

}
