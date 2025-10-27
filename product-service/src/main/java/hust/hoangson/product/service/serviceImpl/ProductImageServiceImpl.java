package hust.hoangson.product.service.serviceImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import hust.hoangson.product.domain.constant.Constant;
import hust.hoangson.product.domain.entity.ProductImageEntity;
import hust.hoangson.product.domain.enums.OwnerType;
import hust.hoangson.product.repository.ProductImageRepository;
import hust.hoangson.product.response.ImageResponse;
import hust.hoangson.product.service.ProductImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final Cloudinary cloudinary;
    private final ProductImageRepository productImageRepository;

    @Override
    @Transactional
    public ImageResponse uploadImage(UUID ownerUuid, String ownerId, MultipartFile file, boolean isPrimary, OwnerType ownerType) {
        String folderName = "";
        switch (ownerType) {
            case OwnerType.CATALOG ->  {
                folderName = Constant.Cloudinary.CATALOG;
            }
            case OwnerType.CATEGORY ->   {
                folderName = Constant.Cloudinary.CATEGORY;
            }
            case OwnerType.VARIANT ->   {
                folderName = Constant.Cloudinary.VARIANT;
            }
            case OwnerType.VENDOR_PRODUCT ->
                folderName = Constant.Cloudinary.V_PRODUCT;
        }

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(Constant.Cloudinary.FOLDER, folderName + ownerUuid));

            String url = (String) uploadResult.get(Constant.Cloudinary.SECURE_URL);

            ProductImageEntity entity = new ProductImageEntity();
            entity.setOwnerId(ownerId);
            entity.setOwnerType(OwnerType.CATALOG.getType());
            entity.setUrl(url);
            entity.setIsPrimary(isPrimary);

            if (isPrimary) {
                List<ProductImageEntity> images = productImageRepository.findByOwnerIdAndOwnerType(ownerId, OwnerType.CATALOG.getType());
                images.forEach(img -> img.setIsPrimary(false));
                productImageRepository.saveAll(images);
            }

            ProductImageEntity saved = productImageRepository.save(entity);

            return ImageResponse.of(saved);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ImageResponse> getImages(String ownerId) {
        return productImageRepository.findByOwnerId(ownerId).stream()
                .map(ImageResponse::of)
                .toList();
    }

    @Override
    public int deleteImage(UUID imageId) {
        return productImageRepository.deleteImageById(imageId);
    }

    public String getPrimeImg(String ownerId) {
        return productImageRepository.findPrimaryImageUrl(ownerId)
                .orElse(null);
    }
}
