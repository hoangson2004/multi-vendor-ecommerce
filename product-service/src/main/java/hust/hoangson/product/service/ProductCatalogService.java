package hust.hoangson.product.service;

import hust.hoangson.product.request.CatalogCreateRequest;
import hust.hoangson.product.request.CatalogSearchRequest;
import hust.hoangson.product.request.CatalogUpdateRequest;
import hust.hoangson.product.response.CatalogResponse;
import org.springframework.data.domain.Page;

public interface ProductCatalogService {
    CatalogResponse create(CatalogCreateRequest catalog);

    Page<CatalogResponse> search(CatalogSearchRequest req);

    CatalogResponse update(String catalogId, CatalogUpdateRequest update);

    int deleteCatalog(String catalogId);
}
