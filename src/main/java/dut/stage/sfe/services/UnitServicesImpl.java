package dut.stage.sfe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.UnitRepository;
import dut.stage.sfe.model.MesurementUnit;

@Service
public class UnitServicesImpl implements UnitServices {

    @Autowired
    UnitRepository repository ;

    @Override
    public MesurementUnit addUnit(MesurementUnit mesurementUnit) {
       return repository.save(mesurementUnit);
    }

    @Override
    public List<MesurementUnit> findAllUnits() {
        return repository.findAll();
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);        
    }

    @Override
    public MesurementUnit findUnitById(int id) {
        MesurementUnit unit = repository.findById(id).orElse(null); 
        return unit;
    }
    
}
