package dut.stage.sfe.services;

import java.util.List;

import org.springframework.data.domain.Page;

import dut.stage.sfe.model.MesurementUnit;

public interface UnitServices {
    MesurementUnit addUnit(MesurementUnit mesurementUnit);

    List<MesurementUnit> findAllUnits();

    void deleteById(int id);

    MesurementUnit findUnitById(int id);

    List<MesurementUnit> searchUnit(String query);

    Page<MesurementUnit> findPage(int pageNumber);
}
