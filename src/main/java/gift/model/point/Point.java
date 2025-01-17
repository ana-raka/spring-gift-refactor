package gift.model.point;

import gift.model.member.Member;
import jakarta.persistence.*;

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
    private int points;

    protected Point(){
    }

    public Point(Member member, int points){
        this.member = member;
        this.points = points;
    }

    public void chargePoints(Integer addPoint){
        this.points = points + addPoint;
    }

    public Long getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }
}
