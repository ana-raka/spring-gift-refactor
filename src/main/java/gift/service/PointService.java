package gift.service;

import gift.dto.memberDto.MemberDto;
import gift.dto.pointDto.PointDto;
import gift.dto.pointDto.PointResponseDto;
import gift.exception.ValueNotFoundException;
import gift.model.Member;
import gift.model.Point;
import gift.repository.MemberRepository;
import gift.repository.PointRepository;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    public PointService(PointRepository pointRepository, MemberRepository memberRepository){
        this.pointRepository = pointRepository;
        this.memberRepository = memberRepository;
    }

    public PointResponseDto getUserPoints(MemberDto memberDto){
        Member member = memberRepository.findByEmail(memberDto.email()).
                orElseThrow(() -> new ValueNotFoundException("Member not exists in Database"));

        Point points = pointRepository.findByMemberId(member.getId()).
                orElseThrow(() -> new ValueNotFoundException("User's Points not exists in Database"));

        return new PointResponseDto(member.getId(), points.getPoints());
    }

    public PointResponseDto chargePoints(PointDto pointDto){
        Member member = memberRepository.findById(pointDto.MemberId()).
                orElseThrow(() -> new ValueNotFoundException("Member not exists in Database"));

        Point points = pointRepository.findByMemberId(member.getId()).
                orElseThrow(() -> new ValueNotFoundException("User's Points not exists in Database"));
        points.chargePoints(pointDto.points());
        pointRepository.save(points);
        return new PointResponseDto(member.getId(), points.getPoints());
    }
}
