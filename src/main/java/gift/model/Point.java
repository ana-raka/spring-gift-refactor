package gift.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "members_id", nullable = false)
    private Member member;

    @Column(columnDefinition = "integer COMMENT '보유한 포인트'")
    private BigDecimal points;

    protected Point(){
    }

    public Point(Member member, BigDecimal points){
        this.member = member;
        this.points = points;
    }

    public BigDecimal chargePoints(BigDecimal addPoint){
        this.points = points.add(addPoint);
        return points;
    }

    public BigDecimal subtractPoints(BigDecimal subtractPoint){
        this.points = points.subtract(subtractPoint);
        return points;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPoints() {
        return points;
    }
}
