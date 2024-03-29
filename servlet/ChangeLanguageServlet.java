/**
 * Here is Unused Servlet
 * @see reference/venderAnalysis.txt
 */
package servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/ChangeLanguageServlet")
public class ChangeLanguageServlet extends MainVenderBundleServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }//doGet()

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String order = (String) request.getParameter("order");

        Locale locale = null;
        if(order.equals("日本語") || order.equals("English")) {
            locale = buildLocale(order);
            calc.restractDidList(data, locale);
            mess.restractLocale(locale);

            //localeChanged = true;
            //first = true;
        }

        parse.parseOrder(order, calc);
        mess.buildMsg(order, locale, calc);

        super.doGet(request, response);
    }//doPost()

    private Locale buildLocale(String order) {
        String parsedLanguage = "";
        if(order.equals("日本語")) {
            parsedLanguage = "ja";
        } else if(order.equals("English")){
            parsedLanguage = "en";
        }

        this.locale = new Locale(parsedLanguage);
        super.setLocale(locale);
        return locale;
    }//buildLocale()

}//class

/*
//====== ChangeLanguageServlet(再論) / 2021-08-18 ======
このServletを利用時に不具合のあった requestScopeが機能しない問題。
venderAuthからの[戻る]ボタンでは current,msgも ちゃんと表示されたので
    request.setAttribute()をここですればいいのかも
    super.doGet()で処理遷移するのではなく、sendRedirect()かも。
MainVenderBundleServlet.doPost()に記述した下記のコードを
バックアップし、再度このServletで機能できるか挑戦してみる。


msgと購入リストが enのままだが、やっぱり、localeの変更ができていない。
drinkList, priceList, selectList, button は日本語に変換、
ここは localeが変更されている。なんじゃりゃ。

//====== MainVenderBundleServlet.doPost() / 2021-08-18 ======
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String order = (String) request.getParameter("order");

    if(order.equals("日本語") || order.equals("English")) {
        buildLocale(order);
        calc.restractDidList(data, locale);
        mess.restractLocale(locale);

        this.localeChanged = true;
        this.first = true;
    }

    parse.parseOrder(order, calc);
    mess.buildMsg(order, locale, calc);

    doGet(request, response);
}//doPost()

private void buildLocale(String order) {
    String parsedLanguage = "";
    if(order.equals("日本語")) {
        parsedLanguage = "ja";
    } else if(order.equals("English")){
        parsedLanguage = "en";
    }

    this.locale = new Locale(parsedLanguage);
}//buildLocale()

◆Redirect
String path = "MainVenderBundleServlet";
response.sendRedirect(path);
これだと、全く日本語表示にならず、localeの初期状態で表示。
ここでの処理は ChangeServletが持つ superに反映していて、
リダイレクトで MainServletに行ったものは、
別のrequestであるし、別のインスタンスなのでは？
current, didBuyListは正しく表示。

◆super.doGet(request, response);
sessionScopeのlocaleは変更されているが、
requestScopeの current,canBuyList,didBuyListの localeは元のまま
ただし、同じくrequestScopeの msgは「日本語に変更しました。」と正しく表示。

【問題】一度 locale en -> jaにすると、再度 ja -> enに戻らない問題。
MainServletのみでは、ちゃんとできていたのだが、ChangeServletはこれができていない。

やはり、NoUsedに戻すか・・
ちなみに このServletを利用しない場合は
//@WebServlet("/ChangeLanguageServlet")
アノテーションをコメントアウトすると Tomcatコンテナにdeployされなくなる。
venderViewBundle.jsp
    formaction="ChangeLanguageServlet" formmethod="POST"
これも削除しておく。

*/