package main_project_025.I6E1.domain.trade.service;

import lombok.RequiredArgsConstructor;
import main_project_025.I6E1.domain.commission.entity.Commission;
import main_project_025.I6E1.domain.commission.service.CommissionService;
import main_project_025.I6E1.domain.member.entity.Member;
import main_project_025.I6E1.domain.member.service.MemberService;
import main_project_025.I6E1.domain.trade.dto.TradePatchDto;
import main_project_025.I6E1.domain.trade.dto.TradePostDto;
import main_project_025.I6E1.domain.trade.dto.TradeResponseDto;
import main_project_025.I6E1.domain.trade.entity.Trade;
import main_project_025.I6E1.domain.trade.repository.TradeRepository;
import main_project_025.I6E1.global.auth.userdetails.AuthMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TradeService {

    private final TradeRepository tradeRepository;
    private final CommissionService commissionService;
    private final MemberService memberService;


    public TradeResponseDto createTrade(TradePostDto tradePostDto) {
        Commission commission = commissionService.existCommission(tradePostDto.getCommissionId());//커미션 검증

        String userEmail = getAuthMember();
        Member member = memberService.findVerifyMemberByEmail(userEmail);

        Trade trade = Trade.builder()
                .title(tradePostDto.getTitle())
                .content(tradePostDto.getContent())
                .commission(commission)
                .member(member)
                .authorEmail(member.getEmail())
                .build();

        tradeRepository.save(trade);
        return TradeResponseDto.fromEntity(trade);
    }

    public TradeResponseDto updateTrade(TradePatchDto tradePatchDto, long tradeId) {
        Trade findTrade = findTradeById(tradeId);
        findTrade.updateStatus(tradePatchDto.getStatus());
        return TradeResponseDto.fromEntity(findTrade);
    }

    public TradeResponseDto readTrade(long tradeId) {
        Trade findTrade = findTradeById(tradeId);
        return TradeResponseDto.fromEntity(findTrade);
    }

    public Page<Trade> readTradesUser(Pageable pageable, Long memberId) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        return tradeRepository.findByMemberId(pageRequest, memberId);
    }

    public Page<Trade> readTradesAuthor(Pageable pageable, String authorEmail) {
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        return tradeRepository.findByAuthorEmail(pageRequest, authorEmail);
    }

    public void deleteTrade(long tradeId) {
        //유저 검증
        //커미션 검증은 굳이 필요 없을 것 같긴 한데 얘기해볼것
        findTradeById(tradeId);//거래 검증
        tradeRepository.deleteById(tradeId);
    }

    public Trade findTradeById(long tradeId) {
        Optional<Trade> optionalTrade = tradeRepository.findById(tradeId);
        Trade trade = optionalTrade.orElseThrow(() -> new RuntimeException("존재하지 않는 거래입니다."));
        return trade;
    }


    public static String getAuthMember() {
        AuthMember authMember = (AuthMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = authMember.getUsername();
        return userEmail;
    }
}