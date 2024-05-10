package dut.stage.sfe.services;

import java.util.List;
import java.util.Optional;

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
        customer.setUserid(user);
        repository.save(customer);
    }

    @Override
    public List<Customers> getAllCustomers() {
        return repository.findAll();
    }

    @Override
    public Customers updteCustomer(Customers customer, int user_id) { 
        
        Customers existingCustomer = repository.findById(user_id).orElse(null);

        existingCustomer.setFirstname(customer.getFirstname());
        existingCustomer.setLastname(customer.getLastname());
        existingCustomer.setPhonenumber(customer.getPhonenumber());
        existingCustomer.setCin(customer.getCin());
        existingCustomer.setEmailaddress(customer.getEmailaddress());
        // existingCustomer.setPhotos(customer.getPhotos());

        return repository.save(existingCustomer);
    } 


}
