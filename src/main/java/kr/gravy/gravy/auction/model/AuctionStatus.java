package kr.gravy.gravy.auction.model;

public enum AuctionStatus {
    SCHEDULED,  // 경매 등록 완료, 시작 대기중
    ACTIVE,     // 경매 진행중 (입찰 가능)
    COMPLETED,  // 경매 종료 (낙찰 완료)
    CANCELLED   // 경매 취소 (판매자가 취소)
}