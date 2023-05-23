package dut.stage.sfe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
@Entity
@Table(name = "orderproducts")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="orderproducts_id")
    private int orderproducts_id ; 

    @Column(name = "order_id")
    private int orderid ; 

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    private Order order ; 

    @ManyToOne
    @JoinColumn(name = "stock_id" , referencedColumnName = "stock_id", nullable = false )
    private Stock stock_id ; 

    private int quantity ; 

    private float price ;

    
    
    public int getOrderproducts_id() {
        return orderproducts_id;
    }

    public void setOrderproducts_id(int orderproducts_id) {
        this.orderproducts_id = orderproducts_id;
    }

    public Stock getStock_id() {
        return stock_id;
    }

    public void setStock_id(Stock stock_id) {
        this.stock_id = stock_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    } 
}
