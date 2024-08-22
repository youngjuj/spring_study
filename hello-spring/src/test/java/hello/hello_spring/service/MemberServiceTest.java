package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        // 각 메소드가 실행되기 전마다 실행되는 코드
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void join() {
        // given : 상황이 주어졌을 때 (기반)
        Member member = new Member();
        member.setName("hello");

        // when : 실행을 했을 때 (검증)
        Long saveId = memberService.join(member);

        // then : 결과로 나와야 한다 (검증부)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // 테스트케이스에서는 직관적인 이해를 위해 한글명도 가능하다
        // given
        Member member1 = new Member();
        member1.setName("spirng");

        Member member2 = new Member();
        member2.setName("spirng");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        memberService.join(member2);

        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}