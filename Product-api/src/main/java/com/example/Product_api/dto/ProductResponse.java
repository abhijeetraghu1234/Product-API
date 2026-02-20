package com.example.Product_api.dto;


public class ProductResponse {

    private Integer id;
    private String productName;

    public ProductResponse(Integer id,String productName){
        this.id=id;
        this.productName=productName;
    }

    public Integer getId()
    { 
        return id; 

    }
    public String getProductName(){ 
        return productName;
     }
}
