package com.slicequeue.inflearn.service;

import com.slicequeue.inflearn.domain.Member;
import com.slicequeue.inflearn.exception.DuplicatedUserNameException;
import com.slicequeue.inflearn.exception.NotFoundException;
import com.slicequeue.inflearn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        checkUserNameDuplicated(member.getName());
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public void update(Long id, String name) {
        // 이 부분 update 하고 나서 Member 응답 하는 것
        Member member = memberRepository.findOne(id);
        if (member == null) {
            throw new NotFoundException(Member.class.getSimpleName());
        }
        // 이름 있는지 검사
        checkUserNameDuplicated(name);

        // 수정 진행
        member.setName(name); // 변경 감지 수행!!!
        // transaction AOP 되면서 commit 플러쉬 다 해버림

        // 생성, 수정에 대해서 void 또는 id 값 정도만 반환하지 엔티티 상세 DTO 전달하지 않음
        // 이는 커멘드와 쿼리를 철저히 분리하는 것
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    private void checkUserNameDuplicated(String name) {
        List<Member> byName = memberRepository.findByName(name);
        if (!byName.isEmpty()) {
            throw new DuplicatedUserNameException(name);
        }
    }
}
