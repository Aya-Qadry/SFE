package dut.stage.sfe.services;

import java.util.List;

import dut.stage.sfe.model.MesurementUnit;

public interface UnitServices {
    MesurementUnit addUnit(MesurementUnit mesurementUnit);
    List<MesurementUnit> findAllUnits();
    void deleteById(int id);
    MesurementUnit findUnitById(int id);
}
