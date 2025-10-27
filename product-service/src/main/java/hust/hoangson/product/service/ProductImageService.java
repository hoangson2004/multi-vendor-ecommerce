package hust.hoangson.product.service;

import hust.hoangson.product.domain.enums.OwnerType;
import hust.hoangson.product.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductImageService {
    ImageResponse uploadImage(UUID ownerUuid, String ownerId, MultipartFile file, boolean isPrimary, OwnerType ownerType);

    List<ImageResponse> getImages(String ownerId);

    int deleteImage(UUID imageId);

    String getPrimeImg(String ownerId);
}
