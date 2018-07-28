<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>ERP - 系统管理 - 角色管理</title>
    <%@ include file="../../include/css.jsp"%>
</head>
<body class="hold-transition skin-purple sidebar-mini">
<div class="wrapper">
<%@ include file="../../include/header.jsp"%>
<jsp:include page="../../include/sider.jsp">
    <jsp:param name="menu" value="manage_role"/>
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
            <h3 class="box-title">角色列表</h3>
            <div class="box-tools">
                <a href="/manage/role/add" class="btn btn-success btn-sm"><i class="fa fa-plus"></i> 新增角色</a>
            </div>
        </div>
        <div class="box-body">
            <table class="table tree">
                <tbody>
                <c:forEach items="${roleList}" var="role">
                    <tr class="bg-blue">
                        <td>
                            角色名称：<strong>${role.roleName}</strong>
                            <span class="pull-right">
                                <a style="color: #fff;" href="/manage/role/edit/${role.id}"><i class="fa fa-pencil"></i></a> &nbsp; &nbsp;
                                <a style="color: #fff;" class="deleteBtn" rel="${role.id}" href="javascript:;"><i class="fa fa-trash"></i></a>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <c:forEach items="${role.permissionList}" var="permission">
                                <i class="fa fa-circle"></i> ${permission.permissionName} &nbsp; &nbsp; &nbsp; &nbsp;
                            </c:forEach>
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

        $(".deleteBtn").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除该角色?", function () {
                window.location.href = "/manage/role/delete/" + id;
            });

        });

       /* //删除
        $(".delLink").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除该角色？",function (index) {
                layer.close(index);
                $.get("/manage/roles/"+id+"/del").done(function (result) {
                    if(result.status == 'success') {
                        window.history.go(0);
                    } else {
                        layer.msg(result.message);
                    }
                }).error(function () {
                    layer.msg("服务器忙");
                });
            })
        });*/

       var msg = "${message}";
       if(msg) {
           layer.msg(msg);
       }
    });
</script>
</body>
</html>
