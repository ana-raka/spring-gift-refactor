package gift.model;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(255) COMMENT '고객 이메일'")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) COMMENT '고객 비밀번호'")
    private String password;

    protected Member() {
    }
  
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public boolean isPasswordEqual(String inputPassword) {
        return password.equals(inputPassword);
    }

    public void updateMemberInfo(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}