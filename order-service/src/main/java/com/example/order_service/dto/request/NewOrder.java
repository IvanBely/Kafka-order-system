package com.example.order_service.dto.request;

import com.example.order_service.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewOrder {
    private String userEmail;
    private List<Product> productList;
}
