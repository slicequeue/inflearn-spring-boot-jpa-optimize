package com.slicequeue.inflearn.service;

import com.slicequeue.inflearn.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    public Long join(Member member) {
        return null; // TODO 작업 진행
    }

    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
