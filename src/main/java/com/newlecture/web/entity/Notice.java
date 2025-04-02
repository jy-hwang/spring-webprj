package com.newlecture.web.entity;

import java.util.Date;

public class Notice {
  private int noticeNo;

  private String writerId;

  private String title;

  private String content;

  private String files;

  private int hit;

  private Date createdDate;

  private Date updatedDate;

  private Date deletedDate;

  private boolean disclose;

  public Notice() {}

  public Notice(int noticeNo, String writerId, String title, String content, String files, int hit, Date createdDate, Date updatedDate, Date deletedDate, boolean disclose) {
    super();
    this.noticeNo = noticeNo;
    this.writerId = writerId;
    this.title = title;
    this.content = content;
    this.files = files;
    this.hit = hit;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
    this.deletedDate = deletedDate;
    this.disclose = disclose;
  }

  public int getNoticeNo() {
    return noticeNo;
  }

  public void setNoticeNo(int noticeNo) {
    this.noticeNo = noticeNo;
  }

  public String getWriterId() {
    return writerId;
  }

  public void setWriterId(String writerId) {
    this.writerId = writerId;
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

  public String getFiles() {
    return files;
  }

  public void setFiles(String files) {
    this.files = files;
  }

  public int getHit() {
    return hit;
  }

  public void setHit(int hit) {
    this.hit = hit;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Date getUpdatedDate() {
    return updatedDate;
  }

  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  public Date getDeletedDate() {
    return deletedDate;
  }

  public void setDeletedDate(Date deletedDate) {
    this.deletedDate = deletedDate;
  }

  public boolean isDisclose() {
    return disclose;
  }

  public void setDisclose(boolean disclose) {
    this.disclose = disclose;
  }

}
