package com.newlecture.web.entity;

import java.util.Date;

public class NoticeView extends Notice {

  private int commentCount;

  public int getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(int commentCount) {
    this.commentCount = commentCount;
  }

  public NoticeView() {}

  public NoticeView(int noticeNo, String writerId, String title, String files, int hit, Date createdDate, Date updatedDate, Date deletedDate, boolean isDisclose, int commentCount) {
    super(noticeNo, writerId, title, "", files, hit, createdDate, updatedDate, deletedDate, isDisclose);
    this.commentCount = commentCount;
  }

}
