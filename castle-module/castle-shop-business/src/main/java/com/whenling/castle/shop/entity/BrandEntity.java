package com.whenling.castle.shop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "shop_brand")
public class BrandEntity extends DataEntity<UserEntity, Long> {

	private static final long serialVersionUID = -2460002220316825755L;

	/**
	 * 类型
	 */
	public enum Type {

		/** 文本 */
		text,

		/** 图片 */
		image
	}

	/** 名称 */
	@Column(nullable = false, length = 100)
	private String name;

	/** 全称 */
	@Column(nullable = false, length = 100)
	@Enumerated(EnumType.STRING)
	private Type type;

	/** logo */
	@Column(length = 200)
	private String logo;

	/** 网址 */
	@Column(length = 200)
	private String url;

	/** 介绍 */
	@Lob
	private String introduction;

	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	private Set<ProductEntity> products = new HashSet<>();

	@ManyToMany(mappedBy = "brands", fetch = FetchType.LAZY)
	@OrderBy("sortNo asc")
	private Set<ProductCategoryEntity> productCategories = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Set<ProductEntity> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductEntity> products) {
		this.products = products;
	}

	public Set<ProductCategoryEntity> getProductCategories() {
		return productCategories;
	}

	public void setProductCategories(Set<ProductCategoryEntity> productCategories) {
		this.productCategories = productCategories;
	}

	/**
	 * 删除前处理
	 */
	@PreRemove
	public void preRemove() {
		Set<ProductEntity> products = getProducts();
		if (products != null) {
			for (ProductEntity product : products) {
				product.setBrand(null);
			}
		}
		Set<ProductCategoryEntity> productCategories = getProductCategories();
		if (productCategories != null) {
			for (ProductCategoryEntity productCategory : productCategories) {
				productCategory.getBrands().remove(this);
			}
		}
	}

}
