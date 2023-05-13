package dut.stage.sfe.services;

import java.util.List;

import dut.stage.sfe.model.Vendor;

public interface VendorServices {

    List<Vendor> getAllVendors();
    void saveVendor(Vendor vendor , String password);
    Vendor getVendorById(int id);
    void deleteVendorByid(int id);
    // void saveVendorRequest(Vendor vendor);
    void updateVendor(int id , Vendor vendor);
}
