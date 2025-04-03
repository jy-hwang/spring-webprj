package com.newlecture.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

  @RequestMapping("index")
  public void index(HttpServletResponse response) throws IOException {
    PrintWriter out = response.getWriter();
    out.println("Hello Index");
    //return "root.index";
  }

}
