package gift.service;

import gift.dto.categoryDto.CategoryDto;
import gift.dto.categoryDto.CategoryMapper;
import gift.dto.categoryDto.CategoryResponseDto;
import gift.exception.ValueNotFoundException;
import gift.model.product.Category;
import gift.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("전체 카테고리 목록을 CategoryResponseDTO로 매핑 후 반환")
    void getAllCategories() {
        //given
        Category mockCategory1 = mock(Category.class);
        Category mockCategory2 = mock(Category.class);
        CategoryResponseDto mockResCategoryDto1 = mock(CategoryResponseDto.class);
        CategoryResponseDto mockResCategoryDto2 = mock(CategoryResponseDto.class);
        List<Category> mockCategories = List.of(mockCategory1, mockCategory2);

        when(categoryRepository.findAll()).thenReturn(mockCategories);
        when(categoryMapper.toCategoryResponseDto(mockCategory1))
                .thenReturn(mockResCategoryDto1);
        when(categoryMapper.toCategoryResponseDto(mockCategory2))
                .thenReturn(mockResCategoryDto2);

        //when
        List<CategoryResponseDto> result = categoryService.getAllCategories();

        //then
        assertThat(result).hasSize(2);
        assertThat(result.getFirst()).isEqualTo(mockResCategoryDto1);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("새로운 카테고리를 저장")
    void saveCategory() {
        //given
        Category mockSavedCategory = mock(Category.class);
        CategoryDto mockCategoryDto = mock(CategoryDto.class);
        CategoryResponseDto mockResCategoryDto = mock(CategoryResponseDto.class);

        when(categoryRepository.save(any(Category.class))).thenReturn(mockSavedCategory);
        when(categoryMapper.toCategoryResponseDto(mockSavedCategory)).thenReturn(mockResCategoryDto);

        //when
        CategoryResponseDto result = categoryService.saveCategory(mockCategoryDto);

        //then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(mockResCategoryDto);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("등록된 카테고리 정보를 업데이트 후 저장")
    void updateCategory_WhenCategoryExists() {
        //given
        Category mockUpdatedCategory = mock(Category.class);
        Category mockExistingCategory = mock(Category.class);
        CategoryResponseDto mockResCategoryDto = mock(CategoryResponseDto.class);
        CategoryDto mockCategoryDto = mock(CategoryDto.class);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockExistingCategory));
        when(categoryRepository.save(mockExistingCategory)).thenReturn(mockUpdatedCategory);
        when(categoryMapper.toCategoryResponseDto(mockUpdatedCategory)).thenReturn(mockResCategoryDto);

        //when
        CategoryResponseDto result = categoryService.updateCategory(1L, mockCategoryDto);

        //then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(mockResCategoryDto);
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("업데이트 대상 카테고리가 존재하지 않을 경우 예외 발생")
    void updateCategory_WhenCategoryDoesNotExist() {
        //given
        Long categoryId = 1L;
        CategoryDto mockCategoryDto = mock(CategoryDto.class);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        //when & then
        assertThrows(ValueNotFoundException.class, () -> {
            categoryService.updateCategory(categoryId, mockCategoryDto);
        });

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    @DisplayName("등록된 카테고리 삭제")
    void deleteCategory_WhenCategoryExists() {
        //given
        Long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        //when
        categoryService.deleteCategory(categoryId);

        //then
        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    @DisplayName("존재하지 않는 카테고리 삭제 시 예외 발생")
    void deleteCategory_WhenCategoryDoesNotExist() {
        //given
        Long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        //when & then
        assertThrows(ValueNotFoundException.class, () -> {
            categoryService.deleteCategory(categoryId);
        });

        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, never()).deleteById(anyLong());
    }
}