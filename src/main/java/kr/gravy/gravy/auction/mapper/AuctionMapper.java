package kr.gravy.gravy.auction.mapper;

import kr.gravy.gravy.auction.dto.AuctionDetailDto;
import kr.gravy.gravy.auction.dto.AuctionListDto;
import kr.gravy.gravy.auction.entity.Auction;
import kr.gravy.gravy.auction.model.AuctionStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuctionMapper {

    void insertAuction(Auction auction);

    Optional<Auction> getAuctionByPublicId(@Param("publicId") UUID publicId);

    Optional<AuctionDetailDto.Response> getAuctionDetailByPublicId(@Param("publicId") UUID publicId);

    List<AuctionListDto.AuctionSummary> getAuctionList(@Param("category") String category,
                                                       @Param("status") AuctionStatus status,
                                                       @Param("searchKeyword") String searchKeyword,
                                                       @Param("offset") int offset,
                                                       @Param("size") int size);

    long countAuctions(@Param("category") String category,
                      @Param("status") AuctionStatus status,
                      @Param("searchKeyword") String searchKeyword);

}