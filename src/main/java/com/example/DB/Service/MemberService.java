package com.example.DB.Service;

import com.example.DB.dto.MemberDto;
import com.example.DB.entity.Member;

import java.util.List;

public interface MemberService {
    MemberDto getMemberById(Long id);

    MemberDto addMember(MemberDto memberDto);

    List<MemberDto> getTeamMembers(Long teamId);

    List<MemberDto> getUserTeamMemberships(Long userId);

    MemberDto updateMemberRole(Long id, Member.TeamRole role);

    void removeMember(Long id);
}
