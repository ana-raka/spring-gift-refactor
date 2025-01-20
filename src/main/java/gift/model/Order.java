package gift.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "option_id")
    private Option option;

    @ManyToOne
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @Column(nullable = false, columnDefinition = "integer COMMENT '주문 수량'")
    private int quantity;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '상품 주문 관련 메시지'")
    private String message;

    public Order() {}

    public Order(Option option,Member member,int quantity,String message){
        this.option = option;
        this.member = member;
        this.quantity = quantity;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Option getOption() {
        return option;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMessage() {
        return message;
    }
}
