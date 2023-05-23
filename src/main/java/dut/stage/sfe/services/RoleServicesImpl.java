package dut.stage.sfe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.RoleRepository;
import dut.stage.sfe.model.Role;

@Service
public class RoleServicesImpl implements RoleServices {

    @Autowired
    private RoleRepository repository ;

    @Override
    public List<Role> findAll() {
       return repository.findAll();
    }

    // @Override
    // public Role findById(int role_id) {
    //     Role r = repository.findById(role_id);
    //     return ;
    // } 
    
    // @Override
    // public Roles findRoleById(int id) {
    //     return repository.findById(id);
    // }

}
