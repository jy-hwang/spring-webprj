package com.newlecture.web.controller.admin.board;

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

  @GetMapping("list")
  public String list() {
    return "";
  }

  @PostMapping("reg")
  @ResponseBody
  public String reg(String title, String content, MultipartFile file, String category, String[] foods, String food) {

    long fileSize = file.getSize();
    String fileName = file.getOriginalFilename();
    System.out.printf("fileName:%s, fileSize:%d\n", fileName, fileSize );
    
    System.out.println("==============");
    for (String f : foods) {
      System.out.println(f);
    }

    System.out.println("food : " + food);
    System.out.println("==============");
    return String.format("title : %s<br> content: %s<br>,  category: %s<br>,", title, content, category);
  }


  @GetMapping("reg")
  public String regPage() {
    return "admin/board/notice/reg";
  }

  @PutMapping("edit")
  public String edit() {
    return "";
  }

  @DeleteMapping("del")
  public String del() {
    return "";
  }

}
