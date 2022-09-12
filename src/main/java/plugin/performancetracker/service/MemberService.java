package plugin.performancetracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plugin.performancetracker.entity.Member;
import plugin.performancetracker.repository.MemberRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository MEMBER_REPOSITORY;

    public Member save(String name, Long age) {
        Member member = new Member(name, age);
        return MEMBER_REPOSITORY.save(member);
    }

    public void delete(Long id) {
        Member member = this.findOneById(id);
        MEMBER_REPOSITORY.delete(member);
    }

    public Member findOneById(Long id) {
        return MEMBER_REPOSITORY.findById(id).orElseThrow(
                () -> new NoSuchElementException("ID 와 일치하는 데이터를 찾을 수 없습니다."));
    }

    public List<Member> findAll() {
        return MEMBER_REPOSITORY.findAll();
    }

}
