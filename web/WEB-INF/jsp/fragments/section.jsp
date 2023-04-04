<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListTextSection" %>
<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<c:forEach var="type" items="<%=SectionType.values()%>">
    <c:set var="section" value="${resume.getSection(type)}"/>
    <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
    <h2>${type.title}</h2>

    <c:choose>
        <c:when test="${type=='POSITION' }">
            <input type='text' name='${type}' size=100 value='<%=section%>'>
        </c:when>
        <c:when test="${type=='PERSONAL'}">
            <textarea name='${type}' cols=100 rows=5><%=section%></textarea>
        </c:when>
        <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <textarea name='${type}' cols=100 rows=5>
                        <%=String.join("\n", ((ListTextSection) section).getTextSections())%>
                    </textarea>
        </c:when>
        <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
            <c:forEach var="" items="<%=((CompanySection) section).getCompanies()%>">
                <jsp:useBean id="comp" type="com.urise.webapp.model.Company"/>
                <dl>
                    <dt>Название компании:</dt>
                    <dd><input type="text" name='${type}' size=100 value="<%=comp.getName()%>"></dd>
                </dl>
                <dl>
                    <dt>Сайт компании:</dt>
                    <dd><input type="text" name='${type}url' size=100 value="<%=comp.getWebsite()%>"></dd>
                    </dd>
                </dl>
                <br>
                <div style="margin-left: 30px">
                    <c:forEach var="period" items="${comp.periods}">
                        <jsp:useBean id="period" type="com.urise.webapp.model.Period"/>
                        <dl>
                            <dt>Начальная дата:</dt>
                            <dd>
                                <input type="text" name="${type}${counter.index}startDate" size=10
                                       value="<%=DateUtil.outputDate(period.getStartDate())%>" placeholder="MM/yyyy">
                            </dd>
                        </dl>
                        <dl>
                            <dt>Конечная дата:</dt>
                            <dd>
                                <input type="text" name="${type}${counter.index}endDate" size=10
                                       value="<%=DateUtil.outputDate(period.getEndDate())%>" placeholder="MM/yyyy">
                        </dl>
                        <dl>
                            <dt>Должность:</dt>
                            <dd><input type="text" name='${type}${counter.index}title' size=100
                                       value="${period.getTitle( )}">
                        </dl>
                        <dl>
                            <dt>Описание:</dt>
                            <dd><textarea name="${type}${counter.index}description" rows=5
                                          cols=75>${period.description}</textarea></dd>
                        </dl>
                    </c:forEach>
                </div>
            </c:forEach>
        </c:when>
    </c:choose>
</c:forEach>


