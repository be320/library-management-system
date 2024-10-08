package com.system.library.controller;

import com.system.library.config.annotations.IsAdmin;
import com.system.library.config.annotations.IsUser;
import com.system.library.dto.member.MemberDTO;
import com.system.library.dto.member.SaveMemberRequest;
import com.system.library.dto.member.MemberDTO;
import com.system.library.dto.member.ViewMembersResponse;
import com.system.library.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/members")
public class MemberController {
    
    @Autowired
    MemberService memberService;

    @GetMapping
    @IsUser
    public ResponseEntity<ViewMembersResponse> viewMembers(){
        ViewMembersResponse viewMembersResponse = memberService.viewMembers();
        return new ResponseEntity<>(viewMembersResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @IsUser
    public ResponseEntity<MemberDTO> viewMemberDetails(@PathVariable Long id){
        MemberDTO memberDTO = memberService.viewMemberDetails(id);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @PostMapping
    @IsAdmin
    public ResponseEntity<MemberDTO> addMember(@RequestBody SaveMemberRequest addMemberRequest){
        MemberDTO memberDTO = memberService.addMember(addMemberRequest);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<MemberDTO> updateMember(@PathVariable Long id, @RequestBody SaveMemberRequest updateMemberRequest){
        MemberDTO memberDTO = memberService.updateMember(id, updateMemberRequest);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<String> deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }
}
