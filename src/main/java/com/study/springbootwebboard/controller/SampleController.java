package com.study.springbootwebboard.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Log4j2
public class SampleController {

    class SampleDTO {
        private String p1, p2, p3;

        public String getP1() {
            return p1;
        }

        public String getP2() {
            return p2;
        }

        public String getP3() {
            return p3;
        }
    }

    @GetMapping("/hello")
    public void hello(Model model) {
        log.info("hello..........");

        model.addAttribute("msg", "HELLO WORLD");
    }

    @GetMapping("/ex/ex1")
    public void ex1(Model model) {

        List<String> list = Arrays.asList("AA", "BB", "CC", "DD");

        model.addAttribute("list", list);

    }

    @GetMapping("/ex/ex2")
    public void ex2(Model model) {

        log.info("ex2..........");

        List<String> strList = IntStream.range(1, 10)
                .mapToObj(i -> "Data" + i)
                .collect(Collectors.toList());

        model.addAttribute("list", strList);

        Map<String, String> map = new HashMap<>();
        map.put("A", "AAAA");
        map.put("B", "BBBB");

        model.addAttribute("map", map);

        SampleDTO sampleDTO = new SampleDTO();
        sampleDTO.p1 = "value -- p1";
        sampleDTO.p2 = "value -- p2";
        sampleDTO.p3 = "value -- p3";

        model.addAttribute("dto", sampleDTO);

    }

    @GetMapping("/ex/ex3")
    public void ex3(Model model) {

        log.info("ex3..........");

        model.addAttribute("arr", new String[]{"AA", "BB", "CC"});

    }

}
