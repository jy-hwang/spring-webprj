package com.newlecture.web.controller.customer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.newlecture.web.entity.NoticeView;
import com.newlecture.web.service.NoticeService;

@Controller
@RequestMapping("/customer/notice/")
public class NoticeController {

  @Autowired
  private NoticeService noticeService;

  @GetMapping("list")
  public String list(@RequestParam(value = "p", defaultValue = "1") Integer page, Model model) {
    System.out.println("page : " + page);

    List<NoticeView> list = noticeService.getNoticeList("title", "", page);
    model.addAttribute("list", list);
    return "notice.list";
  }

  @GetMapping("detail")
  public String detail() {
    return "notice.detail";
  }
}
