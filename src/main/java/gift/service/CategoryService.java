package gift.service;

import gift.dto.categoryDto.CategoryDto;
import gift.dto.categoryDto.CategoryMapper;
import gift.dto.categoryDto.CategoryResponseDto;
import gift.exception.ValueNotFoundException;
import gift.model.product.Category;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper){
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(categoryMapper::toCategoryResponseDto)
                .collect(Collectors.toList());
    }

    public CategoryResponseDto saveCategory(CategoryDto categoryDto){
        Category category = new Category(categoryDto.getName(), categoryDto.getColor(), categoryDto.getImageUrl(), categoryDto.getDescription());
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponseDto(savedCategory);
    }

    public CategoryResponseDto updateCategory(Long categoryId, CategoryDto categoryDto){
        Category category = getCategoryById(categoryId);
        category.updateCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponseDto(savedCategory);
    }

    public void deleteCategory(Long categoryId){
        if (!categoryRepository.existsById(categoryId)){
            throw new ValueNotFoundException("Category not exists in Database");
        }
        categoryRepository.deleteById(categoryId);
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ValueNotFoundException("Category not exists in Database"));
    }
}
