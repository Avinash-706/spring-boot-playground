package org.example.utility;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    
    private static SessionFactory st;
    
    static {
        try {
            st = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("SessionFactry creaton faild: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getFacotry() {
        return st;
    }
    
    public static void shutdwn() {
        if (st != null) {
            st.close();
        }
    }
}
