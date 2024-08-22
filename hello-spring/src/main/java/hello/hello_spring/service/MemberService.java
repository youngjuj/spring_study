package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service // 스프링컨테이너에서 서비스의 존재를 알아차릴 수 있도록 함
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* 회원가입 */
    public Long join(Member member) {
        /*
        // 같은 이름이 있는 중복 회원 불가능
        Optional<Member> result = memberRepository.findByName(member.getName());
        // optional nullable로 감싼 덕분에 ifPresent사용 가능
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        으로 작성할 수 있지만, 이미 findByName의 결과는 Optional<Member>이기 때문에 다음과 같이 간략하게 작성할 수 있다.
        */

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();

    }

    private void validateDuplicateMember(Member member) {
        // 중복 회원 검증
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
        memberRepository.save(member);
        // 을 메소드로 따로 뽑아서 작성할 수 있다. >> ctrl + alt + M
    }

    /* 전체 회원 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /* 회원 조회 */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
