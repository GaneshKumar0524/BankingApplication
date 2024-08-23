package com.project.SpringBootApplication.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.project.SpringBootApplication.model.Product;
import com.project.SpringBootApplication.model.ProductDto;
import com.project.SpringBootApplication.services.ProductsRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {
	
	//private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProductsController.class);

	@Autowired
	private ProductsRepository repo;


	@GetMapping({"","/"})
	public String showProductList(Model model) {
		List<Product> products = repo.findAll() ;
		model.addAttribute("products", products) ; 
		return "products/index";
	}

	@GetMapping ("/create")
	public String showCreatePage (Model model) {
		
		ProductDto productDto  =new ProductDto();
		model.addAttribute("productDto", productDto);
		return "products/Createproduct";
	}
	
	@PostMapping ("/create")
	public String createProduct (@Valid @ModelAttribute("productDto")ProductDto productDto ,BindingResult result ,Model model) throws IOException
	{
		if (productDto.getImageFile().isEmpty()) 
		{
			result.addError(new FieldError ("productDto", "imageFile", "The image file is Required"));
		}
		if (result.hasErrors())
		{
			return "products/Createproduct";
		}	
		
		// Save image file
		MultipartFile image = productDto.getImageFile();
		Date createdAt = new Date();
		String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
		String uploadDir = "public/images/";
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try (InputStream inputStream = image.getInputStream()) {
			Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}

		Product product = new Product();
		product.setName(productDto.getName()); 
		product.setBrand(productDto.getBrand());
		product.setCategory(productDto.getCategory());
		product.setDescription(productDto.getDescription());
		product.setCreatedat(createdAt) ;
		product.setImageFileName(storageFileName) ;
		//saving the form
		repo.save(product);
		return"redirect:/products";
	}
	@GetMapping ("/edit")
	public String showEditPage(Model model, @RequestParam int id) 
	{	
		try {
			Product product = repo.findById(id).get();
			
			model.addAttribute("product", product);
		
			ProductDto productDto = new ProductDto();
			productDto.setName(product.getName()); 
			productDto.setBrand(product.getBrand());
			productDto.setCategory(product.getCategory());
			productDto.setPrice(product.getPrice());
			productDto.setDescription(product.getDescription());

			model.addAttribute("productDto", productDto);	
			
		}
		catch(Exception e) {
			System.out.println("Exception"+ " "+ e.getMessage());
			return"redirect:/products";
		}
		return "products/EditProduct";
	}

	@PostMapping("/edit")
	public String UpdateProduct(Model model,@RequestParam int id,@Valid @ModelAttribute("ProductDto") ProductDto ProductDto,BindingResult result) 
	{
		try {
			Product product =repo.findById(id).get();
			model.addAttribute("product",product);
			if(result.hasErrors()) {
				return"redirect:/EditProduct";
			}
			if (!ProductDto.getImageFile().isEmpty()) {
				// Delete old image
				String uploadDir = "public/images/";
				Path oldImagePath = Paths.get(uploadDir + product.getImageFileName());
				try {
					Files.delete(oldImagePath);
				} catch (Exception ex) {
					System.out.println("Exception: " + ex.getMessage());
				}

				// Save new image file
				MultipartFile image = ProductDto.getImageFile();
				Date createdAt = new Date();
				String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

				try (InputStream inputStream = image.getInputStream()) {
					Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
				} 
				product.setImageFileName(storageFileName);
			}	
			product.setName(ProductDto.getName()); 
			product.setBrand(ProductDto.getBrand());
			product.setCategory(ProductDto.getCategory());
			product.setPrice(ProductDto.getPrice());
			product.setDescription(ProductDto.getDescription());
			//saving the form
			repo.save(product);
		}
		catch(Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
		return "redirect:/products";
	}
	@GetMapping("/delete")
	public String DeleteProduct(@RequestParam int id) {
		try {
			Product product = repo.findById(id).get();
			Path imagepath = Paths.get("public/images/" + product.getImageFileName());
			try {
				Files.delete(imagepath);
			}
			catch (Exception e) {
				System.out.println("Exception -" +e.getMessage());
		}
			repo.delete(product);
		} catch (Exception e) {
			System.out.println("Exception -" +e.getMessage());
		}
		return "redirect:/products";
	}
}