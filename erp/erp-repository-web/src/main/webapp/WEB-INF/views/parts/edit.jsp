<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-库存管理</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../include/css.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="parts"/>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                配件更新
            </h1>
        </section>

        <section class="content">
            <div class="box">
                <div class="box-body">
                    <form action="/parts/edit" method="post" id="editForm">
                        <div class="form-group">
                            <label class=" control-label">配件编号:</label>
                            <input type="text" disabled class="form-control" value="${parts.partsNo}" placeholder="请输入配件编号">
                        </div>
                        <div class="form-group">
                            <label>配件名称:</label>
                            <input type="hidden" name="id" value="${parts.id}" class="form-control" placeholder="请输入配件名称">
                            <input type="text" name="partsName" value="${parts.partsName}" class="form-control" placeholder="请输入配件名称">
                        </div>

                        <div class="form-group">
                            <label>进价:</label>
                            <input type="text" name="inPrice" value="${parts.inPrice}" class="form-control" placeholder="请输入进价">
                        </div>
                        <div class="form-group">
                            <label>售价:</label>
                            <input type="text" name="salePrice" value="${parts.salePrice}" class="form-control" placeholder="请输入售价">
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2">类型:</label>
                            <select name="typeId" class="form-control">
                                <option>请选择类型</option>
                                <c:forEach items="${typeList}" var="type">
                                    <option value="${type.id}" ${parts.typeId == type.id ? 'selected': ''} >${type.typeName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>产地:</label>
                            <input type="text" name="address" value="${parts.address}"  class="form-control" placeholder="请输入产地">
                        </div>
                    </form>
                    <button id="editBtn" class="btn btn-primary pull-left">保存</button>
                </div>

            </div>

        </section>
    </div>
    <%@ include file="../include/footer.jsp" %>
</div>
<%@ include file="../include/js.jsp" %>
<script>
$(function () {

    $("#editBtn").click(function () {
        $("#editForm").submit();
    });


})
</script>
</body>
</html>