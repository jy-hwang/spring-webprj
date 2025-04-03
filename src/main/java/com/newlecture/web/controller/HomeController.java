package com.newlecture.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

  @RequestMapping("/index")
  public String index() {
    return "root.index";
  }

  @RequestMapping("/help")
  public String help() {
    return "root.help";
  }

}
