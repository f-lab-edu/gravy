package kr.gravy.gravy.auction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class AuctionRegisterDto {

    public record Request(@NotBlank(message = "제목은 필수값입니다.") String title,
                          @NotBlank(message = "상품 설명은 필수값입니다.") String description,
                          @NotBlank(message = "카테고리는 필수값입니다.") String category,
                          @NotNull @Positive(message = "시작가는 0보다 큰 값이어야 합니다.") BigDecimal startingPrice,
                          @NotNull @Positive(message = "즉시구매가는 0보다 큰 값이어야 합니다.") BigDecimal buyNowPrice,
                          @NotNull(message = "경매 시작 시간은 필수값입니다.") LocalDateTime auctionStartAt,
                          @NotNull(message = "경매 종료 시간은 필수값입니다.") LocalDateTime auctionEndAt
    ) {
    }

    public record Response(UUID auctionPublicId) {
    }
}