package br.com.drauber.estudos.filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;

import br.com.drauber.estudos.util.HibernateUtil;

/**
 * Font: https://community.jboss.org/wiki/OpenSessionInView?_sscc=t
 * @author Levy Moreira
 *
 */

public class HibernateSessionRequestFilter implements Filter {
	 
    private SessionFactory sf;
 
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
 
        try {           
            sf.getCurrentSession().beginTransaction();
 
            // Call the next filter (continue request processing)
            chain.doFilter(request, response);
 
            // Commit and cleanup         
            sf.getCurrentSession().getTransaction().commit();
 
        } catch (StaleObjectStateException staleEx) {
            throw staleEx;
        } catch (Throwable ex) {
            // Rollback only
            ex.printStackTrace();
            try {
                if (sf.getCurrentSession().getTransaction().isActive()) {             
                    sf.getCurrentSession().getTransaction().rollback();
                }
            } catch (Throwable rbEx) {
               // Could not rollback transaction after exception!
            }
 
            // Let others handle it... maybe another interceptor for exceptions?
            throw new ServletException(ex);
        }
    }
 
    public void init(FilterConfig filterConfig) throws ServletException {
        sf = HibernateUtil.getSessionFactory();
    }
 
    public void destroy() {}
 
}