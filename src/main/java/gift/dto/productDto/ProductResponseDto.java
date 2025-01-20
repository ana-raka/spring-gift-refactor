package gift.dto.productDto;

import java.math.BigDecimal;

public record ProductResponseDto(Long id,
                                 String name,
                                 BigDecimal price,
                                 String imageUrl,
                                 Long categoryId) { }

