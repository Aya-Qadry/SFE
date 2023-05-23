package dut.stage.sfe.services;

import java.util.List;

import dut.stage.sfe.model.Request;

public interface RequestServices {

    Request findById(int id);
    Request findByEmailaddress(String emailaddress) ; 
    Request findByPhonenumber(String phonenumber) ;
    void saveRequest(Request request);
    List<Request> findAll();
    void deleteById(int id);
    Long getTotalRequests();
}
