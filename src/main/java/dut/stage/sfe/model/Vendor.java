package dut.stage.sfe.model;

import jakarta.persistence.*;

@Entity
@Table(name="vendors")
public class Vendor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="vendor_id")
    private int vendor_id ;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , referencedColumnName = "user_id", nullable = false )
    private User user;
    
    private String firstname ; 
    private String lastname ; 
    private String emailaddress ;
    private String phonenumber; 
    private String password ;
    private String cin ;
    private String gender ; 
    
    
    public Vendor() {
    }
    public int getVendor_id() {
        return vendor_id;
    }
    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
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
    public void setPassword(String password) {
        this.password = password;
    }
    public String getCin() {
        return cin;
    }
    public void setCin(String cin) {
        this.cin = cin;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Vendor(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.password = user.getPassword();
        this.emailaddress = user.getEmailaddress();
        this.phonenumber = user.getPhonenumber() ; 
        this.cin = user.getCin();
        this.gender = user.getGender() ; 
        this.user = user;
        // this.roles = Collections.singleton(new Role("ROLE_USER"));
    }
}
