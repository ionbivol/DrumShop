package org.online.shop.controller;

import org.apache.commons.io.FilenameUtils;
import org.online.shop.entity.ProductEntity;
import org.online.shop.repository.BrandRepository;
import org.online.shop.repository.CategoryRepository;
import org.online.shop.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping(path = "/web")
public class ProductController {


    private static final String UPLOAD_PATH = "src/main/resources/uploaded";
    private static final String UPLOAD_PATH_RUNTIME = "target/classes/uploaded";
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;


    @GetMapping("/product/list")
    public ModelAndView productList() {
        ModelAndView modelAndView = new ModelAndView("/product/products");
        modelAndView.addObject("productList", productRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/product/add")
    public ModelAndView addProduct() {
        ModelAndView modelAndView = new ModelAndView("/product/products-form");
        modelAndView.addObject("productObject", new ProductEntity());
        modelAndView.addObject("categoryList", categoryRepository.findAll());
        modelAndView.addObject("brandList", brandRepository.findAll());

        return modelAndView;
    }

    @PostMapping("/product/save")
    public ModelAndView saveProduct(
            @ModelAttribute("productObject") ProductEntity product,
            @RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/product/list");

        String fileName = saveFile(file);
        product.setFileName(fileName);

        productRepository.save(product);
        return modelAndView;

    }


    @GetMapping("/product/edit/{productId}")
    public ModelAndView editProduct(@PathVariable Integer productId) {
        ModelAndView modelAndView = new ModelAndView("/product/products-form");
        modelAndView.addObject("productObject", productRepository.findById(productId).get());
//        modelAndView.addObject("categoryList",categoryRepository.findAll());
//        modelAndView.addObject("brandList",brandRepository.findAll());

        return modelAndView;
    }

    @GetMapping("/product/delete/{productId}")
    public ModelAndView deleteProduct(@PathVariable Integer productId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/web/product/list");
        productRepository.deleteById(productId);
        return modelAndView;
    }



    private String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            LOGGER.info("file is empty");
            return "";
        }
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String fileName = generateRandomName()+ "." + extension;
            LOGGER.info("FileName: " + fileName);

            saveFileToResources(UPLOAD_PATH, fileName, file);
            saveFileToResources(UPLOAD_PATH_RUNTIME, fileName, file);

            return fileName;
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage());
            return "";
        }
    }
    private void saveFileToResources(String path, String fileName, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(path);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }

    private String generateRandomName(){
        return UUID.randomUUID().toString();
    }
}
