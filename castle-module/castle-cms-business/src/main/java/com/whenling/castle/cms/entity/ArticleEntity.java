package com.whenling.castle.cms.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "cms_article")
public class ArticleEntity extends DataEntity<UserEntity, Long> {

	private static final long serialVersionUID = 2104934209123741347L;

	/** 标题 */
	@Column(nullable = false, length = 200)
	private String title;

	/** 作者 */
	@Column(length = 200)
	private String author;

	/** 内容 */
	@Lob
	private String content;

	/** 页面标题 */
	@Column(length = 200)
	private String seoTitle;

	/** 页面关键词 */
	@Column(length = 200)
	private String seoKeywords;

	/** 页面描述 */
	@Column(length = 200)
	private String seoDescription;

	/** 是否发布 */
	@Column(nullable = false)
	private boolean publication;

	/** 是否置顶 */
	@Column(nullable = false)
	private boolean stick;

	/** 点击数 */
	@Column(nullable = false)
	private long hits = 0l;

	/** 文章分类 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private ArticleCategoryEntity articleCategory;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "cms_article_to_tag")
	@OrderBy("sortNo asc")
	private Set<ArticleTagEntity> tags = new HashSet<>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public boolean isPublication() {
		return publication;
	}

	public void setPublication(boolean publication) {
		this.publication = publication;
	}

	public boolean isStick() {
		return stick;
	}

	public void setStick(boolean stick) {
		this.stick = stick;
	}

	public long getHits() {
		return hits;
	}

	public void setHits(long hits) {
		this.hits = hits;
	}

	public ArticleCategoryEntity getArticleCategory() {
		return articleCategory;
	}

	public void setArticleCategory(ArticleCategoryEntity articleCategory) {
		this.articleCategory = articleCategory;
	}

	public Set<ArticleTagEntity> getTags() {
		return tags;
	}

	public void setTags(Set<ArticleTagEntity> tags) {
		this.tags = tags;
	}

}
