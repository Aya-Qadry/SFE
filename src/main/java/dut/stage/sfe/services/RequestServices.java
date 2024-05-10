package dut.stage.sfe.services;

import java.util.List;

import org.springframework.data.domain.Page;

import dut.stage.sfe.model.Request;

public interface RequestServices {

    Request findById(int id);
    
    Request findByCin(String cin);

    Request findByEmailaddress(String emailaddress);

    Request findByPhonenumber(String phonenumber);

    void saveRequest(Request request);

    List<Request> findAll();

    void deleteById(int id);

    Long getTotalRequests();

    List<Request> searchRequest(String query);

    Page<Request> findPage(int pageNumber);    
}
