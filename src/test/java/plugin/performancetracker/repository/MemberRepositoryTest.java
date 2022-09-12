package plugin.performancetracker.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import plugin.performancetracker.entity.Member;

import javax.transaction.Transactional;

@Transactional
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository MEMBER_REPOSITORY;

    @Test
    void save() {
        int count = 5;
        for (int i = 0; i < count; i++) {
            MEMBER_REPOSITORY.save(new Member("TEST_UNIT_" + i, 30L));
        }
    }
}
