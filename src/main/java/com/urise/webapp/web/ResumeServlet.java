package com.urise.webapp.web;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.Config;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.UtilsResume;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getSqlStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                break;
            case "edit":
                r = storage.get(uuid);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String status = request.getParameter("status");

        if (action.equals("back")) {
            response.sendRedirect("resume");
            return;
        }

        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (!UtilsResume.isEmpty(value)) {
                r.addContact(type, new Contact(value));
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String valueSection = request.getParameter(type.name());
            String[] valuesSection = request.getParameterValues(type.name());
            AbstractSection aSection = null;
            if (valueSection != null && valueSection.trim().length() != 0) {
                switch (type) {
                    case PERSONAL, POSITION -> {
                        aSection = new TextSection(valueSection);
                        r.addSection(type, new TextSection(valueSection));
                        break;
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ArrayList<String> listString = new ArrayList<>();
                        String[] lines = valueSection.split("\n", 0);
                        Collections.addAll(listString, lines);
                        aSection = new ListTextSection(listString);
                        break;
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Company> comp = new ArrayList<>();
                        String[] urls = request.getParameterValues(type.name() + "url");
                        for (int i = 0; i < valuesSection.length; i++) {
                            String name = valuesSection[i];
                            if (!UtilsResume.isEmpty(name)) {
                                List<Period> periods = new ArrayList<>();
                                String line = type.name() + i;
                                String[] startDates = request.getParameterValues(line + "startDate");
                                String[] endDates = request.getParameterValues(line + "endDate");
                                String[] titles = request.getParameterValues(line + "title");
                                String[] descriptions = request.getParameterValues(line + "description");
                                if ( titles == null ) { continue; }
                                for (int j = 0; j < titles.length; j++) {
                                    if (!UtilsResume.isEmpty(titles[j])) {
                                        periods.add(
                                                new Period(
                                                        DateUtil.inputDate(startDates[j]),
                                                        DateUtil.inputDate(endDates[j]),
                                                        titles[j],
                                                        descriptions[j]));
                                    }
                                }
                                comp.add(new Company(periods, name, urls[i]));
                            }
                        }
                    }
                }

                if (aSection != null) {
                    r.addSection(type, aSection);
                }
            } else {
                r.getSections().remove(type);
            }
        }

        if (!action.equals("save")) {
            request.setAttribute("status", "");
            request.setAttribute("resume", r);
            request.setAttribute("action", "");
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp")
                    .forward(request, response);
            return;
        }

        List<String> validate = validate(request);
        if (validate.isEmpty()) {
            storage.update(r.getUuid(), r);
            response.sendRedirect("resume");
            return;
        } else {
            request.setAttribute("status", "");
            request.setAttribute("resume", r);
            request.setAttribute("action", "");

            request.setAttribute("validate", validate);
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp")
                    .forward(request, response);
            return;
        }
    }

    public List<String> validate(HttpServletRequest request) {
        String fullName = request.getParameter("fullName");
        List violations = new ArrayList<>();
        if (UtilsResume.isEmpty(fullName)) {
            violations.add("ФИО обязательно для ввода");
        }

        for (SectionType type : SectionType.values()) {
            String valueSection = request.getParameter(type.name());
            String[] valuesSection = request.getParameterValues(type.name());
            AbstractSection aSection = null;
            switch (type) {
                case POSITION -> {
                    if (UtilsResume.isEmpty(valueSection)) {
                        violations.add("Позиция обязательна для ввода");
                    }
                }
                case EXPERIENCE, EDUCATION -> {
                    List<Company> comp = new ArrayList<>();
                    String[] urls = request.getParameterValues(type.name() + "url");
                    for (int i = 0; i < valuesSection.length; i++) {
                        String name = valuesSection[i];
                        String line = type.name() + i;
                        String[] startDates = request.getParameterValues(line + "startDate");
                        String[] endDates = request.getParameterValues(line + "endDate");
                        String[] titles = request.getParameterValues(line + "title");
                        String[] descriptions = request.getParameterValues(line + "description");

                        if (UtilsResume.isEmpty(name) &&
                                (startDates != null ||
                                        endDates != null ||
                                        titles != null ||
                                        descriptions != null)) {
                            violations.add("Для секции " + type.name() + " Название компании обязательно ");
                            continue;
                        } else if (!UtilsResume.isEmpty(name) &&
                                (startDates == null ||
                                 endDates == null ||
                                 titles == null)) {
                            violations.add("Для секции " + type.name() + " " + name + " период и описание обязательны");
                            continue;
                        }
                        if ( titles == null ){
                            continue;
                        }
                        for (int j = 0; j < titles.length; j++) {
                            if (!UtilsResume.isEmpty(titles[j])) {
                                if (UtilsResume.isEmpty(startDates[j])) {
                                    violations.add("Для секции " + type.name() + " " + name + " введите дату начала");
                                    continue;
                                }
                                if (UtilsResume.isEmpty(endDates[j])) {
                                    violations.add("Для секции " + type.name() + " " + name + " введите дату окончания");
                                    continue;
                                }
                                if (UtilsResume.isEmpty(descriptions[j])) {
                                    violations.add("Для секции " + type.name() + " " + name + " введите описание");
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        }
        return violations;
    }
}
