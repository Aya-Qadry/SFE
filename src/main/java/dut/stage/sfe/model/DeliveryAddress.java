package dut.stage.sfe.model;

import jakarta.persistence.*;


@Entity
@Table(name = "deliveryaddress")
public class DeliveryAddress {

    @Id
    @Column(name = "deliveryaddress_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deliveryaddress_id ; 

    @Column(name="zipcode")
    private String zipcode ; 
    
    private String city ; 
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    private String addressline ;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , referencedColumnName = "user_id", nullable = false )
    private User user_id ;

    public DeliveryAddress(String city, String zipcode, String addressline, User user_id) {
        this.city = city;
        this.zipcode = zipcode;
        this.addressline = addressline;
        this.user_id = user_id;
    }
    public DeliveryAddress() {
    }

    public User getUser_id() {
        return user_id;
    }
    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }
    
    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public String getAddressline() {
        return addressline;
    }
    public void setAddressline(String addressline) {
        this.addressline = addressline;
    } 

}
