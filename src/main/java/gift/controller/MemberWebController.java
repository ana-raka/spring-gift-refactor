package gift.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberWebController {
    @GetMapping("/register")
    @Operation(summary = "회원가입 페이지로 이동", description = "회원가입 페이지로 이동할 때 사용하는 API")
    public String showRegisterPage() {
        return "registerMember";
    }

    @GetMapping("/login")
    @Operation(summary = "로그인 페이지로 이동", description = "로그인 페이지로 이동할 때 사용하는 API")
    public String showLoginPage() {
        return "loginMember";
    }
}
