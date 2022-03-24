package com.bong.cascade_orphanremoval.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/test")
    public String test() {
        memberService.testUnexpectedRollback();
        return "ok";
    }
}
