package servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ChangeLanguageServlet")
public class ChangeLanguageServlet extends MainVenderBundleServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }//doGet()

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String language = (String) request.getParameter("language");
        Locale locale = parse.buildLocale(language);
        super.setLocale(locale);
        calc.restractDidList(data, locale);
        mess.restractLocale(locale);
        super.setSession(request);

        super.doGet(request, response);
    }//doPost()

}//class

/*
【考察】 Servletの継承
* フィールドを共有したかったので Servletを継承したのだが、
* sessionフィールドは subの Servletから利用すると nullになるので、
* super.setSession()内で request.getSession()をして生成した。
*
* this.localeのように subからフィールドを変更しても反映されないので
* super.localeも同様、
* super.setLocale(locale);で変更できたが、
* mess, calcの情報は 旧Localeのまま。
*
* 新たに取得しているはずの current, didBuyListなども
* 変更後は 0になっている問題もある。
* Servletが内部プールに保存されていることが原因か
*
* フィールドからの参照(読み取り)はできるが、
* フィールド値の変更(書き込み)は反映されていないようだ。
* Servletの extends ではなく、同一Servlet内からならできそうだが、
* フィールドを他Servletと共有できなくなる。
*
* requestScopeの内容も反映されていないので
* super.setRequestScope(HttpServletRequest)のメソッド抽出をし、
* subServletから呼び出してみたが、解決せず。
* dis.forward(request, response);を subServletからしてみたが、解決せず。
*
*/