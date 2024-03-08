package main_project_025.I6E1.domain.member.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main_project_025.I6E1.domain.member.dto.MemberDetailResponseDto;
import main_project_025.I6E1.domain.member.dto.MemberPostDto;
import main_project_025.I6E1.domain.member.entity.Member;
import main_project_025.I6E1.domain.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {
    private final MemberService memberService;

    //1. 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<MemberDetailResponseDto> postMember(@Valid @RequestBody MemberPostDto memberPostDto){
        MemberDetailResponseDto savedMember = memberService.create(memberPostDto);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity<MemberDetailResponseDto> findMember(@Positive @PathVariable("member-id") Long memberId){
        Member findMember = memberService.findById(memberId);
        MemberDetailResponseDto response = MemberDetailResponseDto.fromEntity(findMember);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/email")
    public ResponseEntity checkEmail(String email){
        boolean check = memberService.checkEmail(email);
        Map<String,Boolean> response = new HashMap<>();
        response.put("email",check);

        return ResponseEntity.ok(response);
    }
}
