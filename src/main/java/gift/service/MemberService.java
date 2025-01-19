package gift.service;

import gift.dto.memberDto.MemberDto;
import gift.dto.memberDto.MemberResponseDto;
import gift.exception.ValueAlreadyExistsException;
import gift.exception.ValueNotFoundException;
import gift.model.member.Member;
import gift.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMemberById(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new ValueNotFoundException("해당 id: "+ memberId + "의 Member를 찾을 수 없습니다."));
        return new MemberResponseDto(member.getId(), member.getEmail());
    }

    @Transactional(readOnly = true)
    public Optional<Member> findByEmail(String email){
        return memberRepository.findByEmail(email);
    }

    public MemberResponseDto createMember(MemberDto memberDto) {
        findByEmail(memberDto.email()).ifPresent(existingMember -> {
            throw new ValueAlreadyExistsException(memberDto.email() + "은 이미 등록된 Email 입니다.");
        });
        Member member = new Member(memberDto.email(),memberDto.password());
        Member savedMember = memberRepository.save(member);
        return new MemberResponseDto(savedMember.getId(), savedMember.getEmail());
    }

    public MemberResponseDto updateMember(Long memberId, MemberDto memberDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new ValueNotFoundException("해당 id: "+ memberId + "의 Member를 찾을 수 없습니다."));
        member.updateMemberInfo(memberDto.password());
        return new MemberResponseDto(member.getId(), member.getEmail());
    }

    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new ValueNotFoundException("해당 id: "+ memberId + "의 Member를 삭제 할 수 없습니다.");
        }
        memberRepository.deleteById(memberId);
    }

/*    public String returnToken(MemberDto memberDto){
        Member member = new Member(memberDto.email(),memberDto.password());
        return jwtTokenProvider.generateToken(member);
    }

    public String loginMember(MemberDto memberDto) {
        Member member = new Member(memberDto.email(),memberDto.password());
        Member registeredMember = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new AuthenticationException("Member not exists in Database"));

        if (member.isPasswordNotEqual(registeredMember.getPassword())){
            throw new AuthenticationException("Incorrect password");
        }

        return jwtTokenProvider.generateToken(member);
    }*/
}
