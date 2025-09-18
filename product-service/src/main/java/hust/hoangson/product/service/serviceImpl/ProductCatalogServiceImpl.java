package hust.hoangson.product.service.serviceImpl;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import hust.hoangson.product.domain.constant.Constant;
import hust.hoangson.product.domain.entity.ProductCatalogEntity;
import hust.hoangson.product.domain.entity.ProductImageEntity;
import hust.hoangson.product.repository.ProductCatalogRepository;
import hust.hoangson.product.repository.ProductImageRepository;
import hust.hoangson.product.request.CatalogCreateRequest;
import hust.hoangson.product.request.CatalogSearchRequest;
import hust.hoangson.product.request.CatalogUpdateRequest;
import hust.hoangson.product.response.CatalogResponse;
import hust.hoangson.product.response.ImageResponse;
import hust.hoangson.product.service.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductCatalogRepository catalogRepository;
    private final Cloudinary cloudinary;
    private final ProductImageRepository productImageRepository;


    @Override
    public CatalogResponse create(CatalogCreateRequest req) {
        ProductCatalogEntity entity = new ProductCatalogEntity();
        entity.setCatalogId("CAT-" + System.currentTimeMillis());
        entity.setName(req.getName());
        entity.setDescription(req.getDescription());
        entity.setBrand(req.getBrand());
        entity.setAttributesJson(req.getAttributesJson());

        ProductCatalogEntity saved = catalogRepository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    public Page<CatalogResponse> search(CatalogSearchRequest req) {
        PageRequest pageRequest = PageRequest.of(req.getPage(), req.getLimit());

        Page<ProductCatalogEntity> listCatalog = catalogRepository.searchCatalogs(
                req.getCatalogId() != null ? req.getCatalogId() : null,
                req.getName() != null ? req.getName() : "",
                req.getDescription()  != null ? req.getDescription() : "",
                req.getBrand()  != null ? req.getBrand() : "",
                pageRequest
        );
        return listCatalog.map(CatalogResponse::of);
    }

    @Override
    public CatalogResponse update(String catalogId, CatalogUpdateRequest update) {
        ProductCatalogEntity catalog = catalogRepository.findByCatalogId(catalogId).orElse(null);

        if (catalog == null) {
            return null;
        }

        catalog.setName(update.getName());
        catalog.setDescription(update.getDescription());
        catalog.setBrand(update.getBrand());
        catalog.setAttributesJson(update.getAttributesJson());

        catalogRepository.save(catalog);
        
        return mapToResponse(catalog);
    }

    @Override
    public int deleteCatalog(String catalogId) {
        return catalogRepository.updateIsActiveFalse(catalogId);
    }

    @Override
    public ImageResponse uploadCatalogImage(String catalogId, MultipartFile file, boolean isPrimary) {
        ProductCatalogEntity catalog = catalogRepository.findByCatalogId(catalogId).orElse(null);
        if (catalog == null) return null;

        UUID catalogUuid = catalog.getId();
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(Constant.Cloudinary.FOLDER, Constant.Cloudinary.CATALOG + catalogUuid));

            String url = (String) uploadResult.get(Constant.Cloudinary.SECURE_URL);

            ProductImageEntity entity = new ProductImageEntity();
            entity.setImageId(UUID.randomUUID());
            entity.setCatalogUuid(catalogUuid);
            entity.setUrl(url);
            entity.setIsPrimary(isPrimary);

            ProductImageEntity saved = productImageRepository.save(entity);

            return ImageResponse.of(saved);

        } catch (Exception e) {
            throw new RuntimeException("Upload image failed", e);
        }
    }

    @Override
    public List<ImageResponse> getCatalogImages(String catalogId) {
        return productImageRepository.findByCatalogId(catalogId).stream()
                .map(ImageResponse::of)
                .toList();
    }

    @Override
    public int deleteImage(String catalogId, UUID imageId) {
        return productImageRepository.deleteImageById(imageId);
    }

    private CatalogResponse mapToResponse(ProductCatalogEntity entity) {
        CatalogResponse response = new CatalogResponse();
        response.setCatalogId(entity.getCatalogId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setBrand(entity.getBrand());
        response.setAttributesJson(entity.getAttributesJson());
        return response;
    }
}
