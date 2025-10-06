package hust.hoangson.order.client;

import hust.hoangson.order.domain.dto.ProductVariantDTO;
import hust.hoangson.order.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "product-service",
        url = "${gateway.url}/api/product"
)
public interface ProductClient {

    @GetMapping("/variants/{variantId}")
    BaseResponse<ProductVariantDTO> getByVariantId(@PathVariable("variantId") String variantId);

}

