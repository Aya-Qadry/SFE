package dut.stage.sfe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dut.stage.sfe.model.Stock;

public interface StockRepository extends JpaRepository<Stock,Integer>{

}
