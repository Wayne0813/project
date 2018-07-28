<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP - 系统管理 - 修改角色</title>
    <%@include file="../../include/css.jsp"%>
</head>
<body class="hold-transition skin-purple sidebar-mini">
<div class="wrapper">

    <%@include file="../../include/header.jsp"%>
    <jsp:include page="../../include/sider.jsp">
        <jsp:param name="menu" value="manage_roles"/>
    </jsp:include>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                角色管理
            </h1>
        </section>

        <section class="content">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">修改角色</h3>
                    <div class="box-tools">
                        <a href="/manage/roles" class="btn btn-success btn-sm">返回</a>
                    </div>
                </div>
                <div class="box-body">
                    <form action="/manage/role/edit" method="post" id="saveForm">
                        <div class="form-group">
                            <label>角色名称</label>
                            <input type="text" value="${role.id}" name="id" hidden>
                            <input type="text" value="${role.roleName}" name="roleName" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>角色代号</label>
                            <input type="text" value="${role.roleCode}" name="roleCode" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>权限配置</label>
                            <table class="table tree">
                                <tbody>
                                <c:forEach items="${permissionList}" var="permission">
                                    <tr class="treegrid-${permission.id}
                                        <c:if test="${permission.pid != 0}">
                                            treegrid-parent-${permission.pid}
                                        </c:if>">
                                        <td>
                                            <input type="checkbox" ${permission.isChecked} name="permissionId"  value="${permission.id}">
                                        </td>
                                        <td>${permission.permissionName}</td>
                                        <td>${permission.permissionCode}</td>
                                        <td>${permission.url}</td>
                                        <td>${permission.permissionType}</td>
                                        <%--<td>
                                            <a class="btn btn-primary btn-xs" href="" title="编辑"><i class="fa fa-pencil"></i></a>
                                            <a class="btn btn-danger btn-xs delLink" ref="${permission.id}" href="javascript:;" title="删除"><i class="fa fa-trash"></i></a>
                                        </td>--%>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </form>
                </div>
                <div class="box-footer">
                    <button class="btn pull-right btn-primary" id="saveBtn">保存</button>
                </div>
            </div>
        </section>
    </div>
</div>
<%@include file="../../include/js.jsp"%>
<script>
    $(function () {
        $("#saveBtn").click(function () {
            $("#saveForm").submit();
        });
        $('.tree').treegrid();
        $('input[type=checkbox]').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' /* optional */
        });
    })
</script>
</body>
</html>
