package dut.stage.sfe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.OrderRepository;
import dut.stage.sfe.model.Customers;
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

    @Override
    public Page<Order> findPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 6);
        return repository.findAll(pageable);
    }

    @Override
    public List<Order> searchOrders(String query) {
        return repository.searchOrder(query);
    }

   

}
