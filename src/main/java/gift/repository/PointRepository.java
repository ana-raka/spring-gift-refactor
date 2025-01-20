package gift.repository;

import gift.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point,Long> {
    Optional<Point> findByMemberId(Long memberId);
}
