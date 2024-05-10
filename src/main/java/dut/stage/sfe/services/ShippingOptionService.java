package dut.stage.sfe.services;

import java.util.List;

import org.springframework.data.domain.Page;

import dut.stage.sfe.model.ShippingOptions;

public interface ShippingOptionService {
    ShippingOptions add(ShippingOptions shipping);

    List<ShippingOptions> findAllOptions();

    void deleteById(int id);

    ShippingOptions findById(int id);

    List<ShippingOptions> searchOptions(String query);

    Page<ShippingOptions> findPage(int pageNumber);
}
