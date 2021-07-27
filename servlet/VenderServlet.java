/**
 * @title venderMachineNEO / servlet / VenderServlet.java
 * @content 自動販売機
 * @see reference / venderMachine_RDD.txt
 *
 * @package ---- servlet ----
 * @class EncodingFilter / jsp,Servletを「UTF-8」でencodeing
 * @class VenderServlet / ◆EntryPoint
 *        / DrinkData data,
 *          VenderCalc calc,
 *          VenderMessage mess,
 *          VenderParse parse,
 *          RequestDispatcher dis /
 *        init() フィールド初期化
 *        doGet()  jspの表示に必要な値の取得し、 forward
 *        doPost() jspから order取得、orderを解析しロジックに渡す
 * @class VenderFinishServlet
 *
 * @package ---- model ----
 * @class DrinkData / ドリンクデータの定義
 *        / List<String> drinkList,
 *          List<Integer> priceList /
 * @class VenderCalc / マネー管理、販売ロジック
 *        / int current,
 *          List<Boolean> canBuyList /
 * @class VenderMessage / 表示メッセージの管理
 *        / String msg /
 *        buildMsg()
 * @class VenderParse / orderの解析
 *        //
 *        parseOrder(String order)
 *
 * @package ---- WebContent/WEB-INF/view ----
 * @file venderView.jsp
 *
 * @author shika
 * @date 2021-07-25
 */

package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DrinkData;
import model.VenderCalc;
import model.VenderMessage;
import model.VenderParse;

@WebServlet("/VenderServlet")
public class VenderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DrinkData data;
    private VenderCalc calc;
    private VenderMessage mess;
    private VenderParse parse;
    private RequestDispatcher dis;

    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);
        this.data = new DrinkData();
        this.calc = new VenderCalc(data);
        this.mess = new VenderMessage();
        this.parse = new VenderParse();

        //---- forward path ----
        String path = "/WEB-INF/view/venderView.jsp";
        this.dis = config.getServletContext().getRequestDispatcher(path);
    }//init()

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int current = calc.getCurrent();
        String msg = mess.buildMsg();
        List<Boolean> canBuyList = calc.getCanBuyList();

        request.setAttribute("current", current);
        request.setAttribute("msg", msg);
        request.setAttribute("canBuyList", canBuyList);

        dis.forward(request, response);
    }//doGet()

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String order = (String) request.getAttribute("order");
        parse.parseOrder(order);

        doGet(request, response);
    }//doPost()

}//class
