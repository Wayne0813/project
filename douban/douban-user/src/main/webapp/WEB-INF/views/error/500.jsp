<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 3 | 500 Error</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="../include/css.jsp"%>
</head>
<body class="hold-transition sidebar-mini">

<div class="wrapper">

  <div class="content">
  
    <section class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1>资源飞走了...</h1>
          </div>
          <div class="col-sm-3"></div>
          <div class="col-sm-2">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="/user/homepage">Home</a></li>
              <li class="breadcrumb-item active">500 Error Page</li>
            </ol>
          </div>
        </div>
      </div>
    </section>

    <section class="content">
      <div class="error-page">
        <h2 class="headline text-danger">500</h2>

        <div class="error-content">
          <h3><i class="fa fa-warning text-danger"></i> 服务器走神了</h3>

          <h4>我们将立即着手解决这个问题。<br />与此同时，您可以返回
           <a href="/user/homepage">主页</a> 面或 搜索其它电影。
          </h4>

          <form class="search-form" action="/user/homepage" >
            <div class="input-group">
              <input type="text" name="keys" class="form-control" placeholder="关键字...">
              <button class="btn btn-danger"><i class="fa fa-search"></i></button>
            </div>
          </form>
        </div>
      </div>

    </section>
  </div>
</div>
<%@ include file="../include/js.jsp"%>
<script src="/static/dist/js/demo.js"></script>
</body>
</html>
