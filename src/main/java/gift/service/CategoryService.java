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

    public CategoryResponseDto updateCategory(Long id, CategoryDto categoryDto){
        Category category = getCategoryById(id);
        category.updateCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponseDto(savedCategory);
    }

    public void deleteCategory(Long id){
        if (!categoryRepository.existsById(id)){
            throw new ValueNotFoundException("Category not exists in Database");
        }
        categoryRepository.deleteById(id);
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ValueNotFoundException("Category not exists in Database"));
    }
}
