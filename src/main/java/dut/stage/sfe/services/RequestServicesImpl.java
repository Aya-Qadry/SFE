package dut.stage.sfe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.RequestRepository;
import dut.stage.sfe.model.Request;

@Service
public class RequestServicesImpl implements RequestServices{

    @Autowired
    RequestRepository repository ; 

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

    @Override
    public Long getTotalRequests() {
        return repository.count();
    }

    @Override
    public List<Request> searchRequest(String query) {
        return repository.searchRequest(query);
    }

    @Override
    public Page<Request> findPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 6);
        return repository.findAll(pageable);
    }

    @Override
    public Request findByCin(String cin) {
        return repository.findByCin(cin);
    }

    
}
