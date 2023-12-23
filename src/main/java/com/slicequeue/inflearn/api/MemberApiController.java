package com.slicequeue.inflearn.api;

import com.slicequeue.inflearn.domain.Member;
import com.slicequeue.inflearn.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

// @Controller
// @ResponseBody - data 자체를 바로 xml 또는 json 으로 보내자
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers(); // List<Member> 엔티티 리스트 자체를 응답하는 것 불필요한 order 까지 다 나오는 것
        // 이걸 막기 위해 엔티티에 jsonignore 처리 모두 불필요한 처리... 다른 API 또다른 요구 있을 수 있기에...
    }

    @GetMapping("/api/v2/members")
    public Result<List<MemberDto>> memberV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> list = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .toList();
        return new Result<>(list.size(), list);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count; // 이렇게 확장도 용이함
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(
            @RequestBody // json 온 body 내용을 Member 클래스로 맵핑
            @Valid Member member
    ) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {
        // 트렌젝션 시작
        memberService.update(id, request.getName());
        // 트렌젝션 끝
        Member findMember = memberService.findOne(id); // 이후 조회 시작 
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        @NotEmpty(message = "name must not be empty.")
        String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {

        @NotEmpty(message = "name must not be empty.")
        String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }


}
