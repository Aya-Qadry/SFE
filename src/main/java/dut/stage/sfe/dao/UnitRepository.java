package dut.stage.sfe.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import dut.stage.sfe.model.MesurementUnit;

public interface UnitRepository extends JpaRepository<MesurementUnit , Integer>{
    
}
