package com.sparta.msa_exam.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.msa_exam.product.entity.Product;
import com.sparta.msa_exam.product.valueobject.Name;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	boolean existsByName(Name name);
}
