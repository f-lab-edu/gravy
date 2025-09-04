package kr.gravy.gravy.auction.dto;

import kr.gravy.gravy.auction.model.AuctionStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class AuctionListDto {

    public record Request(String category,
                          AuctionStatus status,
                          String searchKeyword,
                          Integer page,
                          Integer size) {
    }

    public record Response(List<AuctionSummary> auctions,
                          int totalPages,
                          long totalElements,
                          int currentPage) {
    }

    public record AuctionSummary(UUID publicId,
                                String title,
                                String category,
                                BigDecimal currentPrice,
                                BigDecimal buyNowPrice,
                                AuctionStatus status,
                                LocalDateTime auctionEndAt,
                                String sellerNickname) {
    }
}