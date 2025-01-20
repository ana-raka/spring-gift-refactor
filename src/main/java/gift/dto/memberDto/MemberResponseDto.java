package gift.dto.memberDto;

public class MemberResponseDto {
    private final Long id;
    private final String email;
    public MemberResponseDto(Long id, String email){
        this.id = id;
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }
}