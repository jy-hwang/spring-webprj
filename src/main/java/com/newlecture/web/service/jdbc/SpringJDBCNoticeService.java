package com.newlecture.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;
import com.newlecture.web.service.NoticeService;
import com.newlecture.web.util.CommonBase;
import com.newlecture.web.util.DatabaseUtil;

@Service
public class SpringJDBCNoticeService implements NoticeService {

  @Autowired
  private DataSource dataSource;

  private JdbcTemplate jdbcTemplate;
  
  public SpringJDBCNoticeService(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  Connection conn = null;
  PreparedStatement pStmt = null;
  Statement stmt = null;
  ResultSet rSet = null;

  /* Admin 용 서비스 추가 */
  public int discloseNoticeAll(int[] ids) {

    return 0;
  }

  public int removeNoticeAll(int[] ids) {
    int result = 0;

    String params = "";

    for (int i = 0; i < ids.length; i++) {
      params += ids[i];

      if (i < ids.length - 1) {
        params += ",";
      }
    }

    String query = " UPDATE notice SET deleted_date = NULL WHERE NO IN (" + params + ") ";

    try {
      conn = dataSource.getConnection();

      stmt = conn.createStatement();
      result = stmt.executeUpdate(query);

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (stmt != null) {
          stmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e2) {
        e2.printStackTrace();
      }
    }

    return result;
  }

  public int insertNotice(Notice notice) {
    int result = 0;

    String query = " INSERT INTO notice (writer_id, title, content, files, is_disclose) VALUES (?, ?, ?, ?, ?) ";


    try {
      conn = dataSource.getConnection();

      pStmt = conn.prepareStatement(query);
      pStmt.setString(1, notice.getWriterId());
      pStmt.setString(2, notice.getTitle());
      pStmt.setString(3, notice.getContent());
      pStmt.setString(4, notice.getFiles());
      pStmt.setBoolean(5, notice.isDisclose());
      result = pStmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (pStmt != null) {
          pStmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e2) {
        e2.printStackTrace();
      }
    }

    return result;

  }

  public int deleteNotice(int id) {

    return 0;
  }

  public int updateNotice(Notice notice) {

    return 0;
  }

  public List<Notice> getNoticeNewestList() {

    return null;
  }

  /* 일반 사용자용 서비스 */
  public List<NoticeView> getNoticeList() {

    return getNoticeList("title", "", 1);
  }

  public List<NoticeView> getNoticeList(int page) {

    return getNoticeList("title", "", page);
  }

  public List<NoticeView> getNoticeList(String field, String keyword, int page) {

    int offset = (page - 1) * CommonBase.PAGE_SIZE;
    String query = " SELECT no, title, writerId, hit, files, createdDate, updatedDate, deletedDate, isDisclose, commentCount FROM notice_view " + " WHERE " + field
        + " LIKE '%"+ keyword +"%' ORDER BY createdDate DESC LIMIT "+ CommonBase.PAGE_SIZE +" OFFSET " + offset + " ";

//    JdbcTemplate jdbcTemplate = new JdbcTemplate();
//    jdbcTemplate.setDataSource(dataSource);
    List<NoticeView> list = jdbcTemplate.query(query, new BeanPropertyRowMapper(NoticeView.class));

    return list;
  }

  public List<NoticeView> getNoticeDiscloseList(String field, String keyword, int page) {
    List<NoticeView> list = new ArrayList<NoticeView>();

    // field <<- title, writer_id

    String query = " SELECT no, title, writerId, hit, files, createdDate, updatedDate, deletedDate, isDisclose, commentCount FROM notice_view " + " WHERE " + field
        + " LIKE ? AND is_disclose = 1 ORDER BY createdDate DESC LIMIT ? OFFSET ? ";

    /*
     * 1, 6, 11, 16, 21, ... => an = 1 + (page - 1) * 5 5, 10, 15, 20, ... => page * 5 pageSize 5 page 4
     * offset = (page - 1) * pageSize = (4 - 1) * 5 = 15
     *
     */

    int offset = (page - 1) * CommonBase.PAGE_SIZE;

    try {
      conn = dataSource.getConnection();

      pStmt = conn.prepareStatement(query);
      pStmt.setString(1, "%" + keyword + "%");
      pStmt.setInt(2, CommonBase.PAGE_SIZE);
      pStmt.setInt(3, offset);

      rSet = pStmt.executeQuery();

      while (rSet.next()) {
        int noticeNo = rSet.getInt("no");
        String title = rSet.getString("title");
        String writerId = rSet.getString("writerId");
        String files = rSet.getString("files") == null ? "" : rSet.getString("files");
        int hit = rSet.getInt("hit");
        Date createdDate = rSet.getDate("createdDate");
        Date updatedDate = rSet.getDate("updatedDate");
        Date deletedDate = rSet.getDate("deletedDate");
        boolean isDisclose = rSet.getBoolean("isDisclose");
        int commentCount = rSet.getInt("commentCount");

        NoticeView notice = new NoticeView(noticeNo, writerId, title, files, hit, createdDate, updatedDate, deletedDate, isDisclose, commentCount);
        list.add(notice);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rSet != null) {
          rSet.close();
        }
        if (pStmt != null) {
          pStmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e2) {
        e2.printStackTrace();
      }
    }

    return list;
  }

  public int getNoticeCount() {

    return getNoticeCount("title", "");
  }

  public int getNoticeCount(String field, String keyword) {
    int count = 0;
    String query = " SELECT count(no) as totalCount FROM notice " + " WHERE " + field + " LIKE ? ";

    try {
      conn = dataSource.getConnection();

      pStmt = conn.prepareStatement(query);
      pStmt.setString(1, "%" + keyword + "%");
      rSet = pStmt.executeQuery();

      if (rSet.next()) {
        count = rSet.getInt("totalCount");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rSet != null) {
          rSet.close();
        }
        if (pStmt != null) {
          pStmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e2) {
        e2.printStackTrace();
      }
    }

    return count;
  }

  public Notice getNotice(int id) {
    Notice notice = null;

    String query =
        " SELECT no, writer_id AS writerId, title, content, hit, files, created_date AS createdDate, updated_date AS updatedDate, deleted_date AS deletedDate, is_disclose AS isDisclose FROM notice WHERE no = ? ";

    try {
      conn = dataSource.getConnection();

      pStmt = conn.prepareStatement(query);
      pStmt.setInt(1, id);
      rSet = pStmt.executeQuery();

      if (rSet.next()) {
        int noticeNo = rSet.getInt("no");
        String writerId = rSet.getString("writerId");
        String title = rSet.getString("title");
        String content = rSet.getString("content");
        String files = rSet.getString("files") == null ? "" : rSet.getString("files");
        Date createdDate = rSet.getDate("createdDate");
        Date updatedDate = rSet.getDate("updatedDate");
        Date deletedDate = rSet.getDate("deletedDate");
        int hit = rSet.getInt("hit");
        boolean disclose = rSet.getBoolean("isDisclose");

        notice = new Notice(noticeNo, writerId, title, content, files, hit, createdDate, updatedDate, deletedDate, disclose);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rSet != null) {
          rSet.close();
        }
        if (pStmt != null) {
          pStmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e2) {
        e2.printStackTrace();
      }
    }

    return notice;
  }

  public Notice getNextNotice(int id) {
    Notice notice = null;

    String query =
        " SELECT no, writer_id AS writerId, title, content, hit, files, created_date AS createdDate, updated_date AS updatedDate, deleted_date AS deletedDate, is_disclose AS isDisclose FROM notice "
            + " WHERE created_date > ( SELECT created_date FROM notice WHERE NO = ? AND deleted_date IS NOT NULL) LIMIT 1 ";

    try {
      conn = DatabaseUtil.getConnection();

      pStmt = conn.prepareStatement(query);
      pStmt.setInt(1, id);
      rSet = pStmt.executeQuery();

      if (rSet.next()) {
        int noticeNo = rSet.getInt("no");
        String writerId = rSet.getString("writerId");
        String title = rSet.getString("title");
        String content = rSet.getString("content");
        String files = rSet.getString("files") == null ? "" : rSet.getString("files");
        Date createdDate = rSet.getDate("createdDate");
        Date updatedDate = rSet.getDate("updatedDate");
        Date deletedDate = rSet.getDate("deletedDate");
        int hit = rSet.getInt("hit");
        boolean disclose = rSet.getBoolean("isDisclose");

        notice = new Notice(noticeNo, writerId, title, content, files, hit, createdDate, updatedDate, deletedDate, disclose);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rSet != null) {
          rSet.close();
        }
        if (pStmt != null) {
          pStmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e2) {
        e2.printStackTrace();
      }
    }

    return notice;
  }

  public Notice getPrevNotice(int id) {
    Notice notice = null;

    String query =
        " SELECT no, writer_id AS writerId, title, content, hit, files, created_date AS createdDate, updated_date AS updatedDate, deleted_date AS deletedDate, is_disclose AS isDisclose FROM notice "
            + " WHERE created_date < ( SELECT created_date FROM notice WHERE NO = ? AND deleted_date IS NOT NULL) ORDER BY created_date DESC LIMIT 1 ";

    try {
      conn = dataSource.getConnection();

      pStmt = conn.prepareStatement(query);
      pStmt.setInt(1, id);
      rSet = pStmt.executeQuery();

      if (rSet.next()) {
        int noticeNo = rSet.getInt("no");
        String writerId = rSet.getString("writerId");
        String title = rSet.getString("title");
        String content = rSet.getString("content");
        String files = rSet.getString("files") == null ? "" : rSet.getString("files");
        Date createdDate = rSet.getDate("createdDate");
        Date updatedDate = rSet.getDate("updatedDate");
        Date deletedDate = rSet.getDate("deletedDate");
        int hit = rSet.getInt("hit");
        boolean disclose = rSet.getBoolean("isDisclose");

        notice = new Notice(noticeNo, writerId, title, content, files, hit, createdDate, updatedDate, deletedDate, disclose);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if (rSet != null) {
          rSet.close();
        }
        if (pStmt != null) {
          pStmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e2) {
        e2.printStackTrace();
      }
    }

    return notice;
  }


}
