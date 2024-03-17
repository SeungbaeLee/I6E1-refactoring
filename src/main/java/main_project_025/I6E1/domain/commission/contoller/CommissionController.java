package main_project_025.I6E1.domain.commission.contoller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import main_project_025.I6E1.domain.commission.dto.CommissionPatchDto;
import main_project_025.I6E1.domain.commission.dto.CommissionPostDto;
import main_project_025.I6E1.domain.commission.dto.CommissionResponseDto;
import main_project_025.I6E1.domain.commission.entity.Commission;
import main_project_025.I6E1.domain.commission.service.CommissionService;
import main_project_025.I6E1.global.Page.PageDto;
import main_project_025.I6E1.global.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/commission")
@RequiredArgsConstructor
public class CommissionController {
    private final CommissionService commissionService;

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<CommissionResponseDto> postCommission(@Valid @RequestPart CommissionPostDto postDto,
                                                                @RequestPart List<MultipartFile> multipartFile,
                                                                @AuthenticationPrincipal UserDetails userDetails){
        CommissionResponseDto commissionResponseDto = commissionService.createCommission(postDto, multipartFile, userDetails);
        return new ResponseEntity<>(commissionResponseDto, HttpStatus.CREATED);
    }


    //READ
    @GetMapping("/{commission-id}")
    public ResponseEntity getCommission(@PathVariable("commission-id")long commissionId) throws BusinessException {
            CommissionResponseDto commission = commissionService.readCommission(commissionId);
            return new ResponseEntity<>(commission, HttpStatus.OK);

    }
    //READ ALL
    //페이지네이션 적용
    @GetMapping
    public ResponseEntity getCommissions(Pageable pageable){
            Page<Commission> commissionPage = commissionService.readCommissions(pageable);
            List<Commission> commissionList = commissionPage.getContent();

            PageDto pageDto = new PageDto<>(CommissionResponseDto.fromEntityList(commissionList),commissionPage);
            return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    //Search
    @GetMapping("/search")
    public ResponseEntity searchCommissions(@RequestParam(required = false) String title,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) List<String> tags,
                                            Pageable pageable ) {

        Page<Commission> commissionPage = commissionService.searchOptions(pageable, title, name, tags);
        List<Commission> commissionList = commissionPage.getContent();
        PageDto pageDto = new PageDto<>(CommissionResponseDto.fromEntityList(commissionList),commissionPage);
        return new ResponseEntity<>(pageDto, HttpStatus.OK);
    }

    //UPDATE
    @PatchMapping("/{commission-id}")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<CommissionResponseDto> patchCommission(@PathVariable("commission-id")long commissionId,
                                                                 @Valid @RequestBody CommissionPatchDto patchDto){
            CommissionResponseDto commission = commissionService.updateCommission(commissionId, patchDto);

            return new ResponseEntity<>(commission, HttpStatus.OK);
    }

    //Delete
    //Soft Delete
    @DeleteMapping("/{commission-id}")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<String> deleteCommission(@PathVariable("commission-id")long commissionId){
            commissionService.deleteCommission(commissionId);
            return new ResponseEntity<>("정상적으로 삭제되었습니다. " , HttpStatus.NO_CONTENT);
    }
}
