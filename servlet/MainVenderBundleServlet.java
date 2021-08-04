/**
 * @title venderMachineNEO / servlet / MainVenderBundleServlet.java
 * @content 自動販売機のロジック再現
 *          stylesheetなどの View関連は未実装
 *          Locale対応: 日本語 ja_JP, 英語 en
 *          Bundle対応で jsp内の分岐を最小限にし、Localeで表示切替を可能にした。
 *          その分、DrinkDataに Locale分岐を記述。
 * @see reference / venderMachine_RDD要件定義.txt
 *
 * @package ---- servlet ----
 * @class EncodingFilter / jsp,Servletを「UTF-8」でencodeing
 * @class MainVenderServlet / ◆EntryPoint 〔旧版〕
 * @class MainVenderBundleServlet / ◆EntryPoint 〔新版〕
 *        / #DrinkData data,
 *          #VenderCalc calc,
 *          #VenderMessage mess,
 *          #VenderParse parse,
 *          #RequestDispatcher dis
 *          #Locale locale
 *          #HttpSession session
 *          #double EX_RATE 為替レート 1$=100円
 *          -boolean init /
 *        +init() フィールド初期化
 *        #doGet()  jspの表示に必要な値の取得し、 forward
 *        #doPost() jspから order取得、orderを解析しロジックに渡す
 *        #setSession(HttpServletRequest)
 *        #setLocale(Locale)
 * @class ChangeLanguageServlet extends MainVenderServlet
 *
 * @package ---- model ----
 * @class DrinkData / ドリンクデータの定義
 *        / Locale locale,
 *          double EX_RATE,
 *          List<String> drinkListJp,
 *          List<String> drinkListEn,
 *          List<Integer> priceList
 *          List<String> priceListStr
 *          List<Integer> selsctList
 *          List<String> selectListStr /
 *        -listToStr(List<Integer>,List<String>,Locale,double)
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
 *        restractDidList(data,locale)//ロケール変更処理
 * @class VenderMessage / 表示メッセージの管理
 *        / String msg,
 *          double EX_RATE /
 *        buildMsg(String order, VenderCalc calc)
 *        getMsg()
 *        restractMsg(Locale)
 * @class VenderParse / orderの解析
 *        //
 *        parseOrder(String order, VenderCalc calc)
 *        parseLocale(String language)
 *
 * @package ---- WebContent/WEB-INF/view ----
 * @file venderView.jsp 〔旧版〕
 * @file venderViewBundle.jsp 〔新版〕
 *
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
 * @author shika
 * @date 2021-07-25 ～ 08-04
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

@WebServlet("/MainVenderBundleServlet")
public class MainVenderBundleServlet extends HttpServlet {
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
        this.locale = new Locale("en");
        //this.locale = Locale.getDefault();
        this.data = new DrinkData();
        this.calc = new VenderCalc(data, locale);
        this.mess = new VenderMessage(EX_RATE);
        this.parse = new VenderParse();

        //---- forward path ----
        String path = "/WEB-INF/view/venderViewBundle.jsp";
        this.dis = config.getServletContext().getRequestDispatcher(path);
    }//init()

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(init) { //doGet()初回のみ
            //this.locale = request.getLocale();
            //calc.setDrinkLocale(data, locale);
            setSession(request);
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

    protected void setSession(HttpServletRequest request) {
        this.session = request.getSession();
        List<String> drinkList = data.getDrinkList(locale);
        List<String> priceListStr = data.getPriceListStr(locale, EX_RATE);
        List<String> selectListStr = data.getSelectListStr(locale, EX_RATE);
        session.setAttribute("locale", locale.toString());
        session.setAttribute("EX_RATE", EX_RATE);
        session.setAttribute("drinkList", drinkList);
        session.setAttribute("priceListStr", priceListStr);
        session.setAttribute("selectListStr", selectListStr);
    }//setSession()

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String order = (String) request.getParameter("order");
        parse.parseOrder(order, calc);
        mess.buildMsg(order, locale, calc);

        doGet(request, response);
    }//doPost()

    protected void setLocale(Locale locale) {
        this.locale = locale;
    }

}//class

