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
 * @see reference/venderEdit_En.jpg
 * @author shika
 * @date 2021-08-11
 */

package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.EditData;
import model.EditInputSecurity;
import model.EditMessage;
import model.EditTempLogic;

@WebServlet("/EditorServlet")
public class EditorServlet extends MainVenderBundleServlet {
    private static final long serialVersionUID = 1L;
    protected EditData editData;
    protected EditInputSecurity security;
    protected EditTempLogic editTemp;
    protected EditMessage editMess;

    public void init(ServletConfig config)
            throws ServletException {
        editData = new EditData();
        security = new EditInputSecurity(editData);
        editTemp = new EditTempLogic(editData);
        editMess = new EditMessage(locale);
    }//init()

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = "/WEB-INF/view/venderEditor.jsp";
        doForward(request, response, path);
    }//doGet()

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] indexEditAry = request.getParameterValues("id");
        String[] drinkEditAry = request.getParameterValues("dr");
        String[] priceEditAry = request.getParameterValues("pr");
        String[] appendEditAry = request.getParameterValues("ap");
        String[] deleteEditAry = request.getParameterValues("de");
        editData.setListValue(
            indexEditAry, drinkEditAry, priceEditAry,
            appendEditAry, deleteEditAry);

        //入力チェック(スクリプトタグ)
        security.setEditList();
        boolean canAccept = security.checkListElement();
        editMess.acceptMsg(canAccept);
        if(!canAccept) {
            String path = "/WEB-INF/view/venderEditor.jsp";
            doForward(request, response, path);
        }

        //入力チェック(appendEditList)
        boolean canAppend = editTemp.appendOperation(editMess);
        if(!canAppend) {
            String path = "/WEB-INF/view/venderEditor.jsp";
            doForward(request, response, path);
        }

        editTemp.sortByIndex();

        String path = "/WEB-INF/veiw/venderEditConfirm.jsp";
        doForward(request, response, path);
    }//doPost()

    private void doForward(
            HttpServletRequest request, HttpServletResponse response,
            String path) throws ServletException, IOException {

        RequestDispatcher dis = request.getRequestDispatcher(path);
        dis.forward(request, response);
    }//doForward()

}//class
