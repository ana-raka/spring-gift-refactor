package gift.dto.productDto;

import jakarta.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public record ProductDto(@NotEmpty(message = "상품 이름은 필수 입력값입니다.")
                         String name,
                         BigDecimal price,
                         String imageUrl,
                         Long categoryId) { }
