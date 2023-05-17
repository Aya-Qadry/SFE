package dut.stage.sfe.services;

import java.util.List;

import dut.stage.sfe.model.ShippingOptions;

public interface ShippingOptionService {
    ShippingOptions add(ShippingOptions shipping);
    List<ShippingOptions> findAllOptions();
    void deleteById(int id);
    ShippingOptions findById(int id);
}
