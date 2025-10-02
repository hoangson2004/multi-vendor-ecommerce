package hust.hoangson.product.service;

import hust.hoangson.product.request.CatalogCreateRequest;
import hust.hoangson.product.request.CatalogSearchRequest;
import hust.hoangson.product.request.CatalogUpdateRequest;
import hust.hoangson.product.response.CatalogResponse;
import hust.hoangson.product.response.ImageResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface ProductCatalogService {
    CatalogResponse create(CatalogCreateRequest catalog);

    Page<CatalogResponse> search(CatalogSearchRequest req);

    CatalogResponse update(String catalogId, CatalogUpdateRequest update);

    int deleteCatalog(String catalogId);

    ImageResponse uploadImage(String catalogId, MultipartFile file, boolean isPrimary);

    List<ImageResponse> getImages(String catalogId);

    int deleteImage(String catalogId, UUID imageId);
}
