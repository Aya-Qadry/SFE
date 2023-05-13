package dut.stage.sfe.services;

import java.nio.ReadOnlyBufferException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.VendorRepository;
import dut.stage.sfe.model.Roles;
import dut.stage.sfe.model.User;
import dut.stage.sfe.model.Vendor;

@Service
public class VendorServicesImpl implements VendorServices{

    @Autowired
    private VendorRepository repository ; 

    @Autowired
    private UserServicesImpl userServices ; 

    @Override
    public List<Vendor> getAllVendors() {
        return repository.findAll() ; 
    }

    @Override
    public void saveVendor(Vendor vendor , String password) {
        
        User existingvendor = new User() ; 
        existingvendor.setFirstname(vendor.getFirstname());
        existingvendor.setLastname(vendor.getLastname());
        existingvendor.setEmailaddress(vendor.getEmailaddress());
        existingvendor.setPhonenumber(vendor.getPhonenumber());
        existingvendor.setCin(vendor.getCin());
        existingvendor.setPassword(password);

        Roles role = new Roles(2    , "VENDOR");
        existingvendor.setRole(role);
        vendor.setPassword(existingvendor.getPassword());

        userServices.add(existingvendor);
        vendor.setUser(existingvendor);
        
        this.repository.save(vendor);
    }

    @Override
    public Vendor getVendorById(int id) {
        Optional<Vendor> opt = repository.findById(id);
        Vendor vendor = new Vendor() ; 
        if(opt.isPresent()){
            vendor = opt.get() ; 
        }else{
            throw new RuntimeException("Vendor with id :"+opt+" not found");
        }
        return vendor ; 
    }

    @Override
    public void deleteVendorByid(int id) {
       this.repository.deleteById(id);
    }

    @Override
    public void updateVendor(int id , Vendor vendor) {
        Vendor existingvendor = this.getVendorById(id);
        System.out.println("in the update vendor method "+ existingvendor.getEmailaddress());
        // User existingvendor = new User() ; 
        existingvendor.setFirstname(vendor.getFirstname());
        existingvendor.setLastname(vendor.getLastname());
        existingvendor.setEmailaddress(vendor.getEmailaddress());
        existingvendor.setPhonenumber(vendor.getPhonenumber());
        existingvendor.setCin(vendor.getCin());
        System.out.println(existingvendor.getUser());

        // userServices.add(existingvendor);
        existingvendor.setUser(existingvendor.getUser());
        // vendor.setUser(existingvendor);
        userServices.updateUser(existingvendor.getUser().getUser_id(), existingvendor);
        
        // repository.save(vendor);

    }

    
   
}
