<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>従業員 一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th class="follow_id">社員番号</th>
                    <th class="follow_name">氏名</th>
                    <!--<th class="follow_action">フォロー</th>-->
                </tr>
                <c:forEach var="employeefollows" items="${employeefollows}" varStatus="status">
                       <tr class="row${status.count % 2}">
                           <td class="employee_id"><c:out value="${employeefollows.code}" /></td>
                           <td class="employee_name"><c:out value="${employeefollows.name}" /></td>
                           <!-- <td class="follow_action">
                                   <c:choose>
                                           <c:when test="${employeefollows.followed == true}">
                                               <a href="<c:url value='/employees/list/delete?id=${employeefollows.id}' />">フォロー外す</a>
                                           </c:when>
                                           <c:otherwise>
                                               <a href="<c:url value='/employees/list/new?id=${employeefollows.id}' />">フォローする</a>
                                           </c:otherwise>
                                   </c:choose>
                           </td>-->
                       </tr>
                </c:forEach>
            </tbody>
        </table>

    </c:param>
</c:import>