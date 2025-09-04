package kr.gravy.gravy.auction.entity;

import kr.gravy.gravy.auction.model.AuctionStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Auction {
    private Long id;
    private UUID publicId;
    private UUID sellerPublicId;
    private String title;
    private String description;
    private String category;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private BigDecimal buyNowPrice;
    private AuctionStatus status;
    private LocalDateTime auctionStartAt;
    private LocalDateTime auctionEndAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Auction(UUID publicId, UUID sellerPublicId, String title, String description, 
                   String category, BigDecimal startingPrice, BigDecimal buyNowPrice,
                   AuctionStatus status, LocalDateTime auctionStartAt, LocalDateTime auctionEndAt,
                   LocalDateTime createdAt) {
        this.publicId = publicId;
        this.sellerPublicId = sellerPublicId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.startingPrice = startingPrice;
        this.currentPrice = startingPrice;
        this.buyNowPrice = buyNowPrice;
        this.status = status;
        this.auctionStartAt = auctionStartAt;
        this.auctionEndAt = auctionEndAt;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    public static Auction createForRegistration(UUID publicId, UUID sellerPublicId, String title, 
                                              String description, String category, BigDecimal startingPrice, 
                                              BigDecimal buyNowPrice, LocalDateTime auctionStartAt, 
                                              LocalDateTime auctionEndAt, LocalDateTime createdAt) {
        return new Auction(publicId, sellerPublicId, title, description, category, startingPrice, 
                          buyNowPrice, AuctionStatus.SCHEDULED, auctionStartAt, auctionEndAt, createdAt);
    }

    public void updateCurrentPrice(BigDecimal newPrice) {
        this.currentPrice = newPrice;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStatus(AuctionStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }
}