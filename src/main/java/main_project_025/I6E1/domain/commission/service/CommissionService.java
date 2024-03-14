package main_project_025.I6E1.domain.commission.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main_project_025.I6E1.domain.commission.dto.CommissionPatchDto;
import main_project_025.I6E1.domain.commission.dto.CommissionPostDto;
import main_project_025.I6E1.domain.commission.dto.CommissionResponseDto;
import main_project_025.I6E1.domain.commission.entity.Commission;
import main_project_025.I6E1.domain.commission.repository.CommissionRepository;
import main_project_025.I6E1.domain.commission.repository.CommissionRepositoryImpl;
import main_project_025.I6E1.domain.member.entity.Member;
import main_project_025.I6E1.domain.member.repository.MemberRepository;
import main_project_025.I6E1.domain.member.service.MemberService;
import main_project_025.I6E1.domain.tag.service.TagService;
import main_project_025.I6E1.global.auth.userdetails.AuthMember;
import main_project_025.I6E1.global.aws.AwsS3Service;
import main_project_025.I6E1.global.exception.BusinessException;
import main_project_025.I6E1.global.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CommissionService {
    private final CommissionRepository commissionRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final TagService tagService;
    private final AwsS3Service awsS3Service;
    private final CommissionRepositoryImpl commissionRepositoryImpl;

    //CREATE
    public CommissionResponseDto createCommission(CommissionPostDto commissionPostDto,
                                                  List<MultipartFile> multipartFile,
                                                  UserDetails userDetails){

        String email = userDetails.getUsername();
        Member member = memberService.findVerifyMemberByEmail(email);
        Commission commission = Commission.builder()
                .title(commissionPostDto.getTitle())
                .content(commissionPostDto.getContent())
                .subContent(commissionPostDto.getSubContent())
                .tags(new ArrayList<>())
                .member(member)
                .build();

        Commission commission1 = tagService.createTag(commissionPostDto.getTags(), commission);
        List<String> imageUrl = awsS3Service.uploadThumbnail(multipartFile);
        commission1.setImageUrl(imageUrl);
        commissionRepository.save(commission1);
        return CommissionResponseDto.fromEntity(commission1);
    }

    // READ
    public CommissionResponseDto readCommission(long commissionId){
        Commission commission = existCommission(commissionId);

        commission.updateViewCount();
        return CommissionResponseDto.fromEntity(commission);
    }

    // READ ALL
    public Page<Commission> readCommissions(Pageable pageable){
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize(), pageable.getSort());
        return commissionRepository.findAll(pageRequest);
    }

  //검색 기능
    public Page<Commission> searchOptions(Pageable pageable, String title, String name, List<String> tags) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber()-1, pageable.getPageSize(), pageable.getSort());
        return commissionRepositoryImpl.findBySearchOption(pageRequest, title, name, tags);
    }

    // UPDATE
    public CommissionResponseDto updateCommission(long commissionId, CommissionPatchDto commissionPatchDto){
        Commission verifyCommission = verifyWriter(commissionId);
        verifyCommission.updateCommission(commissionPatchDto.getTitle(), commissionPatchDto.getContent(), commissionPatchDto.getSubContent());
        return CommissionResponseDto.fromEntity(verifyCommission);
    }

    //DELETE
    public void deleteCommission(long commissionId){
        commissionRepository.deleteById(commissionId);
    }

    // 게시글 검증
    public Commission existCommission(long commissionId){
        Optional<Commission> commission = commissionRepository.findById(commissionId);
        return commission.orElseThrow(()-> new BusinessException(ExceptionCode.COMMISSION_NOT_FOUND));
    }

    // (로그인 멤버 = 작성자) 검증
    private Commission verifyWriter(long commissionId){
        AuthMember loginMember = (AuthMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long memberId = loginMember.getMemberId();

        Commission commission = existCommission(commissionId);
        if ( commission.getMember().getMemberId()  != memberId  ){
            throw new BusinessException(ExceptionCode.NOT_AUTHORITY);
        }
        return commission;
    }

    private Member getMemberFromId(long memberId){
        return memberRepository.findById(memberId).get();
    }
}