package hello.hello_spring;

import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import hello.hello_spring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        //추후에 DB가 변경 될 경우를 생각해서 Java코드로 작성함. Component 스캔을 사용할 경우 변경이 어렵다.
        //EX) 아래의 코드를 DBMemberRepository로 변경
        return new MemoryMemberRepository();
    }
}
