package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional // test가 끝날 때마다 transaction이 rollback되기 때문에 DB에 반영 안됨
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

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