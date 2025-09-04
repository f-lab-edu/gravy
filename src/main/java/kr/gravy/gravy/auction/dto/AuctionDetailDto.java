package kr.gravy.gravy.auction.dto;

import kr.gravy.gravy.auction.model.AuctionStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class AuctionDetailDto {

    public record Response(UUID publicId,
                          String title,
                          String description,
                          String category,
                          BigDecimal startingPrice,
                          BigDecimal currentPrice,
                          BigDecimal buyNowPrice,
                          AuctionStatus status,
                          LocalDateTime auctionStartAt,
                          LocalDateTime auctionEndAt,
                          LocalDateTime createdAt,
                          String sellerNickname) {
    }
}