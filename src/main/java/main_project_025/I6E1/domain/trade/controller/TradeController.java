package main_project_025.I6E1.domain.trade.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import main_project_025.I6E1.domain.trade.dto.TradePatchDto;
import main_project_025.I6E1.domain.trade.dto.TradePostDto;
import main_project_025.I6E1.domain.trade.dto.TradeResponseDto;
import main_project_025.I6E1.domain.trade.entity.Trade;
import main_project_025.I6E1.domain.trade.service.TradeService;
import main_project_025.I6E1.global.Page.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TradeResponseDto> createTrade(@RequestBody @Valid TradePostDto tradePostDto) {
        TradeResponseDto createdTrade = tradeService.createTrade(tradePostDto);
        return new ResponseEntity<>(createdTrade, HttpStatus.CREATED);
    }

    @PatchMapping("/{tradeId}")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_AUTHOR')")
    public ResponseEntity<TradeResponseDto> updateTrade(@Valid @RequestBody TradePatchDto tradePatchDto,
                                      @PathVariable("tradeId") @Positive long tradeId) {
        TradeResponseDto updatedTrade = tradeService.updateTrade(tradePatchDto, tradeId);
        return new ResponseEntity<>(updatedTrade, HttpStatus.OK);
    }

    @GetMapping("/{tradeId}")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_AUTHOR')")
    public ResponseEntity<TradeResponseDto> readTrade(@PathVariable("tradeId") @Positive long tradeId) {
        TradeResponseDto trade = tradeService.readTrade(tradeId);
        return new ResponseEntity<>(trade, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity readAllTradeUser(Pageable pageable, Long memberId) {
        Page<Trade> tradePage = tradeService.readTradesUser(pageable, memberId);
        List<Trade> tradeList = tradePage.getContent();
        return new ResponseEntity<>(new PageDto<>(TradeResponseDto.fromEntityList(tradeList), tradePage), HttpStatus.OK);
    }

    @GetMapping("/author")
    public ResponseEntity readAllTradeAuthor(Pageable pageable, String authorEmail) {
        Page<Trade> tradePage = tradeService.readTradesAuthor(pageable, authorEmail);
        List<Trade> tradeList = tradePage.getContent();
        return new ResponseEntity<>(new PageDto<>(TradeResponseDto.fromEntityList(tradeList), tradePage), HttpStatus.OK);
    }


    @DeleteMapping("/{tradeId}")
//    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_AUTHOR')")
    public ResponseEntity<String> deleteTrade(@PathVariable("tradeId") @Positive long tradeId) {
        tradeService.deleteTrade(tradeId);
        return new ResponseEntity<>("거래가 성공적으로 삭제되었습니다.", HttpStatus.OK);
    }
}

