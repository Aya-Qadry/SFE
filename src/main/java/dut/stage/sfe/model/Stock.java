package dut.stage.sfe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name="stock")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Stock {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="stock_id")
    private int stock_id ; 

    @Column(name = "product_id")
    private int product_id;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private Product product ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id" , referencedColumnName = "unit_id", nullable = false )
    private MesurementUnit unit_id ; 

    private float price ; 
    private int quantity ;

    public String getUnitName() {
        return unit_id.getName();
    }

    public int getStock_id() {
        return stock_id;
    }
    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public MesurementUnit getUnit_id() {
        return unit_id;
    }
    public void setUnit_id(MesurementUnit unit_id) {
        this.unit_id = unit_id;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    } 
    
}
