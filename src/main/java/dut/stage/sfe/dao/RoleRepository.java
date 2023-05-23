package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Role;
public interface RoleRepository extends JpaRepository<Role,Integer>{

}
