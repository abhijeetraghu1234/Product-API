package com.example.Product_api.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductRequest {

    @NotBlank
    private String productName;

    private String createdBy;
    private String modifiedBy;

    public String getProductName() {
         return productName; 
        }

    public void setProductName(String productName) { 
        this.productName = productName;
     }

    public String getCreatedBy() { 
        return createdBy;
     }
    public void setCreatedBy(String createdBy) {
         this.createdBy = createdBy; 
        }
          public String getmodifiedBy() { 
        return modifiedBy;
     }
    public void setmodifiedBy(String modifiedBy) {
         this.modifiedBy = modifiedBy; 
        }
}
