package com.system.library.service;

import com.system.library.dto.member.MemberDTO;
import com.system.library.dto.member.SaveMemberRequest;
import com.system.library.dto.member.MemberDTO;
import com.system.library.dto.member.ViewMembersResponse;
import com.system.library.exception.EntityNotFoundException;
import com.system.library.mapper.MemberMapper;
import com.system.library.model.Member;
import com.system.library.model.Member;
import com.system.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberMapper memberMapper;

    public ViewMembersResponse viewMembers(){
        List<MemberDTO> membersDTO = new ArrayList<>();
        List<Member> members = memberRepository.findAll();
        members.forEach(member -> membersDTO.add(memberMapper.toDTO(member)));
        return new ViewMembersResponse(membersDTO);
    }

    public MemberDTO viewMemberDetails(Long id){
        Optional<Member> member =  memberRepository.findById(id);
        if(member.isEmpty())
            throw new EntityNotFoundException();

        return memberMapper.toDTO(member.get());
    }

    public MemberDTO addMember(SaveMemberRequest addMemberRequest){
        Member memberToSave = memberMapper.toEntity(addMemberRequest);
        Member member = memberRepository.save(memberToSave);
        return memberMapper.toDTO(member);
    }

    public MemberDTO updateMember(Long id, SaveMemberRequest updateMemberRequest){

        Optional<Member> memberOptional =  memberRepository.findById(id);
        if(memberOptional.isPresent()){
            Member member = memberOptional.get();
            member.setEmail(updateMemberRequest.getEmail());
            member.setName(updateMemberRequest.getName());
            member.setMembershipDate(updateMemberRequest.getMembershipDate());
            memberRepository.save(member);
            return memberMapper.toDTO(member);
        }
        else
            throw new EntityNotFoundException();
    }

    public void deleteMember(Long id){
        Optional<Member> member =  memberRepository.findById(id);
        if(member.isEmpty())
            throw new EntityNotFoundException();

        memberRepository.delete(member.get());
    }

}
