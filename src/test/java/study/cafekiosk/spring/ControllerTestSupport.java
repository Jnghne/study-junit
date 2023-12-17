package study.cafekiosk.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import study.cafekiosk.spring.api.controller.order.OrderController;
import study.cafekiosk.spring.api.controller.product.ProductController;
import study.cafekiosk.spring.api.service.Order.OrderService;
import study.cafekiosk.spring.api.service.product.ProductService;

@WebMvcTest(controllers = {
        OrderController.class,
        ProductController.class
})
public abstract class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected OrderService orderService;

    // 컨테이너에 ProductService에 해당하는 Mock 객체를 넣어준다
    @MockBean
    protected ProductService productService;

}
