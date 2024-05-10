package dut.stage.sfe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.DeliveryAddressRepository;
import dut.stage.sfe.model.DeliveryAddress;
import dut.stage.sfe.model.User;

@Service
public class DeliveryAddressServicesImpl implements DeliveryAddressServices {

    @Autowired
    private DeliveryAddressRepository repository;

    @Override
    public void saveDeliveryAddress(DeliveryAddress deliveryAddress, User user) {
        deliveryAddress.setUser(user);
        deliveryAddress.setUserid(user.getUser_id());
        repository.save(deliveryAddress);
    }

    @Override
    public DeliveryAddress findByUser_Id(int user_id) {
        return repository.findByUserid(user_id);
    }

    @Override
    public void updateDeliveryAddress(DeliveryAddress deliveryAddress, User user) {
        DeliveryAddress existingAddress = repository.findByUserid(user.getUser_id());
        
        if (existingAddress != null) {
            existingAddress.setCity(deliveryAddress.getCity());
            existingAddress.setAddressline(deliveryAddress.getAddressline());
            existingAddress.setZipcode(deliveryAddress.getZipcode());
            repository.save(existingAddress);
        }
    }

    @Override
    public void deleteByUserid(int user_id) {
        repository.deleteByUserid(user_id);
    }

}
