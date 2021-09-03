package servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DrinkData;
import model.EditDecide;
import model.EditTempLogic;

@WebServlet("/EditConfirmServlet")
public class EditConfirmServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EditDecide decide;

    public void init(ServletConfig config)
            throws ServletException {
        this.decide = new EditDecide();
    }//init()

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }//doGet()

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String confirm = (String) request.getParameter("confirm");
        if(confirm.equals("ng")) {
            doRedirect(response);
        }

        HttpSession session = request.getSession();
        DrinkData data = (DrinkData) session.getAttribute("data");
        EditTempLogic editTemp = (EditTempLogic) session.getAttribute("editTemp");
        decide.setField(data, editTemp);
        decide.reBuildData();

        doRedirect(response);
    }//doPost()

    public void doRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("MainVenderBundleServlet");
    }

}//class
