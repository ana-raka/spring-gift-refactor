package gift.dto.categoryDto;

import gift.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponseDto toCategoryResponseDto(Category category) {
        return new CategoryResponseDto(category.getId(), category.getCategoryName());
    }
}
