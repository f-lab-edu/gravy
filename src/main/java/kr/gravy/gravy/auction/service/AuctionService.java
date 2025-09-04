package kr.gravy.gravy.auction.service;

import kr.gravy.gravy.auction.dto.AuctionDetailDto;
import kr.gravy.gravy.auction.dto.AuctionListDto;
import kr.gravy.gravy.auction.dto.AuctionRegisterDto;
import kr.gravy.gravy.auction.entity.Auction;
import kr.gravy.gravy.auction.mapper.AuctionMapper;
import kr.gravy.gravy.auction.model.AuctionStatus;
import kr.gravy.gravy.common.exception.GravyException;
import kr.gravy.gravy.common.exception.Status;
import kr.gravy.gravy.common.utils.GeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionMapper auctionMapper;

    @Transactional
    public AuctionRegisterDto.Response registerAuction(final AuctionRegisterDto.Request request) {
        UUID sellerPublicId = getCurrentUserPublicId();
        
        validateAuctionTimes(request.auctionStartAt(), request.auctionEndAt());
        validatePrices(request.startingPrice(), request.buyNowPrice());

        UUID auctionPublicId = GeneratorUtil.generatePublicId();
        LocalDateTime now = LocalDateTime.now();

        Auction auction = Auction.createForRegistration(
                auctionPublicId,
                sellerPublicId,
                request.title(),
                request.description(),
                request.category(),
                request.startingPrice(),
                request.buyNowPrice(),
                request.auctionStartAt(),
                request.auctionEndAt(),
                now
        );

        auctionMapper.insertAuction(auction);
        log.info("새 경매 등록 완료: auctionId={}, seller={}", auctionPublicId, sellerPublicId);

        return new AuctionRegisterDto.Response(auctionPublicId);
    }

    public AuctionListDto.Response getAuctionList(final AuctionListDto.Request request) {
        int page = request.page() != null ? request.page() : 0;
        int size = request.size() != null ? request.size() : 20;
        
        if (page < 0) page = 0;
        if (size < 1 || size > 100) size = 20;
        
        int offset = page * size;

        List<AuctionListDto.AuctionSummary> auctions = auctionMapper.getAuctionList(
                request.category(),
                request.status(),
                request.searchKeyword(),
                offset,
                size
        );

        long totalElements = auctionMapper.countAuctions(
                request.category(),
                request.status(),
                request.searchKeyword()
        );

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return new AuctionListDto.Response(auctions, totalPages, totalElements, page);
    }

    public AuctionDetailDto.Response getAuctionDetail(final UUID auctionPublicId) {
        return auctionMapper.getAuctionDetailByPublicId(auctionPublicId)
                .orElseThrow(() -> new GravyException(Status.AUCTION_NOT_FOUND));
    }


    private UUID getCurrentUserPublicId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UUID)) {
            throw new GravyException(Status.AUTHENTICATION_FAILED);
        }
        return (UUID) principal;
    }

    private void validateAuctionTimes(LocalDateTime startAt, LocalDateTime endAt) {
        LocalDateTime now = LocalDateTime.now();
        
        if (startAt.isBefore(now)) {
            throw new GravyException(Status.INVALID_AUCTION_TIME);
        }
        
        if (endAt.isBefore(startAt) || endAt.isBefore(now.plusMinutes(30))) {
            throw new GravyException(Status.INVALID_AUCTION_TIME);
        }
    }

    private void validatePrices(java.math.BigDecimal startingPrice, java.math.BigDecimal buyNowPrice) {
        if (buyNowPrice.compareTo(startingPrice) <= 0) {
            throw new GravyException(Status.BAD_REQUEST);
        }
    }
}