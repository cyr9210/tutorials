package com.bong.cascade_orphanremoval.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void testUnexpectedRollback() {
        inner();
        Optional<Member> byId = memberRepository.findByUniqueId(123L);
        byId.ifPresent(m -> m.setName("ddd"));
        System.out.println("??");
    }

    @Transactional
    public void inner() {
        memberRepository.findByUniqueId(123L)
                .orElseGet(() -> memberRepository.save(new Member("name", 123L)));
    }

}
