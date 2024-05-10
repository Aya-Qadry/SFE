package dut.stage.sfe.services;

import java.util.List;

import dut.stage.sfe.model.Customers;
import dut.stage.sfe.model.User;

public interface CustomerServices {
    void addCustomer(Customers customer, User user);

    List<Customers> getAllCustomers();

    Customers updteCustomer (Customers customer , int customer_id);

    
}
