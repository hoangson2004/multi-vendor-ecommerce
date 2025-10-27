package hust.hoangson.order.service;

import hust.hoangson.clients.ClientResponse;
import hust.hoangson.clients.dto.ProductVariantDTO;
import hust.hoangson.clients.feign.ProductClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    private final ProductClient productClient;

    /**
     * Lấy thông tin variant theo ID từ product-service
     */
    public ProductVariantDTO getVariantById(String variantId) {
        try {
            ClientResponse<ProductVariantDTO> response = productClient.getByVariantId(variantId);

            if (response == null) {
                LOG.error("ProductClient.getByVariantId({}) returned null", variantId);
                return null;
            }

            ProductVariantDTO dto = response.getBody();
            if (dto == null) {
                LOG.warn("ProductClient.getByVariantId({}) returned success but empty body", variantId);
                return null;
            }

            return dto;
        } catch (Exception e) {
            LOG.error("Error calling ProductClient.getByVariantId({}): {}", variantId, e.getMessage());
            return null;
        }
    }




}
