package com.whenling.castle.shop.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.repo.jpa.SortEntity;

@Entity
@Table(name = "shop_product")
public class ProductEntity extends SortEntity<UserEntity, Long> {

	private static final long serialVersionUID = -2460002220316825755L;

	/** 名称 */
	@Column(nullable = false, length = 100)
	private String name;

	/** 全称 */
	@Column(nullable = false, length = 200)
	private String fullName;

	/** 編號 */
	private String sn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private ProductCategoryEntity productCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	private BrandEntity brand;

	/** 展示图片 */
	private String image;

	/** 单位 */
	private String unit;

	/** 重量 */
	private Integer weight;

	/** 产品大小 */
	private String canSize;

	/** 包装大小 */
	private String cartonSize;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "shop_product_tag")
	@OrderBy("order asc")
	private Set<ProductTagEntity> tags = new HashSet<>();

	/** 是否列出 */
	private Boolean list;

	/** 是否置顶 */
	private Boolean top;

	/** 是否为赠品 */
	private Boolean gift;

	/** 介绍 */
	private String introduction;

	/** 备注 */
	private String memo;

	/** 搜索关键词 */
	private String keyword;

	/** 页面标题 */
	private String seoTitle;

	/** 页面关键词 */
	private String seoKeywords;

	/** 页面描述 */
	private String seoDescription;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public ProductCategoryEntity getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategoryEntity productCategory) {
		this.productCategory = productCategory;
	}

	public BrandEntity getBrand() {
		return brand;
	}

	public void setBrand(BrandEntity brand) {
		this.brand = brand;
	}

	public Set<ProductTagEntity> getTags() {
		return tags;
	}

	public void setTags(Set<ProductTagEntity> tags) {
		this.tags = tags;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getCanSize() {
		return canSize;
	}

	public void setCanSize(String canSize) {
		this.canSize = canSize;
	}

	public String getCartonSize() {
		return cartonSize;
	}

	public void setCartonSize(String cartonSize) {
		this.cartonSize = cartonSize;
	}

	public Boolean getList() {
		return list;
	}

	public void setList(Boolean list) {
		this.list = list;
	}

	public Boolean getTop() {
		return top;
	}

	public void setTop(Boolean top) {
		this.top = top;
	}

	public Boolean getGift() {
		return gift;
	}

	public void setGift(Boolean gift) {
		this.gift = gift;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

}
