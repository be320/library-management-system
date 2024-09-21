package com.system.library.mapper;

import com.system.library.dto.member.MemberDTO;
import com.system.library.dto.member.SaveMemberRequest;
import com.system.library.model.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberDTO toDTO(Member member);

    Member toEntity(SaveMemberRequest saveMemberRequest);
}

