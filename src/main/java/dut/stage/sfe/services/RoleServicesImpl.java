package dut.stage.sfe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.RoleRepository;
import dut.stage.sfe.model.Roles;

@Service
public class RoleServicesImpl implements RoleServices {

    @Autowired
    private RoleRepository repository ; 
    
    @Override
    public Roles findRoleById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Roles> findAll(){
        return repository.findAll();
    }
    
}
