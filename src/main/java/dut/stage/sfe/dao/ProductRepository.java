package dut.stage.sfe.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{
    
}
