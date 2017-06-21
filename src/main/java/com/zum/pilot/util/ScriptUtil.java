package com.zum.pilot.util;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ScriptUtil {
  public static void alert(HttpServletResponse response, String msg, String url) {
    response.setContentType("text/html; charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      out.println("<script language=\"javascript\">");
      String alertMsg = "alert('" + msg + "'); location.href=\"" + url + "\"";
      out.println(alertMsg);
      out.println("</script>");
//      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
