package com.whenling.castle.shop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.repo.jpa.HierarchicalEntity;

@Entity
@Table(name = "shop_product_category")
public class ProductCategoryEntity extends HierarchicalEntity<UserEntity, Long, ProductCategoryEntity> {

	private static final long serialVersionUID = -7029182796544449909L;

	/** 名称 */
	@Column(nullable = false, length = 200)
	private String name;

	/** 页面标题 */
	@Column(length = 200)
	private String seoTitle;

	/** 页面关键词 */
	@Column(length = 200)
	private String seoKeywords;

	/** 页面描述 */
	@Column(length = 200)
	private String seoDescription;

	@OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY)
	private Set<ProductEntity> products = new HashSet<>();
	
	/** 筛选品牌 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "shop_product_category_brand")
	@OrderBy("sortNo asc")
	private Set<BrandEntity> brands = new HashSet<BrandEntity>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public Set<ProductEntity> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductEntity> products) {
		this.products = products;
	}

	public Set<BrandEntity> getBrands() {
		return brands;
	}

	public void setBrands(Set<BrandEntity> brands) {
		this.brands = brands;
	}

}
