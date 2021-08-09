package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditorServlet")
public class EditorServlet extends MainVenderBundleServlet {
    private static final long serialVersionUID = 1L;
    private String edit;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.edit = (String) request.getParameter("edit");
        String path = "";
        if(edit.equals("append")) {
            path = "/WEB-INF/view/append.jsp";
        } else if(edit.equals("delete")) {
            path = "/WEB-INF/view/delete.jsp";
        }

        RequestDispatcher dis = request.getRequestDispatcher(path);
        dis.forward(request, response);
    }//doGet()

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String[] editAry = request.getParameterValues("editAry");
        //parse.parseEdit(edit, editAry);

        super.doGet(request, response);
    }//doPost()

}//class
