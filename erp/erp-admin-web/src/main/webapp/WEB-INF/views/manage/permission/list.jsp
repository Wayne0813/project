<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP - 系统管理 - 权限管理</title>
    <%@ include file="../../include/css.jsp"%>
</head>
<body class="hold-transition skin-purple sidebar-mini">
<div class="wrapper">
<%@ include file="../../include/header.jsp"%>
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
            <h3 class="box-title">权限列表</h3>
            <div class="box-tools">
                <a href="/manage/permission/add" class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 新增权限</a>
            </div>
        </div>
        <div class="box-body">
            <table class="table tree">
                <thead>
                <tr>
                    <th>权限名称</th>
                    <th>权限代号</th>
                    <th>资源URL</th>
                    <th>类型</th>
                    <th>#</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${permissionList}" var="permission">
                    <tr class="treegrid-${permission.id}
                    <c:if test="${permission.pid != 0}">
                        treegrid-parent-${permission.pid}
                    </c:if> ">
                        <td>${permission.permissionName}</td>
                        <td>${permission.permissionCode}</td>
                        <td>${permission.url}</td>
                        <td>${permission.permissionType}</td>
                        <td>
                            <a class="btn btn-primary btn-xs" href="/manage/permission/edit/${permission.id}" title="编辑"><i class="fa fa-pencil"></i></a>
                            <a class="btn btn-danger btn-xs deleteBtn" rel="${permission.id}"  href="javascript:;" title="删除"><i class="fa fa-trash"></i></a>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</section>
</div>
</div>
<%@include file="../../include/js.jsp"%>
<script>
    $(function () {
        $('.tree').treegrid();

        var msg = "${message}";
        if(msg){
            layer.msg(msg);
        }

        $(".deleteBtn").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除改权限么？",function() {
                window.location.href = "/manage/permission/delete/" + id;
            })
        });

    });
</script>
</body>
</html>
