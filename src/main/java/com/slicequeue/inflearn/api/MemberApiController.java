package com.slicequeue.inflearn.api;

import com.slicequeue.inflearn.domain.Member;
import com.slicequeue.inflearn.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

// @Controller
// @ResponseBody - data 자체를 바로 xml 또는 json 으로 보내자
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

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
