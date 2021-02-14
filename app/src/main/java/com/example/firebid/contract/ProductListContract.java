package com.example.firebid.contract;

import com.example.firebid.model.Product;

import java.util.List;

public class ProductListContract {

    public interface View{
        void setProducts(List<Product> products);
    }

    public interface Presenter{
        void loadProducts();
    }
}
