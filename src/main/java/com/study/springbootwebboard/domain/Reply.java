package com.study.springbootwebboard.domain;

import lombok.*;

import javax.persistence.*;

@Entity
// name? Reply 엔티티는 Reply라는 이름의 테이블과 매핑
// indexes? 엔티티와 관련된 인덱스를 정의. 인덱스 이름 지정, 인덱스를 생성할 column 목록 지정 (쿼리 조건에 자주 사용되는 컬럼)
// => idx_reply_board_bno 라는 이름의 인덱스가 board_bno 열에 생성 => DB와 엔티티 사이의 매핑 및 성능 최적화
@Table(name = "Reply", indexes = {
        @Index(name = "idx_reply_board_bno", columnList = "board_bno") })
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")    // exclude 속성? board 필드를 toString() 결과에서 제외
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    // fetch? EAGER(즉시 로딩): 엔티티가 로드될 때 연관 엔티티도 함께 로드 -> 모든 데이터 즉시 가져오기 때문에 성능 이슈
    // LAZY(지연 로딩): 연관 엔티티를 필요한 시점에 로드 -> 필요한 경우에만 불러오므로 성능 향상 가능
    @ManyToOne(fetch = FetchType.LAZY)  // 댓글(N) : 게시물(1) 다대일 관계
    private Board board;

    private String replyText;

    private String replyer;

}
