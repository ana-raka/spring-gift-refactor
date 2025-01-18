package gift.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class MemberWebControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MemberWebController memberWebController = new MemberWebController();
        mockMvc = MockMvcBuilders.standaloneSetup(memberWebController).build();
    }

    @Test
    void showRegisterPage() throws Exception {
        // given
        // when and then
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerMember"));
    }

    @Test
    void showLoginPage() throws Exception {
        // given
        // when and then
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginMember"));
    }
}