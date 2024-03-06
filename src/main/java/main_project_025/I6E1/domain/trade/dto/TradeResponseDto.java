package main_project_025.I6E1.domain.trade.dto;

import lombok.Builder;
import lombok.Getter;
import main_project_025.I6E1.domain.trade.entity.Status;
import main_project_025.I6E1.domain.trade.entity.Trade;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TradeResponseDto {
    private Long tradeId;
    private Long commissionId;
    private Long memberId;
    private String title;
    private String content;
    private Status status;
    private String authorEmail;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public TradeResponseDto(Long tradeId, Long commissionId, Long memberId, String title, String content, Status status, String authorEmail, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.tradeId = tradeId;
        this.commissionId = commissionId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.authorEmail = authorEmail;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static TradeResponseDto fromEntity(Trade trade) {
        return TradeResponseDto.builder()
                .tradeId(trade.getTradeId())
                .commissionId(trade.getTradeId())
                .memberId(trade.getTradeId())
                .title(trade.getTitle())
                .content(trade.getContent())
                .status(trade.getStatus())
                .authorEmail(trade.getAuthorEmail())
                .createdAt(trade.getCreatedAt())
                .modifiedAt(trade.getModifiedAt())
                .build();
    }

    public static List<TradeResponseDto> fromEntityList(List<Trade> productList) {
        return productList.stream()
                .map(TradeResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}