<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-首页</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="../include/css.jsp"%>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="employee"/>
    </jsp:include>
    <div class="content-wrapper">

        <section class="content">
            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">员工管理</h3>
                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool"  title="Collapse">
                            <i class="fa fa-plus"></i> 添加员工</button>
                    </div>
                </div>
                <div class="box-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>姓名</th>
                            <th>部门</th>
                            <th>手机</th>
                            <th>#</th>
                        </tr>
                        </thead>
                        <tbody>

                        <c:forEach items="${page.list}" var="employee">
                            <tr>
                                <td>${employee.employeeName}</td>
                                <td>${employee.role.roleName}</td>
                                <td>${employee.employeeTel}</td>
                                <td>
                                    <a href="">禁用</a>
                                    <a href="">删除</a>
                                    <a href="">编辑</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <ul id="pagination" class="pagination pull-right"></ul>
                </div>
            </div>
        </section>
    </div>
    <%@ include file="../include/footer.jsp"%>
</div>
<%@ include file="../include/js.jsp"%>
<script>
    $(function(){

        // 分页
        $("#pagination").twbsPagination({
            totalPages : 10,
            visiblePages : 3,
            first : '<<',
            last:'>>',
            prev:'<',
            next:'>',
            href:"?p={{number}}"
        });

    });
</script>
</body>
</html>

