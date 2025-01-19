package gift.service;

import gift.dto.categoryDto.CategoryDto;
import gift.dto.categoryDto.CategoryMapper;
import gift.dto.categoryDto.CategoryResponseDto;
import gift.exception.ValueNotFoundException;
import gift.model.product.Category;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryResponseDto> getCategoryList(){
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> new CategoryResponseDto(
                        category.getId(),
                        category.getCategoryName(),
                        category.getColor(),
                        category.getImageUrl(),
                        category.getDescription()
                ))
                .collect(Collectors.toList());
    }

    public CategoryResponseDto addNewCategory(CategoryDto categoryDto){
        Category category = new Category(categoryDto.getName(), categoryDto.getColor(), categoryDto.getImageUrl(), categoryDto.getDescription());
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponseDto(savedCategory);
    }

    public CategoryResponseDto updateCategory(Long id, CategoryDto categoryDto){
        Category category = findCategoryById(id);
        category.updateCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponseDto(savedCategory);
    }

    public void deleteCategory(Long id){
        if (!categoryRepository.existsById(id)){
            throw new ValueNotFoundException("Product not exists in Database");
        }
        categoryRepository.deleteById(id);
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ValueNotFoundException("Category not exists in Database"));
    }
}
