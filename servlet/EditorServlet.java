/**
 * @title venderMachineNEO/servlet/EditorServlet.java
 * @file /WEB-INF/view/venderEditor.jsp
 * @formAction EditorServlet
 * @formMethod POST
 * @content drinkList, priceListの編集・追加・削除の入力フォーム
 *
 * @inputData ---- jsp -> Servlet ----
 * @param id -> indexEditList
 * @param dr -> drinkEditList
 * @param pr -> priceEditList
 * @param ap -> appendEditList
 * @param de -> deleteEditList
 *
 * @requestQuery http://localhost:8080/venderMachineNEO/EditorServlet
 *   ?id=&id=&id=&id=&id=&ap=50
 *   &dr=&dr=&dr=&dr=&dr=Milk&ap=Soda&ap=SodaEn
 *   &pr=&pr=&pr=&pr=&pr=&ap=1.20&de=de3
 *
 * @author shika
 * @date 2021-08-10
 */

package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.EditData;
import model.EditLogic;

@WebServlet("/EditorServlet")
public class EditorServlet extends MainVenderBundleServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = "/WEB-INF/view/venderEditor.jsp";
        RequestDispatcher dis = request.getRequestDispatcher(path);
        dis.forward(request, response);
    }//doGet()

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] indexEditAry = request.getParameterValues("id");
        String[] drinkEditAry = request.getParameterValues("dr");
        String[] priceEditAry = request.getParameterValues("pr");
        String[] appendEditAry = request.getParameterValues("ap");
        String[] deleteEditAry = request.getParameterValues("de");
        EditData editData = new EditData(
                indexEditAry, drinkEditAry, priceEditAry,
                appendEditAry, deleteEditAry);

        EditLogic editLogic = new EditLogic(editData);
        editLogic.editParse();
        super.doGet(request, response);
    }//doPost()

}//class
