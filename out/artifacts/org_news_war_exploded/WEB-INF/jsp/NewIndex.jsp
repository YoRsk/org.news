<%@ page import="entity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>主页</title>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <!-- Custom Theme files -->
    <link href="https://cdn.bootcss.com/twitter-bootstrap/4.2.1/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resource/css/style2.css" rel="stylesheet" type="text/css"
          media="all"/>
    <!-- Custom Theme files -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="application/x-javascript"> addEventListener("load", function () {
        setTimeout(hideURLbar, 0);
    }, false);

    function hideURLbar() {
        window.scrollTo(0, 1);
    } </script>
    <!--webfont-->
    <link href='http://fonts.useso.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic'
          rel='stylesheet' type='text/css'>
    <style>
        ul.top-menu > li > .logout {
            color: #f2f2f2;
            font-size: 12px;
            border-radius: 4px;
            -webkit-border-radius: 4px;
            border: 1px solid #64c3c2 !important;
            padding: 5px 15px;
            margin-right: 15px;
            background: #4ECDC4;
            margin-top: 15px;
        }
    </style>
</head>
<body>
<!-- header-section-starts -->
<div class="header">
    <div class="container">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/index"><img src="${pageContext.request.contextPath}/resource/img/friends/fr-09.jpg"
                                      class="img-responsive" alt="" style="width: 100px; height: 100px"/></a>

            <label style="margin-left: 200px;font-size: 40px;margin: 0;color: #ffffff;">新闻发布平台</label>
        </div>

        <div class="header-right">
            <%
                User user = (User) session.getAttribute("user");
                if (user != null) {
            %>
            <h4>欢迎用户<%=user.getUsername()%>
                <ul class="pull-right top-menu">
                    <li><a class="logout" href="${pageContext.request.contextPath}/user/Logout?username=<%=user.getUsername()%>">Logout</a>
                    </li>
                </ul>
            </h4>
            <%
            } else {
            %>
            <h4>
                <div>未登录</div>
                <ul class="pull-right top-menu">
                    <li><a class="logout" href="${pageContext.request.contextPath}/user/login">ToLogin</a></li>
                </ul>
            </h4>
            <%
                }
            %>

            <span class="menu"></span>
            <div class="top-menu">
                <ul>
                    <li><a class="active" href="#">新闻主页</a></li>
                        <%if(user != null){%>
                    <li><a href="${pageContext.request.contextPath}/user/center?userId=<%=user.getUserId()%>">个人中心</a></li>
                        <%if(user.getUserType() == 1){%>
                    <li><a href="${pageContext.request.contextPath}/new/adminIndex">管理界面</a></li>
                        <%}%>
                        <%}%>
            </div>
            <!-- script for menu -->
            <script>
                $("span.menu").click(function () {
                    $(".top-menu").slideToggle("slow", function () {
                        // Animation complete.
                    });
                });
            </script>
            <!-- script for menu -->
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<c:if test="${!result.isSuccess}">
    <span style="color: rebeccapurple">${result.errMes}</span>
</c:if>
<!-- header-section-ends -->
<div class="content">
    <div class="blog-section">
        <div class="container">
            <div class="col-md-8 left_column" style="float: left">
                <c:choose>
                    <c:when test="${empty title}">
                        <h2 class="with-line">新闻列表</h2>
                    </c:when>
                    <c:otherwise>
                        <h2 class="with-line">${title}</h2>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="data" items="${newData}">
                    <c:if test="${data.aNew.states==2}">
                    <article class="clearfix">
                        <header class="grid_8 alpha">
                            <div class="sub">
                                <a target="_blank" href="/new/detail?newId=${data.aNew.newId}"
                                   class="readblog">${data.aNew.title}</a>
                                <p class="sub_head">作者: <a href="#">${data.username}</a> 浏览量: ${data.aNew.views}
                                </p>
                            </div>
                            <div class="clearfix"></div>
                        </header>
                        <c:if test="${data.aNew.newId%3==0}">
                            <div class="alpha1"><a href="/new/detail?newId=${data.aNew.newId}"><img
                                    src="${pageContext.request.contextPath}/resource/img/index-3_img-1.jpg" alt=""/></a>
                            </div>
                        </c:if>
                        <c:if test="${data.aNew.newId%3==1}">
                            <div class="alpha1"><a href="single.html"><img
                                    src="${pageContext.request.contextPath}/resource/img/index-3_img-2.jpg" alt=""/></a>
                            </div>
                        </c:if>
                        <c:if test="${data.aNew.newId%3==2}">
                            <div class="alpha1"><a href="single.html"><img
                                    src="${pageContext.request.contextPath}/resource/img/index-3_img-3.jpg" alt=""/></a>
                            </div>
                        </c:if>

                        <div class="grid_b4">
                            <h5>${data.aNew.title}</h5>
                            <p>${data.typeName}|${data.aNew.keyWords}</p>
                            <a href="/new/detail?newId=${data.aNew.newId}" class="btn btn-1 btn-1c">Read More</a>
                        </div>
                        <div class="clearfix"></div>
                    </article>
                    </c:if>
                </c:forEach>

            </div>

            <div class="col-md-4 right_column" style="float: left;">

                <div class="item2">
                    <header>
                        <h2 class="with-line">与我相关</h2>
                    </header>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/new/editor?index=1">添加文章</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/center">我发表的文章</a></li>
                        <%--<li><a href="${pageContext.request.contextPath}/user/center">我的评论</a></li>
                        <li><a href="${pageContext.request.contextPath}/user/center">我的消息</a></li>--%>
                    </ul>
                </div>

                <div class="item">
                    <header>
                        <h2 class="with-line">新闻分类</h2>
                    </header>
                    <ul>
                        <li><a href="/hot">热点</a></li>
                        <c:forEach var="cList" items="${categoryList}">
                            <li><a href="/category?categoryId=${cList.categoryId}">${cList.categoryName}</a></li>
                        </c:forEach>
                    </ul>
                </div>

            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>


<div class="footer text-center">
    <div class="copyright">
        <p> © Copyrights <strong>彭刘羿</strong>. All Rights Reserved
    </div>
</div>
</body>
</html>