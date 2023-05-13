package dut.stage.sfe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.RequestRepository;
import dut.stage.sfe.dao.VendorRepository;
import dut.stage.sfe.model.Request;

@Service
public class RequestServicesImpl implements RequestServices{

    @Autowired
    RequestRepository repository ; 

    @Autowired
    VendorRepository vendorRepository ; 

    @Override
    public Request findByEmailaddress(String emailaddress) {
        return repository.findByEmailaddress(emailaddress);
    }

    @Override
    public Request findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Request findByPhonenumber(String phonenumber) {
        return repository.findByPhonenumber(phonenumber);
    }

    @Override
    public void saveRequest(Request request) {
        repository.save(request);
    }

    @Override
    public List<Request> findAll() {
        return repository.findAll() ; 
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    
}
