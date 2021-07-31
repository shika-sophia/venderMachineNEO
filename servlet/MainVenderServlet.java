/**
 * @title venderMachineNEO / servlet / MainVenderServlet.java
 * @content 自動販売機
 * @see reference / venderMachine_RDD要件定義.txt
 *
 * @package ---- servlet ----
 * @class EncodingFilter / jsp,Servletを「UTF-8」でencodeing
 * @class MainVenderServlet / ◆EntryPoint
 *        / #DrinkData data,
 *          #VenderCalc calc,
 *          #VenderMessage mess,
 *          #VenderParse parse,
 *          #RequestDispatcher dis /
 *        init() フィールド初期化
 *        doGet()  jspの表示に必要な値の取得し、 forward
 *        doPost() jspから order取得、orderを解析しロジックに渡す
 * @class VenderFinishServlet extends MainVenderServlet
 *
 * @package ---- model ----
 * @class DrinkData / ドリンクデータの定義
 *        / List<String> drinkList,
 *          List<Integer> priceList /
 * @class VenderCalc / マネー管理、販売ロジック
 *        / int current,
 *          int input,
 *          String buyDrink,
 *          List<Boolean> canBuyList /
 *        judgeCanBuy(int input) //現在の金額で買えるか
 *        doBuy(int index)       //実際の購入処理
 *        -addMoney(int input)   //現在金額の加算処理
 * @class VenderMessage / 表示メッセージの管理
 *        / String msg /
 *        buildMsg(String order, VenderCalc calc)
 *        getMsg()
 * @class VenderParse / orderの解析
 *        //
 *        parseOrder(String order, VenderCalc calc)
 *
 * @package ---- WebContent/WEB-INF/view ----
 * @file venderView.jsp
 *
 * @deploy /WEB-INF/lib/jstl-api-1.2.jar
 * @deploy /WEB-INF/lib/jstl-impl-1.2.jar
 * @see reference/venderViewBasic.jpg
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

@WebServlet("/MainVenderServlet")
public class MainVenderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected DrinkData data;
    protected VenderCalc calc;
    protected VenderMessage mess;
    protected VenderParse parse;
    protected RequestDispatcher dis;

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
        String msg = mess.getMsg();
        List<Boolean> canBuyList = calc.getCanBuyList();
        List<String> didBuyList = calc.getDidBuyList();

        request.setAttribute("data", data);
        request.setAttribute("current", current);
        request.setAttribute("msg", msg);
        request.setAttribute("canBuyList", canBuyList);
        request.setAttribute("didBuyList", didBuyList);

        dis.forward(request, response);
    }//doGet()

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String order = (String) request.getParameter("order");
        parse.parseOrder(order, calc);
        mess.buildMsg(order, calc);

        doGet(request, response);
    }//doPost()

}//class
