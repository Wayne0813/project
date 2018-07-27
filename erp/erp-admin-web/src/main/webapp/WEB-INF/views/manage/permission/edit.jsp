<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP - 系统管理 - 修改</title>
    <%@include file="../../include/css.jsp"%>
</head>
<body class="hold-transition skin-purple sidebar-mini">
<div class="wrapper">
    <%@include file="../../include/header.jsp"%>
    <jsp:include page="../../include/sider.jsp">
        <jsp:param name="menu" value="manage_permission"/>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                权限管理
            </h1>
        </section>

        <section class="content">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">修改权限</h3>
                    <div class="box-tools">
                        <a href="/manage/permission" class="btn btn-success btn-sm">返回</a>
                    </div>
                </div>
                <div class="box-body">
                    <form action="/manage/permission/edit" method="post" id="editForm">
                        <div class="form-group">
                            <label>权限名称</label>
                            <input type="text" value="${permission.id}" name="id" hidden >
                            <input type="text" value="${permission.permissionName}" name="permissionName" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>权限代号</label>
                            <input type="text" value="${permission.permissionCode}" name="permissionCode" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>资源URL</label>
                            <input type="text" value="${permission.url}" name="url" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>权限类型</label>
                            <select name="permissionType" class="form-control">
                                <option ${permission.permissionType == "菜单" ? 'selected' : ''} value="菜单">菜单</option>
                                <option ${permission.permissionType == "按钮" ? 'selected' : ''} value="按钮">按钮</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>父权限</label>
                            <select name="pid" class="form-control">
                                <option value="0">顶级菜单</option>
                                <c:forEach items="${permissionListAndTypeIsMenu}" var="permi">
                                    <option  ${permission.pid ==  permi.id? 'selected' : ''} value="${permi.id}">${permi.permissionName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn pull-right btn-primary" id="editBtn">保存</button>
                </div>
            </div>
        </section>
    </div>
</div>

<%@include file="../../include/js.jsp"%>
<script>
    $(function () {
        $("#editBtn").click(function () {
            $("#editForm").submit();
        });
    })
</script>
</body>
</html>
