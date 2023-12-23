package com.slicequeue.inflearn.service;

import com.slicequeue.inflearn.domain.Member;
import com.slicequeue.inflearn.exception.NotFoundException;
import com.slicequeue.inflearn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(Member member) {
        return null; // TODO 작업 진행
    }

    @Transactional
    public void update(Long id, String name) {
        // 이 부분 update 하고 나서 Member 응답 하는 것
        Member member = memberRepository.findOne(id);
        if (member == null) {
            throw new NotFoundException(Member.class.getSimpleName());
        }

        member.setName(name); // 변경 감지 수행!!!
        // transaction AOP 되면서 commit 플러쉬 다 해버림

        // 생성, 수정에 대해서 void 또는 id 값 정도만 반환하지 엔티티 상세 DTO 전달하지 않음
        // 이는 커멘드와 쿼리를 철저히 분리하는 것
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
