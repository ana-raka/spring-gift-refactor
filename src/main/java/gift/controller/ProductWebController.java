package gift.controller;

import gift.dto.productDto.ProductResponseDto;
import gift.model.product.Product;
import gift.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductWebController {
    private final ProductService productService;

    public ProductWebController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "전체 상품 목록 페이지 조회", description = "등록된 상품 전체 조회 후 View 렌더링 후 반환합니다.")
    public String showProductListPage(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<ProductResponseDto> productPage = productService.getAllProducts(PageRequest.of(page, 20));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "productManage";
    }

    @GetMapping("/{productId}")
    @Operation(summary = "상품 수정 페이지 조회", description = "ProductId에 등록된 상품이 있는 경우 상품을 수정하는 페이지를 반환합니다.")
    public String showEditProductPage(@PathVariable Long productId, Model model) {
        Product product = productService.selectProduct(productId);
        model.addAttribute("product", product);
        return "editProduct";
    }
}
