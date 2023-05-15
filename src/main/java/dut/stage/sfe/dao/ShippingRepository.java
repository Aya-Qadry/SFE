package dut.stage.sfe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.ShippingOptions;

@Repository
public interface ShippingRepository extends JpaRepository<ShippingOptions,Integer> {
    
}
