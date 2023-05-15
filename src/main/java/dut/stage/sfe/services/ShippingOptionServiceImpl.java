package dut.stage.sfe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.ShippingRepository;
import dut.stage.sfe.model.ShippingOptions;

@Service
public class ShippingOptionServiceImpl implements ShippingOptionService{

    @Autowired
    ShippingRepository repository ;

    @Override
    public ShippingOptions add(ShippingOptions shipping) {
        return repository.save(shipping);
    }

    @Override
    public List<ShippingOptions> findAllOptions() {
        return repository.findAll();
    }
    
}
