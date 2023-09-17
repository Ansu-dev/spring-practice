package com.ansu.board.service;

import com.ansu.board.domain.Member;
import com.ansu.board.repository.MemberRepository;
import com.ansu.board.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
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

    /** 회원 단일 검색 */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    /** 중복 이름 검사 메소드 */
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재 하는 회원 입니다.");
        });
    }
}
