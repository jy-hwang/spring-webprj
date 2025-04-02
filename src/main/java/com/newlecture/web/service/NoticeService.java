package com.newlecture.web.service;

import java.util.List;
import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;

public interface NoticeService {
  /* Admin 용 서비스 추가 */
  int discloseNoticeAll(int[] ids);

  int removeNoticeAll(int[] ids);

  int insertNotice(Notice notice);

  int deleteNotice(int id);

  int updateNotice(Notice notice);

  List<Notice> getNoticeNewestList();

  /* 일반 사용자용 서비스 */
  List<NoticeView> getNoticeList();

  List<NoticeView> getNoticeList(int page);

  List<NoticeView> getNoticeList(String field, String keyword, int page);

  List<NoticeView> getNoticeDiscloseList(String field, String keyword, int page);

  int getNoticeCount();

  int getNoticeCount(String field, String keyword);

  Notice getNotice(int id);

  Notice getNextNotice(int id);

  Notice getPrevNotice(int id);
}
