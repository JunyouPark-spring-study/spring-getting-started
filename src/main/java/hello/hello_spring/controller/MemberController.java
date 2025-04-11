package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        // Component스캔 MemberController는 MemberService에 의존함
        // Java코드로 직접 spring bean에 등록한 memberService를 가져옴
        // DI에는 3가지 방법이 있다. 필드,생성자,세터 주입.
        // 의존관계가 실행중에 변하는 경우는 거의 없음, 따라서 생성자 주입 사용
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "/members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
//        System.out.println(form.getName());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
