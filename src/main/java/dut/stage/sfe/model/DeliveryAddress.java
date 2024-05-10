package dut.stage.sfe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "deliveryaddress")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DeliveryAddress {

    @Id
    @Column(name = "deliveryaddress_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deliveryaddress_id ; 

    @Column(name="zipcode")
    @NotEmpty(message = "Fill the zip code field")
    private String zipcode ; 
    
    @NotNull(message = "Select a correspondant city")
    private String city ; 
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @NotEmpty(message = "Fill the address line field")
    private String addressline ;
    
    @Column(name = "user_id")
    private int userid;


    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , referencedColumnName = "user_id",insertable = false, updatable = false )
    private User user ;

    public DeliveryAddress(String city, String zipcode, String addressline, User user) {
        this.city = city;
        this.zipcode = zipcode;
        this.addressline = addressline;
        this.user = user;
    }
    public DeliveryAddress() {
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
    public int getDeliveryaddress_id() {
        return deliveryaddress_id;
    }
    public void setDeliveryaddress_id(int deliveryaddress_id) {
        this.deliveryaddress_id = deliveryaddress_id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    } 

}
