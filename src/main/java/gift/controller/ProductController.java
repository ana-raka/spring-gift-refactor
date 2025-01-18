package gift.controller;

import gift.dto.productDto.ProductDto;
import gift.dto.productDto.ProductResponseDto;
import gift.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/products")
@Controller
@Validated
@Tag(name = "Product Management", description = "Product Management API")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "새로운 상품 등록", description = "상품을 등록할 때 사용하는 API")
    public ResponseEntity<ProductResponseDto> addNewProduct(@Valid @RequestBody ProductDto productDto) {
        ProductResponseDto productResponseDto = productService.addNewProduct(productDto);
        return ResponseEntity.ok().body(productResponseDto);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "등록된 상품을 삭제", description = "등록된 상품을 삭제할 때 사용하는 API")
    public String deleteProduct(@PathVariable Long productId){
        productService.DeleteProduct(productId);
        return "productManage";
    }

    @PutMapping("/{productId}")
    @Operation(summary = "상품을 업데이트", description = "등록된 상품을 업데이트 할 때 사용하는 API")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductDto productDto) {
        ProductResponseDto productResponseDto = productService.updateProduct(productId, productDto);
        return ResponseEntity.ok().body(productResponseDto);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "특정 상품 조회", description = "특정 상품을 조회할 때 사용하는 API")
    public ResponseEntity<ProductResponseDto> getProductById(@Valid @PathVariable Long productId) {
        ProductResponseDto productResponseDto = productService.getProductById(productId);
        return ResponseEntity.ok().body(productResponseDto);
    }
}