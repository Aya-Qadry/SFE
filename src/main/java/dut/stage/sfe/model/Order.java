package dut.stage.sfe.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_id;

    @Column(name = "customer_id")
    private int customer_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", insertable = false, updatable = false)
    private Customers customer;

    @Column(name = "shipping_id")
    private int shipping_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_id", referencedColumnName = "shipping_id", insertable = false, updatable = false)
    private ShippingOptions shipping;

    @Column(name = "deliveryaddress_id")
    private int deliveryaddress_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliveryaddress_id", referencedColumnName = "deliveryaddress_id", insertable = false, updatable = false)
    private DeliveryAddress deliveryaddress;

    @CreationTimestamp
    private LocalDateTime date;

    public Order() {
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

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public int getShipping_id() {
        return shipping_id;
    }

    public void setShipping_id(int shipping_id) {
        this.shipping_id = shipping_id;
    }

    public ShippingOptions getShipping() {
        return shipping;
    }

    public void setShipping(ShippingOptions shipping) {
        this.shipping = shipping;
    }

    public int getDeliveryaddress_id() {
        return deliveryaddress_id;
    }

    public void setDeliveryaddress_id(int deliveryaddress_id) {
        this.deliveryaddress_id = deliveryaddress_id;
    }

    public DeliveryAddress getDeliveryaddress() {
        return deliveryaddress;
    }

    public void setDeliveryaddress(DeliveryAddress deliveryaddress) {
        this.deliveryaddress = deliveryaddress;
    }
}
