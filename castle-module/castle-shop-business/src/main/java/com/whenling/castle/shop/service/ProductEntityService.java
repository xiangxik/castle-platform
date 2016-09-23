package com.whenling.castle.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.castle.main.service.EntityService;
import com.whenling.castle.shop.entity.ProductEntity;
import com.whenling.castle.shop.repo.ProductEntityRepository;

@Service
public class ProductEntityService extends EntityService<ProductEntity, Long> {

	@Autowired
	private ProductEntityRepository productEntityRepository;
	
	public List<ProductEntity> relatedProduct(ProductEntity self) {
		return productEntityRepository.findTop3ByProductCategoryAndIdNot(self.getProductCategory(), self.getId());
	}
	
}
