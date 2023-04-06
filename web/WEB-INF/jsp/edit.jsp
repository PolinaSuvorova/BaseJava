<%@ page import="com.urise.webapp.util.DateUtil" %>
<%@ page import="com.urise.webapp.model.*" %>
<%@ page import="com.urise.webapp.model.ListTextSection" %>
<%@ page import="com.urise.webapp.model.CompanySection" %>
<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.util.UtilsResume" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <c:if test="${validate != null}">
        <c:forEach  var="valueerror" items="${validate}">
            <p style="background-color:tomato;">${valueerror}.</p>
        </c:forEach>
    </c:if>
    <form name='theForm' method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <h1>Имя:</h1>
        <dl>
            <input type="text"
                   name="fullName"
                   required="true"
                   size=55
                   pattern="[А-Яа-яЁё]{1,40} [А-Яа-яЁё]{1,40} [А-Яа-яЁё]{0,40}"
                   value="${resume.fullName}"
                   >
           </dl>
        <h2>Контакты:</h2>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>


        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:catch var="errorFlag">
                <c:set var="section" value="${resume.getSection(type)}"/>
            </c:catch>
            <c:choose>
                <c:when test="${empty errorFlag}">
                    <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>

                    <h2><a>${type.title}</a></h2>
                    <c:choose>
                        <c:when test="${type=='POSITION'}">
                            <input type='text' name='${type}'
                                   size=75

                                   value='<%=((TextSection) section).getDescription()%>' >
                        </c:when>
                        <c:when test="${type=='PERSONAL'}">
                    <textarea name='${type}' cols=75
                              rows=5><%=((TextSection) section).getDescription()%></textarea>
                        </c:when>
                        <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <textarea name='${type}' cols=75
                              rows=5><%=String.join("\n", ((ListTextSection) section).getTextSections())%></textarea>
                        </c:when>
                        <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                            <c:forEach var="org" items="<%=((CompanySection) section).getCompanies()%>"
                                       varStatus="counter">
                                <dl>
                                    <dt>Название учереждения:</dt>
                                    <dd><input type="text" name='${type}' size=100 value="${org.name}"></dd>
                                </dl>
                                <dl>
                                    <dt>Сайт учереждения:</dt>
                                    <dd><input type="text" name='${type}url' size=100 value="${org.website}"></dd>
                                    </dd>
                                </dl>
                                <br>
                                <div style="margin-left: 30px">
                                    <c:forEach var="pos" items="${org.periods}">
                                        <jsp:useBean id="pos" type="com.urise.webapp.model.Period"/>
                                        <dl>
                                            <dt>Начальная дата:</dt>
                                            <dd>
                                                <input type="text" name="${type}${counter.index}startDate" size=10
                                                       value="<%=DateUtil.outputDate(pos.getStartDate())%>"
                                                       placeholder="MM/yyyy">
                                            </dd>
                                        </dl>
                                        <dl>
                                            <dt>Конечная дата:</dt>
                                            <dd>
                                                <input type="text" name="${type}${counter.index}endDate" size=10
                                                       value="<%=DateUtil.outputDate(pos.getEndDate())%>"
                                                       placeholder="MM/yyyy">
                                        </dl>
                                        <dl>
                                            <dt>Должность:</dt>
                                            <dd><input type="text" name='${type}${counter.index}title' size=75
                                                       value="${pos.title}">
                                        </dl>
                                        <dl>
                                            <dt>Описание:</dt>
                                            <dd><textarea name="${type}${counter.index}description" rows=5
                                                          cols=75>${pos.description}</textarea></dd>
                                        </dl>
                                    </c:forEach>
                                </div>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <h2><a>${type.title}</a></h2>
                    <c:choose>
                        <c:when test="${type=='POSITION'}">
                            <input type='text' name='${type}'
                                   size=75
                                   value=''
                                   >
                        </c:when>
                        <c:when test="${type=='PERSONAL' || type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                          <textarea name='${type}' cols=75
                                    rows=5></textarea>
                        </c:when>
                        <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                            <dl>
                                <dt>Название учреждения:</dt>
                                <dd><input type="text" name='${type}' size=100 value=""></dd>
                            </dl>
                            <dl>
                                <dt>Сайт учереждения:</dt>
                                <dd><input type="text" name='${type}url' size=100 value=""></dd>
                                </dd>
                            </dl>
                            <br>
                            <div style="margin-left: 30px">
                                <dl>
                                    <dt>Начальная дата:</dt>
                                    <dd>
                                        <input type="text" name="${type}1startDate" size=10
                                               value=""
                                               placeholder="MM/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd>
                                        <input type="text" name="${type}1endDate" size=10
                                               value=""
                                               placeholder="MM/yyyy">
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd><input type="text" name='${type}1title' size=75
                                               value="">
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd><textarea name="${type}1description" rows=5
                                                  cols=75></textarea></dd>
                                </dl>
                            </div>
                        </c:when>
                    </c:choose>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="button" name="action" value="save" onclick='setVal("save")' >Сохранить</button>
        <button type="button" onclick="window.history.go(-1)" name="action" value="back" >Отменить</button>
            <input type="hidden" name='status'>

    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
<script>
    function setVal(buttonCmd) {
        document.forms['theForm'].status.value = buttonCmd;
        document.forms['theForm'].submit();
        return false;
    }

</script>