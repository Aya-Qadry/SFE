package dut.stage.sfe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.DeliveryAddressRepository;
import dut.stage.sfe.model.DeliveryAddress;
import dut.stage.sfe.model.User;
import dut.stage.sfe.model.Vendor;

public interface DeliveryAddressServices {
    
    void saveDeliveryAddress(DeliveryAddress deliveryAddress ,  User user);
    void updateDeliveryAddress(DeliveryAddress deliveryAddress , User user);
    // Optional<DeliveryAddress> findByZipCode(String zipcode);
    DeliveryAddress findByUser_Id(int user_id);
    void deleteByUserid(int user_id);
}
