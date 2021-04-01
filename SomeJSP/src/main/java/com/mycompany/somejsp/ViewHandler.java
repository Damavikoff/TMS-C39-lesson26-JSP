/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.somejsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Кудин
 */
public class ViewHandler extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DataHandler</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DataHandler at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        
        Cookie[] cookies = request.getCookies();
        String burnedCookie = "user";
        boolean hasCookie = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(burnedCookie)) {
                    hasCookie = true;
                    break;
                }
            }
        }
        
        if (!hasCookie) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        DatabaseHandler dbh = new DatabaseHandler();
        List<String> problems = new ArrayList<>();
        if (request.getSession().getAttribute("problems") != null) {
            problems = (ArrayList)request.getSession().getAttribute("problems");
        }
        
        String query = "select id, title, population from cities order by 1 desc";
        List<Map<String, Object>> result = dbh.executeSelect(query);
        List<City> cities;
        
        if (result == null || result.isEmpty()) {
            problems.add(result == null ? dbh.getLastError() : "Нет результатов для вывода");
        } else {
            cities = City.getFromMapList(result);
            request.setAttribute("cities", cities);
        }
        
        if (!problems.isEmpty()) {
            request.getSession().setAttribute("problems", problems);
        } else {
            request.getSession().removeAttribute("problems");
        }
        
        request.getRequestDispatcher("view.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
