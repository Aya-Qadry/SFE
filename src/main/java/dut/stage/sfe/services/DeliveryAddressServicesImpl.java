package dut.stage.sfe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.DeliveryAddressRepository;
import dut.stage.sfe.model.DeliveryAddress;
import dut.stage.sfe.model.User;
import dut.stage.sfe.model.Vendor;

@Service
public class DeliveryAddressServicesImpl implements DeliveryAddressServices{

    @Autowired
    private DeliveryAddressRepository repository ; 

    @Override
    public void saveDeliveryAddress(DeliveryAddress deliveryAddress , Vendor vendor) {
        deliveryAddress.setUser_id(vendor.getUser());
        repository.save(deliveryAddress);
    }

    // @Override
    // public DeliveryAddress findByUser_Id(int user_id) {
    //     return repository.findByUser_id(user_id);
    // }

    

   
    
}
