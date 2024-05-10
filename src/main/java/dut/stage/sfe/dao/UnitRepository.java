package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dut.stage.sfe.model.MesurementUnit;

public interface UnitRepository extends JpaRepository<MesurementUnit, Integer> {

    @Query("SELECT c FROM MesurementUnit c WHERE LOWER(c.name) LIKE %:query% OR LOWER(c.abbreviation) LIKE %:query%")
    List<MesurementUnit> searchMesurementUnit(String query);

}
