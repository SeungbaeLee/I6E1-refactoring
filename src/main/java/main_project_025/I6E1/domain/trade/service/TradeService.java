package main_project_025.I6E1.domain.trade.service;

import lombok.RequiredArgsConstructor;
import main_project_025.I6E1.global.auth.userdetails.AuthMember;
import main_project_025.I6E1.domain.commission.entity.Commission;
import main_project_025.I6E1.domain.commission.repository.CommissionRepository;
import main_project_025.I6E1.domain.trade.entity.Trade;
import main_project_025.I6E1.domain.trade.repository.TradeRepository;
import main_project_025.I6E1.domain.member.entity.Member;
import main_project_025.I6E1.domain.member.repository.MemberRepository;
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
    private final CommissionRepository commissionRepository;
    private final MemberRepository memberRepository;


    public Trade createTrade(Trade trade) {
        Commission commission = findCommissionById(trade.getCommission().getCommissionId());//커미션 검증

        AuthMember authMember = (AuthMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = authMember.getUsername();

        Optional<Member> optionalMember = memberRepository.findByEmail(userEmail);
        Member member = optionalMember.orElseThrow(() -> new RuntimeException("거래 신청 권한이 없습니다."));

        trade.setMember(member);
        trade.setAuthorEmail(commission.getMember().getEmail());
        trade.setCommission(commission);
        return tradeRepository.save(trade);
    }

    public Trade updateTrade(Trade trade) {
        Trade findTrade = findTradeById(trade.getTradeId());//거래 검증
        Commission commission = findCommissionById(findTrade.getCommission().getCommissionId());//커미션 검증
        findTrade.setCommission(commission);

        AuthMember authMember = (AuthMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = authMember.getUsername();

        Optional<Member> optionalMember = memberRepository.findByEmail(userEmail);
        Member member = optionalMember.orElseThrow(() -> new RuntimeException("거래 신청 권한이 없습니다."));

        findTrade.setMember(member);
        findTrade.setContent(findTrade.getContent());
        findTrade.setTitle(findTrade.getTitle());
        findTrade.setStatus(trade.getStatus());
        return findTrade;
    }

    public Trade readTrade(long tradeId) {
        Trade findTrade = findTradeById(tradeId);
        return findTrade;
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

    public Commission findCommissionById(long commissionId) {
        Optional<Commission> optionalCommission = commissionRepository.findById(commissionId);
        Commission commission = optionalCommission.orElseThrow(() -> new RuntimeException("존재하지 않는 판매글입니다."));
        return commission;
    }
}