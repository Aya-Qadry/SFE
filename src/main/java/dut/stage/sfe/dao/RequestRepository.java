package dut.stage.sfe.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dut.stage.sfe.model.Request;
import dut.stage.sfe.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    Request findById(int id);

    Request findByEmailaddress(String emailaddress);

    Request findByPhonenumber(String phonenumber);

    Request findByCin(String cin);

    List<Request> findAll();

    @Query("SELECT r FROM Request r WHERE LOWER(r.firstname) LIKE %:query% OR LOWER(r.lastname) LIKE %:query% OR LOWER(r.emailaddress) LIKE %:query% OR (r.phonenumber) LIKE %:query% OR LOWER(r.cin) LIKE %:query% " )
    List<Request> searchRequest(String query);
}
