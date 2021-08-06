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
        String language = (String) request.getParameter("language");
        Locale locale = parse.buildLocale(language);
        super.setLocale(locale);
        calc.restractDidList(data, locale);
        mess.restractLocale(locale);
        super.setSession(request);

        super.doGet(request, response);
    }//doPost()

}//class

