package hust.hoangson.product.response;

import hust.hoangson.product.domain.entity.ProductImageEntity;
import hust.hoangson.product.domain.entity.VariantImageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
    private UUID imageId;
    private String url;
    private Boolean isPrimary;

    public static ImageResponse of(ProductImageEntity entity) {
        ImageResponse res = new ImageResponse();
        res.setImageId(entity.getImageId());
        res.setUrl(entity.getUrl());
        res.setIsPrimary(entity.getIsPrimary());
        return res;
    }

    public static ImageResponse of(VariantImageEntity entity) {
        ImageResponse res = new ImageResponse();
        res.setImageId(entity.getImageId());
        res.setUrl(entity.getUrl());
        res.setIsPrimary(entity.getIsPrimary());
        return res;
    }
}

