package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.ShippingOptions;

@Repository
public interface ShippingRepository extends JpaRepository<ShippingOptions,Integer> {
    @Query("Select s FROM ShippingOptions s WHERE LOWER(s.name) LIKE %:query% OR LOWER(s.description) LIKE %:query% ")
    List<ShippingOptions> searchOption(String query);
}
