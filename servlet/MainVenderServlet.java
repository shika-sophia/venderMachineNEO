/**
 * @title venderMachineNEO / servlet / MainVenderServlet.java
 * @content 自動販売機のロジック再現
 *          stylesheetなどの View関連は未実装
 *          Locale対応: 日本語 ja_JP, 英語 en
 * @see reference / venderMachine_RDD要件定義.txt
 *
 * @package ---- servlet ----
 * @class EncodingFilter / jsp,Servletを「UTF-8」でencodeing
 * @class MainVenderServlet / ◆EntryPoint
 *        / #DrinkData data,
 *          #VenderCalc calc,
 *          #VenderMessage mess,
 *          #VenderParse parse,
 *          #RequestDispatcher dis
 *          #Locale locale
 *          #HttpSession session
 *          #double EX_RATE 為替レート 1$=100円
 *          -boolean init /
 *        init() フィールド初期化
 *        doGet()  jspの表示に必要な値の取得し、 forward
 *        doPost() jspから order取得、orderを解析しロジックに渡す
 *
 * @package ---- model ----
 * @class DrinkData / ドリンクデータの定義
 *        / List<String> drinkListJp,
 *          List<String> drinkListEn,
 *          List<Integer> priceList /
 * @class VenderCalc / マネー管理、販売ロジック
 *        / List<String> drinkList,
 *          List<Integer> priceList,
 *          List<Boolean> canBuyList
 *          List<String> didBuyList,
 *          int current,
 *          int input,
 *          String buyDrink, /
 *        judgeCanBuy(int input) //現在の金額で買えるか
 *        doBuy(int index)       //実際の購入処理
 *        returnMoney()          //返金処理
 *        -addMoney(int input)   //現在金額の加算処理
 * @class VenderMessage / 表示メッセージの管理
 *        / String msg,
 *          double EX_RATE /
 *        buildMsg(String order, VenderCalc calc)
 *        getMsg()
 * @class VenderParse / orderの解析
 *        //
 *        parseOrder(String order, VenderCalc calc)
 *
 * @package ---- WebContent/WEB-INF/view ----
 * @file venderView.jsp
 * @package ---- WebContent/WEB-INF/classes ----
 * @file vender_ja.properties //Bundle用プロパティファイル
 * @file vender_en.properties //下記 txtファイルを UTF-8でエンコード
 * @file vender_ja.txt // >native2asciiでエンコードする前の元データ
 * @file vender_en.txt // txtファイル内にコマンド全文あり
 *
 * @library JSTLライブラリ
 * @deploy /WEB-INF/lib/jstl-api-1.2.jar
 * @deploy /WEB-INF/lib/jstl-impl-1.2.jar
 *
 * @result 実行結果画像
 * @see reference/venderView_input110.jpg
 * @see reference/venderView_button.jpg
 * @see reference/venderView_returnMoney.jpg
 * @see reference/venderView_localeEn.jpg
 *
 * @see venderBundle.jsp / MainVenderBungleServlet.java
 * => Bundle対応の新版 jsp/Servletを作成。
 *
 * @author shika
 * @date 2021-07-25 ～ 08-03
 */

package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    protected HttpSession session;
    protected Locale locale;
    protected final double EX_RATE = 100d;//為替レート 1$=100円
    private boolean init = true;  //doGet()の初回か

    public void init(ServletConfig config)
            throws ServletException {
        super.init(config);
        //this.locale = new Locale("en");
        this.locale = Locale.getDefault();
        this.data = new DrinkData();
        this.calc = new VenderCalc(data, locale);
        this.mess = new VenderMessage(EX_RATE);
        this.parse = new VenderParse();

        //---- forward path ----
        String path = "/WEB-INF/view/venderView.jsp";
        this.dis = config.getServletContext().getRequestDispatcher(path);
    }//init()

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(init) { //doGet()初回のみ
            this.locale = request.getLocale();
            calc.setDrinkLocale(data, locale);

            this.session = request.getSession();
            List<String> drinkList = data.getDrinkList(locale);
            List<Integer> priceList = data.getPriceList();
            session.setAttribute("locale", locale.toString());
            session.setAttribute("EX_RATE", EX_RATE);
            session.setAttribute("drinkList", drinkList);
            session.setAttribute("priceList", priceList);

            this.init = false;
        }

        int current = calc.getCurrent();
        String msg = mess.getMsg(locale);
        List<Boolean> canBuyList = calc.getCanBuyList();
        List<String> didBuyList = calc.getDidBuyList();

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
        mess.buildMsg(order, locale, calc);

        doGet(request, response);
    }//doPost()

}//class

/*
【設定】
◆Locale対応
＊英語版の表示
・//this.locale = new Locale("en");を ONにする。
   this.locale = new Locale(
       Locale.getDefault().getLanguage());を OFFにする。
・doGet()内 if(init){ }の
    this.locale = request.getLocale();
    calc.setDrinkLocale(data, locale);
を OFFにする。

@see reference/venderView_localeEn.jpg

JSP【考察】 JavaScript
◆<form>内 onsubmit属性
onsubmit="return document.orderForm.order.value != '' "
ラジオボタンが未チェックの場合、送信無効。
購入ボタン時にも作用してしまい、ラジオボタンをチェックしていないと
ボタンが無効になる。

onsubmit属性を消去すると、
未チェックのまま入金で order=nullとなり、NullPointerException

<input type="hidden" name="order" value="input0">で解決

JSP【考察】Locale対応
Servletから来る localeを元に場合分けして表示したが、
<c:choose>
  <c:when>
  <c:otherwise>
が何度も出てきて可読性に欠き、別ページにしたほうが見やすい。

それか<fmt:Bundle>で propertyファイルの切り替えをして
同じページを 日本語/英語に切り替えるのがオブジェクト指向ぽい。

Localeを切り替えても、100円 -> $100になるので、
缶ジュース１本 $100(=１万円)になってしまう。
為替レートで調整する計算が必要

=> Bundle対応の新版 jsp/Servletを作成。
@see venderBundle.jsp / MainVenderBungleServlet.java
*/