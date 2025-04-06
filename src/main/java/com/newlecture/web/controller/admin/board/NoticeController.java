package com.newlecture.web.controller.admin.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("adminNoticeController")
@RequestMapping("/admin/board/notice/")
public class NoticeController {

  @GetMapping("list")
  public String list() {
    return "";
  }
  
  @PostMapping("reg")
  @ResponseBody
  public String reg(String title, String content) {
    return String.format("title : %s<br> content: %s<br>, ", title, content);
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
