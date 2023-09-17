package com.ansu.board.controller;

import com.ansu.board.domain.Member;
import com.ansu.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller()
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public  MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        // 멤버 객체 생성
        Member member = new Member();
        // 매핑된 name에서 값을 가져와서 생성된 객체에 넣어줌
        member.setName(form.getName());

        // 멤버 가입
        memberService.join(member);
        return "redirect:/"; // 성공시 홈으로 리다이렉트
    }

    @GetMapping("members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
