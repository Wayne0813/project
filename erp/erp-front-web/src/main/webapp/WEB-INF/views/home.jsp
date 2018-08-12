<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家-首页</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="include/header.jsp"%>
    <jsp:include page="include/sider.jsp">
        <jsp:param name="menu" value="home"/>
    </jsp:include>
    <div class="content-wrapper">
        <section class="content-header">
            <h1>

            </h1>
        </section>

        <section class="content">
            <div class="box">
                <div class="box-body">
                    Start creating your amazing application!
                </div>
            </div>
        </section>
    </div>
    <%@ include file="include/footer.jsp"%>
</div>
<%@ include file="include/js.jsp"%>
</body>
</html>
