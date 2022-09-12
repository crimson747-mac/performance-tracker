package plugin.performancetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plugin.performancetracker.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
