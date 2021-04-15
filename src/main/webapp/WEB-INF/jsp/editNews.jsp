<%@ page import="entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/script/editor.js"></script>

    <title>文章编辑器</title>
    <style>
        .form-group {
            border: 1px solid #F2F3F6;
            display: inline-block;
            width: 80%;
            margin-bottom: 0;
        }
        .form-group label {
            line-height: 34px;
            width: 10%;
            float: left;
            padding-left: 5px;
            margin-bottom: 0;
        }
        .form-group input, .form-group input:focus {
            border: none;
            padding: 0;
            width: 80%;
            float: left;
            height: 6%;
        }
        h4 {
            font-size: 24px;
            color: #a2a2a2;
            font-weight: 300;
        }
        .btn-theme {
            color: #fff;
            background-color: #4ECDC4;
            border-color: #48bcb4;
        }
    </style>
</head>
<body>
<h4 style="margin-left: 100px;margin-top: 10px">${title}修改新闻</h4>

<div style="margin-left: 100px;margin-right: 100px;margin-top:50px;margin-bottom: 200px">
    <%
        User user=(User)session.getAttribute("user");
    %>

    <span>${updateResult.errMes}</span>

    <form action="${pageContext.request.contextPath}/new/update" method="post" onsubmit="return checkEditorAll()">

        <input style="display: none" name="newId" value="${editResult.data.aNew.newId}">
        <input style="display: none" name="userId" value="${editResult.data.user.userId}">
        <div class="form-group">
            <label for="title" class="">标 题:</label>
            <input type="text" name="title" tabindex="1" id="title" onblur="checkEditorTitle()" class="form-control" value="${editResult.data.aNew.title}">
            <span class="help-block" id="tip_title" style="color: #c7161c"></span>
        </div>
        <%--<div class="form-group">
            <label for="category" class="">类 型:</label>
            <input type="text"  name="categoryId" tabindex="1" id="category" class="form-control"
                   value="${editResult.data.aNew.categoryId}">
            <span id="tip_category" style="color: rebeccapurple"></span>
        </div>--%>
        <div class="form-group">
            <label for="dov" class="">作者:</label>
            <input type="text" disabled="disabled" name="author " tabindex="1" id="dov" class="form-control" value="${editResult.data.user.username}">
        </div>
        <div class="form-group">
            <label for="keywords" class="">关键字:</label>
            <input type="text" name="keyWords" tabindex="1" id="keywords" onblur="checkEditorKeywords()" class="form-control" value="${editResult.data.aNew.keyWords}">
            <span class="help-block" id="tip_keywords" style="color: #c7161c"></span>
        </div>
        <div class="form-group">
            <label class="">类 型:</label>
            <label>
                <select id="categoryId" name="categoryId" class="form-select form-select-sm">
                    <option selected>请选择文章类型：</option>
                    <c:forEach var="cList" items="${categoryList}">
                        <option value="${cList.categoryId}">${cList.categoryName}</option>
                    </c:forEach>
                </select>
            </label>
            <span class="help-block" id="tip_category" style="color: #c7161c"></span>
        </div>
        <div id="NewContent" style="display: none">${editResult.data.aNew.content}</div>
        <!-- 加载编辑器的容器 -->
        <script id="container" name="content" type="text/plain">
        </script>

        <button class="btn-theme" id="submit" type="submit"><i class="fa fa-lock"></i>提 交</button>
    </form>
</div>

<!-- 配置文件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/ueditor/ueditor.config.js" ></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/ueditor/ueditor.all.js" ></script>
<!-- 实例化编辑器 -->
<script type="text/javascript">
    var ue = UE.getEditor('container');
    var tsg=document.getElementById('NewContent').innerHTML;
    setTimeout(function () {
        ue.setContent(tsg.toString());
    },500)
</script>
<%-- 下拉框默认选中类型--%>
<script type="text/javascript">
    //控制轮播类型的初始选中值
    //str = document.getElementById("typev").value;
    obj = document.getElementById("categoryId");
    for(let i=0; i<obj.length; i++){
        if(obj[i].value==${editResult.data.aNew.categoryId})
            obj[i].selected = true;
    }
</script>


</body>
</html>
