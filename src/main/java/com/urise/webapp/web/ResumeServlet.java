package com.urise.webapp.web;

import com.urise.webapp.model.Resume;
import com.urise.webapp.util.Config;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        //     String name = request.getParameter("name");
        //     response.getWriter().write(name == null? "Hello":"Helloy " + name);
        PrintWriter writer = response.getWriter();
        writer.println(
                "<table>\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<th>UUID</th><th>Full Name</th>\n" +
                "</tr>");
        List<Resume> list = Config.getInstance()
                .getSqlStorage()
                .getAllSorted();
        for (Resume r : list) {
            writer.println("<tr>");
            writer.println("<td>" + r.getUuid() + "</td>");
            writer.println("<td>" + r.getFullName() + "</td>");
            writer.println("</tr>");
        }
        writer.println(
                "</tbody\n" +
                "</table>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
