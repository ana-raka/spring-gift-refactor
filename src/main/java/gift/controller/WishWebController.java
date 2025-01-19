package gift.controller;

import gift.dto.memberDto.MemberDto;
import gift.model.member.LoginMember;
import gift.model.wish.Wish;
import gift.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WishWebController {

    public WishWebController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    private final WishListService wishListService;

    @GetMapping("/wishes")
    @Operation(summary = "전체 장바구니 호출", description = "회원의 장바구니를 불러올 때 사용하는 API")
    public String getAllWishes(@LoginMember MemberDto memberDto, @RequestParam(defaultValue = "0") int page, Model model) {
        Page<Wish> wishPage = wishListService.getAllWishes(memberDto, PageRequest.of(page, 20));
        model.addAttribute("wishes", wishPage.getContent());
        model.addAttribute("totalPages", wishPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "manageWishList";
    }
}
