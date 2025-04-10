package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!");
        //속성 이름: data, 속성 값: "hello!"
        return "hello"; //templates/hello.html을 찾아서 return
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template"; //MVC방식
    }

    @GetMapping("hello-string")
    @ResponseBody //ResponseBody가 있으면 HttpMessageConverter동작
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; //view가 없이 데이터만 내려감
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; //API 방식으로 객체를 반환하면 json형식으로 간다
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
