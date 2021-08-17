/**
 * @title venderMachineNEO / servlet / AuthServlet.java
 * @content 編集機能に入る前にユーザー認証
 * @from venderViewBundle.jsp, venderAuth.jsp
 * @to   venderAuth.jsp, EditorServlet
 * @author shika
 * @date 2021-08-16
 */

package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AuthServlet")
public class AuthServlet extends MainVenderBundleServlet {
    private static final long serialVersionUID = 1L;
    private final String user = "shika";
    private final String pass = "1111";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String authMsg = mess.authMsg(locale);
        request.setAttribute("msg", authMsg);

        String path = "/WEB-INF/view/venderAuth.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        dis.forward(request, response);
    }//doGet()

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userInput = (String) request.getParameter("user");
        String passInput = (String) request.getParameter("pass");
        String order = (String) request.getParameter("order");

        boolean canAuth = judgeAuth(userInput, passInput);
        String path = "";
        if(canAuth && !order.equals("input0")) {
            mess.authEditable(locale);
            path = "EditorServlet";

        } else {
            mess.authFailed(locale);
            path = "MainVenderBundleServlet";
        }

        String msg = mess.getMsg(locale);
        request.setAttribute("msg", msg);
        response.sendRedirect(path);
    }//doPost()

    private boolean judgeAuth(String userInput, String passInput) {
        if(this.user.equalsIgnoreCase(userInput)
           && this.pass.equals(passInput)) {
            return true;
        }

        return false;
    }//judgeAuth()

}//class
