package hust.hoangson.clients.feign;

import hust.hoangson.clients.ClientResponse;
import hust.hoangson.clients.dto.ProductVariantDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${gateway.url}/api/product")
public interface ProductClient {

    @GetMapping("variants/{variantId}")
    ClientResponse<ProductVariantDTO> getByVariantId(@PathVariable("variantId") String variantId);
}