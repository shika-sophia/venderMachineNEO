package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Drink;


@WebServlet("/StartServlet")
public class StartServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //initialize parameter
      int moneyStock = 0;

      //---- call Drink.drinkName() and price() ----
      Drink drink = new Drink();
      List<String> drinkName = drink.drinkName();
      List<Integer> price = drink.price();

      //---- set them to session scope ----
      HttpSession session = request.getSession();
      session.setAttribute("drinkName", drinkName);
      session.setAttribute("price", price);

      //---- set initializer to request scope ----
      request.setAttribute("moneyStock", moneyStock);

      //---- forward to vender_index.jsp ----
      String path = "/vender_index.jsp";
      RequestDispatcher dis = request.getRequestDispatcher(path);
      dis.forward(request, response);

  }//doGet()


  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

  }//doPost()

}//class
