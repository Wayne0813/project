<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>车管家ERP-首页</title>
    <%@ include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="../include/header.jsp" %>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="looker"/>
    </jsp:include>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                质检部任务领取
            </h1>
        </section>

        <section class="content">

            <div class="box">
                <div class="box-body">

                    <c:forEach items="${fixOrderList}" var="fixOrder">
                        <div class="panel panel-info">
                            <div class="panel-heading"><a href="/looker/detail/${fixOrder.orderId}">订单号：${fixOrder.orderId}</a> - ${fixOrder.carType} - ${fixOrder.orderType}
                                <c:if test="${fixOrder.orderState == '4'}">
                                    <button rel="${fixOrder.orderId}" class="btn btn-success btn-sm pull-right getBtn">任务领取</button>
                                </c:if>
                            </div>
                            <ul class="list-group">
                                <c:forEach var="parts" items="${fixOrder.fixPartsList}">
                                    <li class="list-group-item">${parts.partsNo} - ${parts.partsName} * ${parts.partsNum}</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:forEach>

                </div>
        </section>
    </div>
    <%@ include file="../include/footer.jsp"%>
</div>
<%@ include file="../include/js.jsp"%>
<script>
    $(function(){

        $(".getBtn").click(function(){
            var orderId = $(this).attr("rel");
            layer.confirm("确定要接单吗?",function (index) {
                layer.close(index);

                $.get("/looker/trans/" + orderId).done(function (res) {
                    if(res.state == "success") {
                        window.location.href = "/looker/detail/" + orderId;
                    } else {
                        layer.msg(e.message);
                    }
                }).error(function () {
                    layer.msg("系统异常!");
                })
            });
        });

        var msg = "${message}";
        if(msg) {
            layer.msg(msg);
        };



        $("#pagination").twbsPagination({
            totalPages : 5,
            visiblePages : 7,
            first : '首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"#"
        });

        var locale = {
            "format": 'YYYY-MM-DD',
            "separator": " - ",//
            "applyLabel": "确定",
            "cancelLabel": "取消",
            "fromLabel": "起始时间",
            "toLabel": "结束时间'",
            "customRangeLabel": "自定义",
            "weekLabel": "W",
            "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
            "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            "firstDay": 1
        };

        var startDate = "";
        var endDate = "";

        if(startDate && endDate) {
            $('#time').val(startDate + " / " + endDate);
        }


        $('#time').daterangepicker({
            autoUpdateInput:false,
            "locale": locale,
            "opens":"right",
            "timePicker":false
        },function(start,end) {
            $("#startTime").val(start.format('YYYY-MM-DD'));
            $("#endTime").val(end.format('YYYY-MM-DD'));

            $('#time').val(start.format('YYYY-MM-DD') + " / " + end.format('YYYY-MM-DD'));
        });
    })
</script>
</body>
</html>
