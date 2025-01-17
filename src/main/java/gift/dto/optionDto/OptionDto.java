package gift.dto.optionDto;

import jakarta.validation.constraints.NotEmpty;

public record OptionDto(
        @NotEmpty(message = "옵션 이름은 필수 입력값입니다.")
        String optionName,
        int quantity ){ }
