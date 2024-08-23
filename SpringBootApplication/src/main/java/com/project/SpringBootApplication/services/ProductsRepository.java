package com.project.SpringBootApplication.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.SpringBootApplication.model.Product;

public interface ProductsRepository extends JpaRepository<Product, Integer> {
	

}
