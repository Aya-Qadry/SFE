package dut.stage.sfe.services;

import java.util.List;

import dut.stage.sfe.model.Role;

public interface RoleServices {

    // Roles findRoleById(int id);  
    List<Role> findAll();
    // Role findById(int role_id);
}
