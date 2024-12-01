package com.example.DB.Service.impl;

import com.example.DB.Service.MemberService;
import com.example.DB.dto.MemberDto;
import com.example.DB.entity.Member;
import com.example.DB.entity.Team;
import com.example.DB.entity.User;
import com.example.DB.repository.MemberRepository;
import com.example.DB.repository.TeamRepository;
import com.example.DB.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public MemberDto addMember(MemberDto memberDto) {
        if (memberRepository.existsByUserIdAndTeamId(memberDto.getUserId(), memberDto.getTeamId())) {
            throw new RuntimeException("User is already a member of this team");
        }

        User user = userRepository.findById(memberDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Team team = teamRepository.findById(memberDto.getTeamId())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        Member member = new Member();
        member.setUser(user);
        member.setTeam(team);
        member.setTeamRole(memberDto.getTeamRole());

        Member savedMember = memberRepository.save(member);
        return convertToDto(savedMember);
    }

    public MemberDto getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        return convertToDto(member);
    }

    public List<MemberDto> getTeamMembers(Long teamId) {
        return memberRepository.findByTeamId(teamId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MemberDto> getUserTeamMemberships(Long userId) {
        return memberRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MemberDto updateMemberRole(Long id, Member.TeamRole newRole) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        member.setTeamRole(newRole);
        Member updatedMember = memberRepository.save(member);
        return convertToDto(updatedMember);
    }

    public void removeMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("Member not found");
        }
        memberRepository.deleteById(id);
    }

    private MemberDto convertToDto(Member member) {
        return new MemberDto(
                member.getId(),
                member.getUser().getId(),
                member.getTeam().getId(),
                member.getTeamRole(),
                member.getJoinedAt()
        );
    }
}
