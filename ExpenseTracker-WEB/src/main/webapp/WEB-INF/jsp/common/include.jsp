<%@page import="java.util.Date"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="wm" uri="/WEB-INF/wmfmt.tld"%>
<jsp:useBean id="currDate" class="java.util.Date" />
<fmt:formatDate pattern='${initParam.dateFormat}' value='${currDate}' var="now"/>
<c:set var="wmparam" value="${applicationScope.wmParams}"/>