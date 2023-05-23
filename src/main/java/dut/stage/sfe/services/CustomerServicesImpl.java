package dut.stage.sfe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.CustomerRepository;
import dut.stage.sfe.model.Customers;
import dut.stage.sfe.model.User;

@Service
public class CustomerServicesImpl implements CustomerServices{
    
    @Autowired
    private CustomerRepository repository ;

    @Override
    public void addCustomer(Customers customer , User user) {
        customer.setUser_id(user);
        repository.save(customer);
    } 


}
