package plugin.performancetracker.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import plugin.performancetracker.entity.Member;
import plugin.performancetracker.params.MemberDeleteDto;
import plugin.performancetracker.params.MemberSaveDto;
import plugin.performancetracker.service.MemberService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService MEMBER_SERVICE;

    @GetMapping
    public List<Member> findAll() {
        return MEMBER_SERVICE.findAll();
    }

    @GetMapping("/{id}")
    public Member findOneById(@PathVariable Long id) {
        return MEMBER_SERVICE.findOneById(id);
    }

    @PostMapping
    public Member save(@RequestBody MemberSaveDto memberSaveDto) {
        return MEMBER_SERVICE.save(
                memberSaveDto.getName(),
                memberSaveDto.getAge());
    }

    @DeleteMapping
    public String delete(@RequestBody MemberDeleteDto memberDeleteDto) {
        MEMBER_SERVICE.delete(memberDeleteDto.getId());
        return "OK";
    }
}
