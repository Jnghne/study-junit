package study.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import study.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import study.cafekiosk.spring.api.service.product.response.ProductResponse;
import study.cafekiosk.spring.domain.product.Product;
import study.cafekiosk.spring.domain.product.ProductRepository;
import study.cafekiosk.spring.domain.product.ProductSellingStatus;
import study.cafekiosk.spring.domain.product.ProductType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * readOnly 가 true인 경우 : 읽기 전용으로 트랜잭션이 걸린다.
 *  - CRUD에서 C,U,D 동작이 일어나지 않음 오직 Read만 가능
 *  - JPA에서의 이점 : C,U,D 의 스냅샷 저장 변경감지를 안하기 때문에 성능 향상 효과
 *
 *  - CQRS : Command (C/U/D) / Query 의 책임을 분리해서 서로 연관이 안생기게 할 수 있다.
 *      => 보통 Command 보다는 Query 성 메소드가 많이 호출된다.
 *      => Command / Query 기능에 대한 장애가 발생 시 서로 영향이 가지 않게 해야 한다.
 *      => Command / Query 에 대한 서비스를 분리할 수 도 있다.
 *      => DB에 대한 EndPoint를 분리할 수 있다. (Master : Command 성 쿼리 / Slave : Query 성 쿼리)
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    // 동시성 이슈 존재 : 내가 상품을 등록할 때 다른 사람이 동시에 등록하면 어떻게 할지 ?
        // => 방안 1) 컬럼에 Unique 전략을 걸고, 동시에 등록하다가 겹쳐서 튕겨나간 경우 3회 이상 재등록 시도 하기
        // => 방안 2) ProductNumber를 UUID로 만들도록 전략을 짜기
    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        // DB에서 마지막 저장된 Product의 상품 번호 조회
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        // next product Number

        return ProductResponse.of(savedProduct);
    }
    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (latestProductNumber == null) {
            return "001";
        }
        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }
}
