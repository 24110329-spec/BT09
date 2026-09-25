package vn.iotstar.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.iotstar.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO findById(Long id);
    List<ProductDTO> findAll();
    Page<ProductDTO> findAll(Pageable pageable);
    ProductDTO save(ProductDTO dto);
    void deleteById(Long id);
    long count();
}
