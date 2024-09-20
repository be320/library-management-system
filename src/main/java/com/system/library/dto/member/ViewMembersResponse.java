package com.system.library.dto.member;

import jakarta.validation.constraints.NotNull;

import java.util.List;


public class ViewMembersResponse {

    @NotNull
    private List<MemberDTO> members;

    public ViewMembersResponse(List<MemberDTO> members) {
        this.members = members;
    }

    public List<MemberDTO> getMembers() {
        return members;
    }

    public void setMembers(List<MemberDTO> members) {
        this.members = members;
    }
}
