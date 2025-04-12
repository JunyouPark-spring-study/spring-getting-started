package hello.hello_spring.domain;

import jakarta.persistence.*;

@Entity
public class Member {
    //post했을 때 어떻게 name을 찾을 수 있을까?
    //post요청 시 bean규약에 의해 getName method를 찾게 됨
    //만약 메서드 명이 setNamee과 같은 방식으로 다르다면 name을 set할 수 없음(null값 입력됨)
    //변수명은 의미가 없다! 하지만 가독성을 위해 일치시키는 게 좋음
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
