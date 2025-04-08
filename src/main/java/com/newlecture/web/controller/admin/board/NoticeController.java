package com.newlecture.web.controller.admin.board;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller("adminNoticeController")
@RequestMapping("/admin/board/notice/")
public class NoticeController {

  @Autowired
  private ServletContext ctx;

  @GetMapping("list")
  public String list() {
    return "admin.board.notice.list";
  }

  @PostMapping("reg")
  public String reg(String title, String content, MultipartFile[] files, String category, String[] foods, String food) {// , HttpServletRequest request) {

    for (MultipartFile file : files) {
      long fileSize = file.getSize();
      String fileName = file.getOriginalFilename();
      System.out.printf("fileName:%s, fileSize:%d\n", fileName, fileSize);

      // ServletContext ctx = request.getServletContext();

      String realPath = getRealUploadPath();
      realPath += File.separator + fileName;
      File saveFile = new File(realPath);

      try {
        file.transferTo(saveFile);
      } catch (IllegalStateException e) {
        System.out.println(e.getMessage());
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }

      System.out.printf("realPath : %s\n", realPath);
    }


    System.out.println("==============");
    for (String f : foods) {
      System.out.println(f);
    }

    System.out.println("food : " + food);
    System.out.println("==============");
    //return String.format("title : %s<br> content: %s<br>,  category: %s<br>,", title, content, category);
    
    // 리디렉션 : list 페이지로
    return "redirect:list";
    // 포워딩 : return "admin/notice/reg";
  }


  @GetMapping("reg")
  public String regPage() {
    return "admin.board.notice.reg";
  }

  @GetMapping("edit")
  public String editPage() {
    return "admin.board.notice.edit";
  }
  
  @PutMapping("edit")
  public String edit() {
    return "admin.board.notice.edit";
  }

  @DeleteMapping("del")
  public String del() {
    return "admin.board.notice.del";
  }


  public String getRealUploadPath() {
    String tempUploadPath = System.getProperty("upload.path");

    String uploadPath = tempUploadPath + "/uploads";

    File uploadDir = new File(uploadPath);
    if (!uploadDir.exists()) {
      boolean created = uploadDir.mkdirs();
      if (created) {
        System.out.println("업로드 폴더 생성됨: " + uploadPath);
      } else {
        System.out.println("업로드 폴더 생성 실패!");
      }
    } else {
      System.out.println("업로드 폴더가 이미 존재함.");
    }

    return uploadPath;
  }


}
