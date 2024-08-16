package com.psc.gx2.api;

import com.psc.gx2.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "productAPI", url = "https://rgr3viiqdl8sikgv.public.blob.vercel-storage.com")
public interface ProductAPI {
    @GetMapping("/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json")
    List<Product> getProducts();
}
