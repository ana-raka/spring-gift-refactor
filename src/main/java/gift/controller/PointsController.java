package gift.controller;

import gift.dto.memberDto.MemberDto;
import gift.dto.pointDto.PointDto;
import gift.dto.pointDto.PointResponseDto;
import gift.dto.wishDto.WishDto;
import gift.model.member.LoginMember;
import gift.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/points")
@Tag(name = "Points Management", description = "Points Management API")
@RestController
public class PointsController {
    private final PointService pointService;

    public PointsController(PointService pointService){
        this.pointService = pointService;
    }

    @GetMapping
    @Operation(summary = "회원의 포인트 조회", description = "회원의 포인트를 조회할 때 사용하는 API")
    public ResponseEntity<PointResponseDto> getUserPoints(@LoginMember MemberDto memberDto){
        PointResponseDto pointResponseDto = pointService.getUserPoints(memberDto);
        return ResponseEntity.ok().body(pointResponseDto);
    }

    @PostMapping("/charge")
    @Operation(summary = "회원 계정에 포인트 추가", description = "회원 계정에 포인트를 추가할 때 사용하는 API")
    public ResponseEntity<PointResponseDto> chargePoints(@LoginMember MemberDto memberDto, @RequestBody PointDto pointDto){
        PointResponseDto pointResponseDto = pointService.chargePoints(pointDto);
        return ResponseEntity.ok().body(pointResponseDto);
    }
}
