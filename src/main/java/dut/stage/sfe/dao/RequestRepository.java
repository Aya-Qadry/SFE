package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer> {

    Request findById(int id);
    Request findByEmailaddress(String emailaddress) ; 
    Request findByPhonenumber(String phonenumber) ;
    List<Request> findAll();
}
