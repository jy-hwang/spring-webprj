package com.newlecture.web.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiNoticeController")
@RequestMapping("/api/notice/")
public class NoticeController {

  @GetMapping("list")
  public String list() {
    
    return "공지사항 list";
  }
  
}
