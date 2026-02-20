package com.example.Product_api.entity;
import jakarta.persistence.*;

@Entity
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    private String name;

    public Integer getId() { 
        return id; 
    }
    public void setId(Integer id) { 
        this.id = id;
     }

    public Integer getQuantity() { 
        return quantity; 
    }
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity;
     }

    public Product getProduct() { 
        return product; 
    }
    public void setProduct(Product product) { 
        this.product = product;
     }
  

}
