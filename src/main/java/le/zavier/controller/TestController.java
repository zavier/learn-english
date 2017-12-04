package le.zavier.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    static class Product {
        private String name;
        private Integer price;

        public Product(String name, Integer price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {
        List<Product> products = new ArrayList<>();
        products.add(new Product("product1", 100));
        products.add(new Product("product2", 150));
        products.add(new Product("product3", 200));
        products.add(new Product("product14", 200));
        model.addAttribute("products", products);
        return "home";
    }

    @RequestMapping("/upload")
    public void upload() {
    }

    @PostMapping("upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        logger.info("originalFileName : {}", originalFilename);
        String suffixFileName = originalFilename.substring(originalFilename.lastIndexOf("."));
        try {
            file.transferTo(new File("E:\\zheng" + suffixFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "home";
    }
}
