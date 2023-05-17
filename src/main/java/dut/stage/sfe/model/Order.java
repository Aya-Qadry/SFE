package dut.stage.sfe.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order implements Serializable{

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_id;

   
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customers customer_id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_id", referencedColumnName = "shipping_id", nullable = false)
    private ShippingOptions shipping_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliveryaddress_id", referencedColumnName = "deliveryaddress_id", nullable = false)
    private DeliveryAddress deliveryaddress_id;

    @CreationTimestamp
    private LocalDateTime date;

    public Customers getCustomer_id() {
        return customer_id;
    }
    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setCustomer_id(Customers customer_id) {
        this.customer_id = customer_id;
    }

    public ShippingOptions getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(ShippingOptions shipping_id) {
        this.shipping_id = shipping_id;
    }

    public DeliveryAddress getDeliveryaddress_id() {
        return deliveryaddress_id;
    }

    public void setDeliveryaddress_id(DeliveryAddress deliveryaddress_id) {
        this.deliveryaddress_id = deliveryaddress_id;
    }
}
