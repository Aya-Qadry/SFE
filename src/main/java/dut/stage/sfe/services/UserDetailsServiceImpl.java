package dut.stage.sfe.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.RequestRepository;
import dut.stage.sfe.dao.UserRepository;
import dut.stage.sfe.dao.VendorRepository;
import dut.stage.sfe.model.Request;
import dut.stage.sfe.model.Roles;
import dut.stage.sfe.model.User;
import dut.stage.sfe.model.Vendor;

@Service("userDetailsService")
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository ;
   
    @Autowired
    private RequestRepository requestRepository ; 
 
    @Autowired
    private VendorRepository vendorRepository ; 

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserbyUsername(email);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        
        return new MyUserDetails(user);
    }

    public void enableUserRequest(int id) {
        Request request = requestRepository.findById(id);

        if(request == null){
            throw new UsernameNotFoundException("Could not find request with this email");
        }
        request.setEnabled(true);
        requestRepository.save(request);

        User user = new User(request);
        Roles role = new Roles(2    , "VENDOR");
        user.setRole(role);
        userRepository.save(user);

        Vendor vendor = new Vendor(user);
        vendorRepository.save(vendor);
        
        requestRepository.delete(request);
    }
 
}