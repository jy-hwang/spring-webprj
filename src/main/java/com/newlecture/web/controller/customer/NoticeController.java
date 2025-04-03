package com.newlecture.web.controller.customer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.newlecture.web.entity.NoticeView;
import com.newlecture.web.service.NoticeService;

@Controller
@RequestMapping("/customer/notice/")
public class NoticeController {
  
  @Autowired
  private NoticeService noticeService;

  @GetMapping("list")
  public String list() {
    //ModelAndView mv = new ModelAndView("");
    List<NoticeView> list = noticeService.getNoticeList("title", "", 1);
    //mv.addObject("list", list);
    return "notice.list";
  }

  @GetMapping("detail")
  public String detail(){
    return "notice.detail";
  }
}
