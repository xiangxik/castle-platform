package com.whenling.bbs.domain;

import java.util.Date;

import com.whenling.main.domain.Domain;
import com.whenling.main.domain.User;

public class Topic extends Domain<Long> {

	private static final long serialVersionUID = -877640551276773678L;

	private Tab tab;

	private String title;
	private String content;

	private boolean stick;

	private boolean good;

	private int viewCount;

	private int replyCount;

	private Date publishedDate;

	private User publisher;

	public Tab getTab() {
		return tab;
	}

	public void setTab(Tab tab) {
		this.tab = tab;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isStick() {
		return stick;
	}

	public void setStick(boolean stick) {
		this.stick = stick;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}

}
