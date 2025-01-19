package gift.controller;

import gift.config.LoginMemberArgumentResolver;
import gift.dto.memberDto.MemberDto;
import gift.model.wish.Wish;
import gift.service.WishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class WishWebControllerTest {
    private MockMvc mockMvc;

    @Mock
    private WishService wishService;

    @Mock
    private LoginMemberArgumentResolver loginMemberArgumentResolver;

    @InjectMocks
    private WishWebController wishWebController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(wishWebController)
                .setCustomArgumentResolvers(loginMemberArgumentResolver)
                .build();
    }

    @Test
    void getAllWishes() throws Exception {
        //given
        MemberDto mockMember = mock(MemberDto.class);
        Wish mockWish1 = mock(Wish.class);
        Wish mockWish2 = mock(Wish.class);
        List<Wish> mockWishes = List.of(mockWish1, mockWish2);

        Mockito.when(loginMemberArgumentResolver.supportsParameter(any()))
                .thenReturn(true);
        Mockito.when(loginMemberArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .thenReturn(mockMember);

        Mockito.when(wishService.getAllWishes(eq(mockMember), any(Pageable.class)))
                .thenReturn(new PageImpl<>(mockWishes, PageRequest.of(0, 20), 2));

        //when & then
        mockMvc.perform(get("/wishes?page=0"))
                .andExpect(status().isOk())
                .andExpect(view().name("manageWishList"))
                .andExpect(model().attributeExists("wishes"))
                .andExpect(model().attribute("wishes", mockWishes));

        verify(wishService, times(1)).getAllWishes(eq(mockMember), any(Pageable.class));
    }
}