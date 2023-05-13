package dut.stage.sfe.model;


import jakarta.persistence.*;
import java.lang.Integer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name="user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    private String firstname ; 
    private String lastname ; 
    private String emailaddress ;
    private String phonenumber; 
    private String password ;
    private String cin ; 
    private String gender ; 

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id" , referencedColumnName = "role_id", nullable = false )
    private Roles role;


    public User() {
    }
    public Roles getRole() {
        return role;
    }
    public void setRole(Roles role) {
        this.role = role;
    }
    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }
    public User(Request request) {
        this.firstname = request.getFirstname();
        this.lastname = request.getLastname();
        this.password = request.getPassword();
        this.emailaddress = request.getEmailaddress();
        this.phonenumber = request.getPhonenumber() ; 
        this.cin = request.getCin();
        this.gender = request.getGender() ; 
        // this.roles = Collections.singleton(new Role("ROLE_USER"));
    }
    public String getFirstname() {
        return firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getEmailaddress() {
        return emailaddress;
    }
    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }
    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    public String getPassword() {
        return password;
    }
   
    
}
