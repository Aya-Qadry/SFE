package dut.stage.sfe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Override
    public ShippingOptions findById(int id) {
        ShippingOptions s = repository.findById(id).orElse(null);
        return s; 
    }

    @Override
    public List<ShippingOptions> searchOptions(String query) {
        return repository.searchOption(query);
    }
    
    @Override
    public Page<ShippingOptions> findPage(int pageNumber) {
       Pageable pageable = PageRequest.of(pageNumber - 1, 6);
        return repository.findAll(pageable);
    }
    
}
