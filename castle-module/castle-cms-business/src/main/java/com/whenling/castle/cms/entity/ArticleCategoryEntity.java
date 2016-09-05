package com.whenling.castle.cms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.repo.jpa.HierarchicalEntity;

@Entity
@Table(name = "cms_article_category")
public class ArticleCategoryEntity extends HierarchicalEntity<UserEntity, Long, ArticleCategoryEntity> {

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

	@OneToMany(mappedBy = "articleCategory", fetch = FetchType.LAZY)
	private Set<ArticleEntity> articles = new HashSet<>();

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

	public Set<ArticleEntity> getArticles() {
		return articles;
	}

	public void setArticles(Set<ArticleEntity> articles) {
		this.articles = articles;
	}

}
