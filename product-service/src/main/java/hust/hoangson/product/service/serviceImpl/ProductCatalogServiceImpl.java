package hust.hoangson.product.service.serviceImpl;


import hust.hoangson.product.domain.entity.ProductCatalogEntity;
import hust.hoangson.product.domain.enums.OwnerType;
import hust.hoangson.product.repository.ProductCatalogRepository;
import hust.hoangson.product.request.CatalogCreateRequest;
import hust.hoangson.product.request.CatalogSearchRequest;
import hust.hoangson.product.request.CatalogUpdateRequest;
import hust.hoangson.product.response.CatalogResponse;
import hust.hoangson.product.response.ImageResponse;
import hust.hoangson.product.service.ProductCatalogService;
import hust.hoangson.product.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private final ProductCatalogRepository catalogRepository;
    private final ProductImageService productImageService;

    @Override
    public CatalogResponse create(CatalogCreateRequest req) {
        ProductCatalogEntity entity = new ProductCatalogEntity();
        entity.setCatalogId("CAT-" + System.currentTimeMillis());
        entity.setName(req.getName());
        entity.setDescription(req.getDescription());
        entity.setBrand(req.getBrand());
        entity.setAttributesJson(req.getAttributesJson());

        ProductCatalogEntity saved = catalogRepository.save(entity);
        return CatalogResponse.of(saved, null);
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

        return listCatalog.map(entity -> {
            String url = getPrimeImg(entity.getCatalogId());
            return CatalogResponse.of(entity, url);
        });
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

        String url = getPrimeImg(catalogId);
        
        return CatalogResponse.of(catalog, url);
    }

    @Override
    public int deleteCatalog(String catalogId) {
        return catalogRepository.updateIsActiveFalse(catalogId);
    }

    @Override
    public ImageResponse uploadImage(String catalogId, MultipartFile file, boolean isPrimary) {
        ProductCatalogEntity catalog = catalogRepository.findByCatalogId(catalogId).orElse(null);
        if (catalog == null) return null;

        UUID catalogUuid = catalog.getId();

        return productImageService.uploadImage(catalogUuid, catalogId, file, isPrimary, OwnerType.CATALOG);

    }

    @Override
    public List<ImageResponse> getImages(String catalogId) {
        return productImageService.getImages(catalogId);
    }

    @Override
    public int deleteImage(String catalogId, UUID imageId) {
        return productImageService.deleteImage(imageId);
    }

    public String getPrimeImg(String catalogId) {
       return productImageService.getPrimeImg(catalogId);
    }

}
