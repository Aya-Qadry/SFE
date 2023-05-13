package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Integer> {

    List<Vendor> findAll();
    
    // void saveVendor(Vendor vendor);
}
