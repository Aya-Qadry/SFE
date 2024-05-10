package dut.stage.sfe.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int product_id;

    @Column(nullable = false, length = 128)
    // @NotBlank(message = "Must upload an image of your product")
    private String photos;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull(message = "Choose a correspondant category")
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false) 
    private Category category_id;


    @NotEmpty(message = "Fill the description field")
    private String description;

    @NotEmpty(message = "Fill the name field")
    private String name; 

    @NotEmpty(message = "Fill the product's code field")
    private String product_code;

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || product_id == 0)
            return null;
        return "/product-photos/" + product_id + "/" + photos;
    }

    // @Override
    // public boolean equals(Object obj) {
    //     if (this == obj)
    //         return true;
    //     if (obj == null || getClass() != obj.getClass())
    //         return false;
    //     Product product = (Product) obj;
    //     return product_id == product.product_id;
    // }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(product_id);
    // }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public Category getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Category category_id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

}
