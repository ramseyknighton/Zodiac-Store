package com.tts.ecom.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

import com.tts.ecom.Models.Product;
import com.tts.ecom.Services.ProductService;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/product/{id}")
    public String show(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        System.out.println("---------------------------------");
        System.out.println(product);
        String sign = product.getSign();
        String category = product.getCategory();
        List<Product> filtered = productService.findBySignAndOrCategory(sign, category);
        model.addAttribute("products", filtered); // Overrides the @ModelAttribute above.
        model.addAttribute("product", product);
        return "product";
    }

    // TODO: Either implement admin controls or remove these methods.

    @RequestMapping(value = "/product", method = { RequestMethod.POST, RequestMethod.PUT })
    public String createOrUpdate(@Valid Product product) {
        productService.save(product);
        return "redirect:/product/" + product.getId();
    }
}