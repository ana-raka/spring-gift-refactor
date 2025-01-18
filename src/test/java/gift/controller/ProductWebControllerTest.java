package gift.controller;

import gift.model.product.Product;
import gift.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductWebControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductWebController productWebController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productWebController).build();
    }

    @Test
    void showProductListPage() throws Exception {
        // Given
        Product mockProduct1 = mock(Product.class);
        Product mockProduct2 = mock(Product.class);

        List<Product> mockProducts = List.of(mockProduct1, mockProduct2);
        when(productService.getAllProducts(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(mockProducts, PageRequest.of(0, 20), 2));

        // When & Then
        mockMvc.perform(get("/products?page=0"))
                .andExpect(status().isOk())
                .andExpect(view().name("productManage"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", mockProducts));

        verify(productService, times(1)).getAllProducts(any(PageRequest.class));
    }

    @Test
    void showEditProductPage() throws Exception {
        // Given
        Long productId = 1L;
        Product mockProduct = mock(Product.class);

        // given
        when(productService.selectProduct(productId)).thenReturn(mockProduct);

        // When & Then
        mockMvc.perform(get("/products/" + productId))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", mockProduct));

        verify(productService, times(1)).selectProduct(productId);
    }
}