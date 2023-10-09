package com.study.springbootwebboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    // 페이징 관련 정보(page, size)와 검색 종류(type), 키워드(keyword), 링크(link)

    @Builder.Default    // 매개변수를 제공하지 않으면 page 값을 기본 1로 설정
    private int page = 1;

    @Builder.Default    // 매개변수를 제공하지 않으면 size 값을 기본 10으로 설정
    private int size = 10;

    private String type;    // 검색 종류 t(title), c(content), w(writer), tc, tw, twc

    private String keyword;

    private String link;

    public String[] getTypes() {    // 검색 조건인 type을 분리하여 배열로 반환. tc면 ["t", "c"] 배열 반환
        if(type == null || type.isEmpty()) {
            return null;
        }
        return type.split("");
    }

    public Pageable getPageable(String...props) {
        // String...props는 메서드를 호출할 때 원하는 개수의 문자열을 전달할 수 있게 해주는 가변 인자 문법
        // 예를 들어 Pageable pageable = pageRequestDTO.getPageable("t", "c", "w"); 이렇게 호출이 가능
        // 페이지 번호는 0부터 시작하므로 this.page - 1로 설정
        return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
    }

    public String getLink() {
        // 검색 조건과 페이징 조건을 문자열로 구성
        if(link == null) {
            // link가 null일 때만 링크를 생성하고 null이 아닐 땐 기존에 생성한 링크를 재사용한다.
            StringBuilder builder = new StringBuilder();
            builder.append("page=" + this.page);
            builder.append("&size=" + this.size);

            if(type != null && type.length() > 0) {
                builder.append("&type" + this.type);
            }

            if(keyword != null) {
                try {
                    builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }
            // 링크를 모두 생성한 후 link 변수에 생성된 링크 저장
            // 다음 번에 getLink 메서드를 호출할 때는 링크를 다시 생성하지 않고 저장된 링크를 반환
            link = builder().toString();
        }

        return link;

    }

}
