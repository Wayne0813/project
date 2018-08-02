<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP - 系统管理 - 员工管理</title>
    <%@ include file="../../include/css.jsp"%>
</head>
<body class="hold-transition skin-purple sidebar-mini">
<div class="wrapper">
<%@ include file="../../include/header.jsp"%>
<jsp:include page="../../include/sider.jsp">
    <jsp:param name="menu" value="employee"/>
</jsp:include>
<div class="content-wrapper">
<section class="content-header">
    <h1>
        员工管理
    </h1>
</section>
<!-- Main content -->
<section class="content">
    <div class="box no-border">
        <div class="box-body">
            <form class="form-inline">
                <input type="text" name="nameMobile" placeholder="账号或手机号码" class="form-control" value="${param.nameMobile}">
                <select name="roleId" class="form-control">
                    <option value="">所有账号</option>
                    <c:forEach items="${roleList}" var="role">
                        <option value="${role.id}" ${param.roleId == role.id ? 'selected' : ''}>${role.roleName}</option>
                    </c:forEach>
                </select>
                <button class="btn btn-default">搜索</button>
            </form>
        </div>
    </div>
    <div class="box">
        <div class="box-header">
            <div class="box-tools">
                <a href="/manage/employee/add" class="btn btn-success btn-sm">
                    <i class="fa fa-plus"></i> 新增账号
                </a>
            </div>
        </div>
        <div class="box-body">
            <table class="table">
                <thead>
                <tr>
                    <th>账号</th>
                    <th>手机号码</th>
                    <th>角色</th>
                    <th>状态</th>
                    <th>创建时间</th>
                    <th>#</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${employeeList}" var="employee">
                    <tr>
                        <td>${employee.employeeName}</td>
                        <td>${employee.employeeTel}</td>
                        <td>
                            <c:forEach items="${employee.roleList}" var="role">
                                ${role.roleName}
                            </c:forEach>
                        </td>
                        <td>
                                ${employee.state == 1 ? "正常" : "禁用"}
                        </td>
                        <td>
                           <fmt:formatDate value="${employee.createTime}"/>
                        </td>
                        <td>
                            <a class="btn btn-warning btn-xs stateBtn" rel="${employee.id}" href="javascript:;" title="禁用"><i class="fa fa-${employee.state == 1 ? 'unlock' : 'lock'}"></i></a>
                            <a class="btn btn-primary btn-xs" href="/manage/employee/edit/${employee.id}"><i class="fa fa-edit"></i></a>
                            <a class="btn btn-danger btn-xs deleteBtn" rel="${employee.id}" href="javascript:;" title="删除"><i class="fa fa-trash"></i></a>
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


        $(".stateBtn").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要修改用户状态么？",function() {
                window.location.href = "/manage/employee/state/" + id;
            })
        });

        $(".deleteBtn").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除用户么？",function() {
                window.location.href = "/manage/employee/delete/" + id;
            })
        });


        var msg = "${message}";
        if(msg) {
            layer.msg(msg);
        }

    })
</script>
</body>
</html>
