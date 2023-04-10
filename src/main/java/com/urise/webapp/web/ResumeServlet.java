package com.urise.webapp.web;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view" -> r = storage.get(uuid);
            case "edit" -> {
                if (uuid == null) {
                    r = new Resume("", "");
                } else {
                    r = storage.get(uuid);
                }
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String status = request.getParameter("status");
        if (action != null && action.equals("back")) {
            response.sendRedirect("resume");
            return;
        }

        Resume r;
        boolean isNew = false;
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        try {
            r = storage.get(uuid);
        }catch (NotExistStorageException er){
            r = new Resume( fullName );
            isNew = true;
        }

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
                     }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ArrayList<String> listString = new ArrayList<>();
                        String[] lines = valueSection.split("\n", 0);
                        Collections.addAll(listString, lines);
                        listString.removeAll(Arrays.asList("", null));
                        aSection = new ListTextSection(listString);
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
                                if (titles == null) {
                                    continue;
                                }
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
                        if ( comp != null ) {
                            aSection = new CompanySection(comp);
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

        List<String> validate = validate(request);
        if (validate.isEmpty() && !status.isEmpty() && status.equals("save")) {
            if (isNew){
                storage.save(r);
            }else {
                storage.update(r.getUuid(), r);
            }
            response.sendRedirect("resume");
        } else {
            request.setAttribute("status", "");
            request.setAttribute("resume", r);
            request.setAttribute("validate", validate);
            request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp")
                    .forward(request, response);
        }
    }

    public List<String> validate(HttpServletRequest request) {
        String fullName = request.getParameter("fullName");
        List<String> violations = new ArrayList<>();
        if (UtilsResume.isEmpty(fullName)) {
            violations.add("ФИО обязательно для ввода");
        }

        for (SectionType type : SectionType.values()) {
            String valueSection = request.getParameter(type.name());
            String[] valuesSection = request.getParameterValues(type.name());
            switch (type) {
                case POSITION -> {
                    if (UtilsResume.isEmpty(valueSection)) {
                        violations.add("Позиция обязательна для ввода");
                    }
                }
                case EXPERIENCE, EDUCATION -> {
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
                        if (titles == null) {
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
