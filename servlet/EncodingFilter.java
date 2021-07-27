package servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class EncodingFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException { }

    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ((HttpServletRequest)request).setCharacterEncoding("UTF-8");
        ((HttpServletResponse)response).setCharacterEncoding("UTF-8");

        chain.doFilter(request, response);
    }//doFilter()


    public void destroy() { }
}
