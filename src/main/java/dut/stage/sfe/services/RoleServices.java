package dut.stage.sfe.services;

import java.util.List;

import dut.stage.sfe.model.Roles;

public interface RoleServices {

    Roles findRoleById(int id);  
    List<Roles> findAll();
}
