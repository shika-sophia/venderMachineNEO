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
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    private volatile boolean editFirst;

    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);
        editData = new EditData();
        security = new EditInputSecurity(editData);
        editTemp = new EditTempLogic(editData);
        editMess = new EditMessage(locale);
        this.editFirst = true;
    }//init()

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //---- sessionScope ----
        HttpSession session = request.getSession();
        if(editFirst) {
            List<String> drinkListJp = data.getDrinkList(new Locale("ja"));
            List<String> drinkListEn = data.getDrinkList(new Locale("en"));
            session.setAttribute("drinkListJp", drinkListJp);
            session.setAttribute("drinkListEn", drinkListEn);
            editFirst = false;

        } else {
            List<String> drinkListJp = editTemp.getDrinkJpTempList();
            List<String> drinkListEn = editTemp.getDrinkEnTempList();
            List<String> priceListStr =editTemp.getPriceTempList();
            session.setAttribute("drinkListJp", drinkListJp);
            session.setAttribute("drinkListEn", drinkListEn);
            session.setAttribute("priceListStr", priceListStr);
        }
        //---- reqestScope ----
        String editMsg = editMess.getEditMsg();
        request.setAttribute("editMsg", editMsg);

        //---- forward ----
        String path = "/WEB-INF/view/venderEditor.jsp";
        doForward(request, response, path);
    }//doGet()

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] indexEditAry = request.getParameterValues("id");
        String[] drinkJpEditAry = request.getParameterValues("drJp");
        String[] drinkEnEditAry = request.getParameterValues("drEn");
        String[] priceEditAry = request.getParameterValues("pr");
        String[] appendEditAry = request.getParameterValues("ap");
        String[] deleteEditAry = request.getParameterValues("de");
        editData.setListValue(
            indexEditAry, drinkJpEditAry, drinkEnEditAry,
            priceEditAry, appendEditAry, deleteEditAry);

        //入力チェック(スクリプトタグ)
        security.setEditList();
        boolean canAccept = security.checkListElement();
        editMess.acceptMsg(canAccept);
        if(!canAccept) {
            editFirst = true; //現在の editDataを破棄
            doGet(request,response);
        }

        //入力チェック(数値であるか)
        boolean canIndex = editTemp.checkIndexList(editData.getIndexEditList());
        boolean canPrice = editTemp.checkPriceList(editData.getPriceEditList());
        if(!canIndex || !canPrice) {
            editMess.IncorrectInput();
            doGet(request,response);
        }

        //TempListを生成
        editTemp.setValue();

        //入力チェック(appendEditList)
        boolean canAppend = editTemp.appendOperation(editMess);
        if(!canAppend) {
            doGet(request,response);
        }

        //indexで並べ替え
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

/*
【NullPo問題】 なぜか localeの値が継承されていない。
editMess = new EditMessage(locale);
System.out.println("locale: " + locale); //locale: null

locale = super.locale;と明示的に代入してみたが、結果は同様

AuthServletを処理遷移の間に挿入する前は ちゃんと機能していた。
継承関係は以下のように並列
*MainServlet <-- AuthServlet
*             ┗ EditorServlet
* 試しに Main <- Auth <- Editor のように直列にしてみたが
* 結果は同様。
*
* AuthServletのほうには継承されている
* String authMsg = mess.authMsg(locale);
* request.setAttribute("msg", authMsg);
*
* 【解決】
* AuthServletには init()がない。
* EditorServletには init()を Overrideしたが、
* super.init(config);を記述していなかったので、
* EditorServletのインスタンス時に、superのインスタンスも行われるが
* superのinit()が呼び出されておらず、locale値が nullになったままになっていた。
* super.init(config)を補ったら解決す。
*/