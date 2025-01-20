package gift.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @ManyToOne
    @JoinColumn(nullable = false, name = "category_id")
    private Category category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 15, columnDefinition = "VARCHAR(15) COMMENT '상품명'")
    @Embedded
    private ProductName name;

    @Column(nullable = false, columnDefinition = "DECIMAL(19,4) COMMENT '상품 가격'")
    private BigDecimal price;

    @Column(nullable = false , columnDefinition = "VARCHAR(255) COMMENT '상품 이미지 주소'")
    private String imageUrl;

    protected Product(){
    }

    public Product(Category category, ProductName name, BigDecimal price, String imageUrl){
        this.category = category;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void updateProduct(Product product){
        this.category = product.getCategory();
        this.name = new ProductName(product.getName());
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }

    public Category getCategory() {
        return category;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
