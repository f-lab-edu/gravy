package kr.gravy.gravy.auction.controller;

import jakarta.validation.Valid;
import kr.gravy.gravy.auction.dto.AuctionDetailDto;
import kr.gravy.gravy.auction.dto.AuctionListDto;
import kr.gravy.gravy.auction.dto.AuctionRegisterDto;
import kr.gravy.gravy.auction.model.AuctionStatus;
import kr.gravy.gravy.auction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping("/api/v1/auctions")
    @PreAuthorize("hasRole('BASIC')")
    public ResponseEntity<AuctionRegisterDto.Response> registerAuction(
            @Valid @RequestBody AuctionRegisterDto.Request request) {
        AuctionRegisterDto.Response response = auctionService.registerAuction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/api/v1/auctions")
    public ResponseEntity<AuctionListDto.Response> getAuctionList(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) AuctionStatus status,
            @RequestParam(required = false) String searchKeyword,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        
        AuctionListDto.Request request = new AuctionListDto.Request(
                category, status, searchKeyword, page, size
        );
        
        AuctionListDto.Response response = auctionService.getAuctionList(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/auctions/{auctionPublicId}")
    public ResponseEntity<AuctionDetailDto.Response> getAuctionDetail(
            @PathVariable UUID auctionPublicId) {
        AuctionDetailDto.Response response = auctionService.getAuctionDetail(auctionPublicId);
        return ResponseEntity.ok(response);
    }

}