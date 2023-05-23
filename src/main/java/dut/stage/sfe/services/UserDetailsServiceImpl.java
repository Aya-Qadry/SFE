package dut.stage.sfe.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import dut.stage.sfe.dao.RequestRepository;
import dut.stage.sfe.dao.RoleRepository;
import dut.stage.sfe.dao.UserRepository;
import dut.stage.sfe.model.Request;
import dut.stage.sfe.model.Role;
import dut.stage.sfe.model.User;
import dut.stage.sfe.model.Vendor;

@Service("userDetailsService")
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository ;
   
    @Autowired
    private RequestRepository requestRepository ; 

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserbyUsername(email);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        
        return new MyUserDetails(user);
    }
    @Autowired  
    RoleRepository roleRepo;
     
     
    public void registerVendor(User user) {
        Role roleUser = roleRepo.findById(2).orElse(null);
        user.addRole(roleUser);
        System.out.println("lol");
        userRepository.save(user);
    }
    
    public void enableUserRequest(int id) {
        Request request = requestRepository.findById(id);
        User user = new User(request);

        if(request == null){
            throw new UsernameNotFoundException("Could not find request with this email");
        }
        request.setEnabled(true);
        requestRepository.save(request);

        registerVendor(user);
        requestRepository.delete(request);
    }
 
}