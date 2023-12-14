package study.cafekiosk.spring.api.controller.order.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.cafekiosk.spring.api.service.Order.request.OrderCreateServiceRequest;

import java.util.List;


@Getter
@NoArgsConstructor
public class OrderCreateRequest {
    @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
    private List<String> productNumbers;

    @Builder
    private OrderCreateRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }

    /** 메소드 설명
     * OrderCreateRequest 를 OrderService 가 직접 받지 않게 의존관계를 끊어준다.
     * - controller 패키지의 dto를 service 패키지 내부에서 의존하게 되기 때문에
     * - service 단 호출할 때 service 단의 Dto로 변환하는 과정
     *
     * Tip. 레이어드 아키텍쳐 설계할 때, 내부 레이어가 바깥 레이어를 참조하는것은 좋지 않음 (ex) service => controller 의존)
     *
     */
    public OrderCreateServiceRequest toServiceRequest() {
        return OrderCreateServiceRequest.builder()
                .productNumbers(productNumbers)
                .build();
    }
}
