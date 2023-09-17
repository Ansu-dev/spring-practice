package com.ansu.board.service;

import com.ansu.board.domain.Member;
import com.ansu.board.repository.MemberRepository;
import com.ansu.board.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    
    private final MemberRepository memberRepository = new MemoryMemberRepository();
    
    public Long join(Member member) {
        // 중복 이름의 회원은 안된다.
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /** 전체 회원 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
}
