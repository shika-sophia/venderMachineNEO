package archive;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/MoneyServlet")
public class MoneyServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;


  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


  }//doGet()


  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // ---- read request from 'vender_index.jsp' ----
      request.setCharacterEncoding("UTF-8");
      int moneyFlow = (Integer)request.getAttribute("moneyFlow");

      // ---- add moneyFlow to moneyStock ----
      int moneyStock = 0;
      moneyStock += moneyFlow;

      // ---- set moneyStock to request scope ----
      request.setAttribute("moneyStock", moneyStock);

      // ---- "drinkName" and "price" already have been set to session scope in StartServlet ----

      // ---- foward to vender_index ----
      String path = "/vender_index.jsp";
      RequestDispatcher dis = request.getRequestDispatcher(path);
      dis.forward(request, response);
  }//doPost()

}//class
