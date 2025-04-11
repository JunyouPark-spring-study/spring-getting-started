package hello.hello_spring.controller;

import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
}
