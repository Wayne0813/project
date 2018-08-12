<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家登录</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@ include file="include/css.jsp"%>
</head>
<body class="hold-transition login-page">
<div class="login-box">
    <div class="login-logo">
        <a href="../../index2.html"><b>车管家汽车维修平台③</b></a>
    </div>

    <div class="login-box-body">
        <form action="/" method="post" id="loginForm">
            <c:if test="${not empty message}">
                <div class="alert alert-danger">${message}</div>
            </c:if>
            <div class="form-group has-feedback">
                <input type="email" value="${userTel}" class="form-control" name="userTel" placeholder="请输入手机号码">
                <span class="glyphicon glyphicon-phone form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" class="form-control" name="password" placeholder="请输入密码">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox">
                        <label>
                            <input name="remember" value="remember" type="checkbox"> 记住我
                        </label>
                    </div>
                </div>
            </div>
        </form>
        <button type="button" id="btn" class="btn btn-primary btn-block btn-flat">登录</button>
    </div>
</div>
<%@ include file="include/js.jsp"%>
<script>
    $(function(){

        $("#btn").click(function () {
            $("#loginForm").submit();
        })

    })
</script>
</body>
</html>
