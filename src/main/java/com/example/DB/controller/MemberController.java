package com.example.DB.controller;

import com.example.DB.Service.MemberService;
import com.example.DB.dto.MemberDto;
import com.example.DB.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto addMember(@RequestBody MemberDto memberDto) {
        return memberService.addMember(memberDto);
    }

    @GetMapping("/{id}")
    public MemberDto getMember(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @GetMapping("/team/{teamId}")
    public List<MemberDto> getTeamMembers(@PathVariable Long teamId) {
        return memberService.getTeamMembers(teamId);
    }

    @GetMapping("/user/{userId}")
    public List<MemberDto> getUserTeamMemberships(@PathVariable Long userId) {
        return memberService.getUserTeamMemberships(userId);
    }

    @PatchMapping("/{id}/role")
    public MemberDto updateMemberRole(@PathVariable Long id, @RequestBody Member.TeamRole role) {
        return memberService.updateMemberRole(id, role);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMember(@PathVariable Long id) {
        memberService.removeMember(id);
    }
}