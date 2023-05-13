package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Roles;

public interface RoleRepository extends CrudRepository<Roles,Integer>{

    Roles findById(int id);
    List<Roles> findAll();

}
