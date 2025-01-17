package gift.controller;

import gift.dto.categoryDto.CategoryDto;
import gift.dto.categoryDto.CategoryResponseDto;
import gift.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category Management", description = "Category Management API")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "전체 카테고리 리스트 조회", description = "전체 카테고리 목록을 조회할 때 사용하는 API")
    public ResponseEntity<List<CategoryResponseDto>> getCategoryList() {
        List<CategoryResponseDto> categoryList = categoryService.getAllCategories();
        return ResponseEntity.ok(categoryList);
    }

    @PostMapping
    @Operation(summary = "새로운 카테고리 추가", description = "카테고리를 추가할 때 사용하는 API")
    public ResponseEntity<CategoryResponseDto> addNewCategory(@RequestBody CategoryDto categoryDto) {
        CategoryResponseDto categoryResponseDto = categoryService.saveCategory(categoryDto);
        return ResponseEntity.ok().body(categoryResponseDto);
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "카테고리 정보 수정", description = "카테고리를 수정할 때 사용하는 API")
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDto categoryDto) {
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(categoryId,categoryDto);
        return ResponseEntity.ok().body(categoryResponseDto);
    }

}
