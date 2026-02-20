package com.example.Product_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Product_api.entity.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item,Integer>  {

}
