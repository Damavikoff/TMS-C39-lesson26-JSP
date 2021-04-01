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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Кудин
 */
public class DataHandler extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        if (!request.getParameterMap().containsKey("title") || !request.getParameterMap().containsKey("population")) {
            return;
        }

        String title = request.getParameter("title").trim();
        DatabaseHandler dbh = new DatabaseHandler();
        List<String> problems = new ArrayList<>();
        Integer population = null;

        try {
            population = Integer.parseInt(request.getParameter("population").trim());
            if (population < 0) {
                problems.add("A negative population?");
            }
        } catch (NumberFormatException e) {
            problems.add("Invalid number");
        }

        if (title.isEmpty())  {
            problems.add("Invalid title");
        }

        if (!problems.isEmpty()) {
            request.getSession().setAttribute("problems", problems);
            response.sendRedirect(request.getContextPath() + "/view");
            return;
        }

        String query = "insert into cities (title, population) values ('" + title + "', " + population + ")";
        Integer result = dbh.executeUpdate(query);
        if (result == null || result == 0) {
            problems.add(result == null ? dbh.getLastError() : "No rows affected.");
            request.getSession().setAttribute("problems", problems);
        } else {
            request.getSession().removeAttribute("problems");
        }

        response.sendRedirect(request.getContextPath() + "/view");
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
