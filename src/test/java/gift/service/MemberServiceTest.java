package gift.service;

import gift.dto.memberDto.MemberDto;
import gift.dto.memberDto.MemberResponseDto;
import gift.exception.ValueNotFoundException;
import gift.model.Member;
import gift.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("id로 member 조회 시 member가 존재하면 해당 member 반환")
    void getMemberById_WhenMemberExists() {
        //given
        Long memberId = 1L;
        Member member = new Member("test@example.com", "password");
        ReflectionTestUtils.setField(member, "id", memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        //when
        MemberResponseDto result = memberService.getMemberById(memberId);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(memberId);
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    @DisplayName("id로 member 조회 시 member가 존재하지 않으면 예외 발생")
    void getMemberById_WhenMemberDoesNotExist() {
        //given
        Long memberId = 1L;
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        //when & then
        assertThrows(ValueNotFoundException.class, () -> {
            memberService.getMemberById(memberId);
        });

        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    @DisplayName("email로 member 조회 시 member가 존재하면 member를 Optional로 반환")
    void findByEmail_WhenMemberExists() {
        //given
        String testEmail = "test@example.com";
        String testPassword = "password";
        Member member = new Member(testEmail, testPassword);
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));

        //when
        Optional<Member> result = memberService.findByEmail(testEmail);

        //then
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo(testEmail);
        assertThat(result.get().getPassword()).isEqualTo(testPassword);
        verify(memberRepository, times(1)).findByEmail(testEmail);
    }

    @Test
    @DisplayName("email로 member 조회 시 member가 없으면 Optional.empty 반환")
    void findByEmail_WhenMemberDoesNotExist() {
        //given
        String testEmail = "test@example.com";
        when(memberRepository.findByEmail(testEmail)).thenReturn(Optional.empty());

        //when
        Optional<Member> result = memberService.findByEmail(testEmail);

        //then
        assertThat(result).isNotPresent();
        verify(memberRepository, times(1)).findByEmail(testEmail);
    }

    @Test
    @DisplayName("새로운 member 생성")
    void createMember() {
        //given
        String testEmail = "test@example.com";
        String testPassword = "password";
        MemberDto memberDto = new MemberDto(testEmail, testPassword);
        Member savedMember = new Member(memberDto.email(), memberDto.password());
        ReflectionTestUtils.setField(savedMember, "id", 1L);

        when(memberRepository.findByEmail(testEmail)).thenReturn(Optional.empty());
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        //when
        MemberResponseDto result = memberService.createMember(memberDto);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo(memberDto.email());
        verify(memberRepository, times(1)).findByEmail(memberDto.email());
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    @DisplayName("id로 member를 조회 후 업데이트")
    void updateMember_WhenMemberExists() {
        //given
        Long memberId = 1L;
        String testEmail = "test@example.com";
        String newPassword = "newPassword";
        MemberDto memberDto = new MemberDto(testEmail, newPassword);
        Member existingMember = new Member(testEmail, "oldPassword");
        ReflectionTestUtils.setField(existingMember, "id", memberId);

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(existingMember));

        //when
        MemberResponseDto result = memberService.updateMember(memberId, memberDto);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(memberId);
        assertThat(existingMember.isPasswordEqual(newPassword)).isTrue();
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    @DisplayName("id로 member 조회 후 member가 존재하지 않으면 예외 발생")
    void updateMember_WhenMemberDoesNotExist() {
        //given
        Long memberId = 1L;
        String testEmail = "test@example.com";
        String newPassword = "newPassword";
        MemberDto memberDto = new MemberDto(testEmail, newPassword);

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ValueNotFoundException.class, () -> {
            memberService.updateMember(memberId, memberDto);
        });

        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    @DisplayName("id로 등록된 member 삭제")
    void deleteMember_WhenMemberExists() {
        //given
        Long memberId = 1L;
        when(memberRepository.existsById(memberId)).thenReturn(true);

        //when
        memberService.deleteMember(memberId);

        //then
        verify(memberRepository, times(1)).existsById(memberId);
        verify(memberRepository, times(1)).deleteById(memberId);
    }

    @Test
    @DisplayName("id로 등록된 member를 삭제할 때 member가 존재하지 않으면 예외 발생")
    void deleteMember_WhenMemberDoesNotExist() {
        //given
        Long memberId = 1L;
        when(memberRepository.existsById(memberId)).thenReturn(false);

        //when & then
        assertThrows(ValueNotFoundException.class, () -> {
            memberService.deleteMember(memberId);
        });

        verify(memberRepository, times(1)).existsById(memberId);
        verify(memberRepository, never()).deleteById(anyLong());
    }
}