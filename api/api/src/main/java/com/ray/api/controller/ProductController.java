package com.ray.api.controller;

import com.ray.api.dao.ProductRepository;
import com.ray.api.dto.ProductReturnDto;
import com.ray.api.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/products")
@CrossOrigin("http://localhost:3000") //used when client url and backend url is difference
public class ProductController {
    private final ProductRepository productRepository; //define property

    @Autowired
    public ProductController(ProductRepository productRepository) { //inject product from repository to property
        this.productRepository = productRepository;
    }

    //api list product
    @GetMapping //if no params, url = api/products
    //use below
//    public ResponseEntity<List<Product>> getAllProducts() {
//        List<Product> products = productRepository.findAll();
//        return new ResponseEntity<>(products, HttpStatus.OK); //?? response entity
//    }

    public ResponseEntity<List<ProductReturnDto>> getAllProducts() {
        List<Product> products = productRepository.findAll();
//        List<ProductReturnDto> returnDtoList = new ArrayList<>();
//
//        for(Product p : products) {
//            ProductReturnDto productReturnDto = new ProductReturnDto(p);
//            returnDtoList.add(productReturnDto);
//        }

        List<ProductReturnDto> returnDtoList =
                products.stream().map(product -> new ProductReturnDto(product)).collect(Collectors.toList());

        return new ResponseEntity<>(returnDtoList, HttpStatus.OK); //?? response entity
    }

    //api get product by id
    @GetMapping("/{productId}")
    public ResponseEntity<ProductReturnDto> getProductById(@PathVariable("productId") Long productId) {
        Product product = productRepository.findById(productId).get();
        return new ResponseEntity<>(new ProductReturnDto(product), HttpStatus.OK);
    }
}
