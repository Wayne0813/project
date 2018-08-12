<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-维修保养服务单</title>
    <%@ include file="../include/css.jsp"%>
    <style>
        .td_title {
            font-weight: bold;
        }
        .star {
            font-size: 20px;
            color: #ff7400;
        }
    </style>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="fixDeatil"/>
    </jsp:include>

    <!-- 右侧内容部分 -->
    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                项目维修
            </h1>
        </section>

        <section class="content">

            <div class="box">
                <div class="box-body no-padding">
                    <table class="table">
                        <tbody>
                        <tr>
                            <td class="td_title">车主姓名</td><td>${fixOrder.customerName}</td>
                            <td class="td_title">车主电话</td><td>${fixOrder.customerTel}</td>
                            <td class="td_title">车牌号码</td><td>${fixOrder.carLicence}</td>
                        </tr>
                        <tr>
                            <td class="td_title">车型</td><td>${fixOrder.carType}</td>
                            <td class="td_title">颜色</td><td>${fixOrder.carColor}</td>
                            <td class="td_title">时间</td><td><fmt:formatDate value="${fixOrder.orderTime}" pattern="yyyy-MM-dd"/></td>
                        </tr>
                        <tr>
                            <td class="td_title">维修员</td><td>${fixOrder.fixEmployeeName}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

            </div>

            <div class="panel panel-primary">
                <div class="panel-heading">${fixOrder.orderType}:备件列表</div>
                <table class="table table-bordered " style="border-width: 2px;">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>名称</th>
                        <th>数量</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${fixOrder.fixPartsList}" var="parts">
                        <tr>
                            <td>${parts.partsNo}</td>
                            <td>${parts.partsName}</td>
                            <td>${parts.partsNum}</td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>
            <c:if test="${curr_employee_id == fixOrder.fixEmployeeId}">
                <button class="btn btn-success btn-block btn-lg" id="doneBtn">完成</button>
            </c:if>
        </section>
    </div>
    <%@ include file="../include/footer.jsp" %>
</div>
<%@ include file="../include/js.jsp" %>
<script>
    $(function(){
        $("#doneBtn").click(function(){
            layer.confirm("确定已完成所有维修工作？", function(){
                window.location.href = "/fix/done/${fixOrder.orderId}";
            });
        })
    })
</script>
</body>
</html>
