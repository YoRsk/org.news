<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<html>
<head>
    <title>Title</title>
    <%@include file="common/header.jsp" %>
    <style>

        .comment-short{
            margin-top: 50px;
        }

        .LEFT {

            width: 890px;
            float: left;
            margin-bottom: 250px;

        }

        .LEFT h1 {

            font-size: 40px;
            line-height: 1.5;
            margin-bottom: 25px;
            color: #333;
            margin-top: 8px;
        }

        p {
            font-size: 40px;
            line-height: 1.5;
            margin-bottom: 25px;
            color: #333;
            margin-top: 8px;
        }

        .header1 {
            height: 28px;
            padding-bottom: 22px;
            overflow: hidden;
        }

        .header-title {

            font-size: 26px;
            color: #2a2a2a;
            float: left;
            height: 100%;
            margin-right: 10px;

        }

        .header-protocol {

            float: left;
            height: 100%;
            color: #ccc;
            font-size: 14px;
            line-height: 34px;

        }

        .header-protocol a, .header-protocol a:visited {

            color: #ccc;

        }

        .header-number, .header-number:visited {

            color: #379be9;

        }

        .header-number {

            float: right;
            height: 100%;
            color: #adadad;
            line-height: 34px;
            font-size: 14px;

        }

        .header-number, .header-number:visited {

            color: #379be9;

        }

        .header-number {

            color: #adadad;
            line-height: 34px;
            font-size: 14px;

        }

        .box {

            position: relative;
            padding-left: 68px;
            padding-bottom: 30px;
        }
        .box-content box-login{
            margin-right: 12px;
            height: 104px;
        }

        .center-click .my-avatar, .user-click .comment-short .common-avatar, .user-click .comment-username, .user-click .reply-user-nick {

            cursor: pointer;

        }

        .common-avatar {

            display: block;
            position: absolute;
            left: 0;
            z-index: 1;
            width: 50px;
            height: 50px;
            font-size: 0;
            border-radius: 50%;
            background: url(//mat1.gtimg.com/v/comment/images/avatar_default.9d95c455.jpg);
            overflow: hidden;

        }

        a, b, body, button, dd, div, dl, dt, em, form, h1, h2, h3, h4, header, input, li, p, span, textarea, ul {

            margin: 0;
            padding: 0;
            border: 0;
            font-size: 100%;
            vertical-align: baseline;

        }

        .common-avatar img {

            width: 100%;
            height: 100%;

        }

        img {

            border: 0;

        }

        .box-content {

            border: 3px solid #f0f0f0;
            position: relative;

        }

        a, b, body, button, dd, div, dl, dt, em, form, h1, h2, h3, h4, header, input, li, p, span, textarea, ul {

            margin: 0;
            padding: 0;
            border: 0;
            font-size: 100%;
            vertical-align: baseline;

        }

        .box-login .box-textarea-block {

            margin-right: 12px;
            height: 104px;

        }

        .box-textarea-block {

            margin-left: 12px;
            margin-top: 12px;
            margin-bottom: 12px;

        }

        .box-textarea {

            height: 100%;
            width: 70%;
            display: block;
            border: none;
            font-size: 14px;
            line-height: 24px;
            color: #4b4b4b;

        }

        input, textarea {

            outline: none;

        }

        textarea {

            resize: none;
            overflow: auto;

        }

        .box-images {

            display: none;
            padding: 16px 12px;
            overflow: hidden;

        }

        li, ul {

            list-style: none;

        }

        .box-upload-form {

            position: absolute;
            width: 100%;
            height: 100%;
            z-index: 1;
            left: 0;
            top: 0;
            overflow: hidden;
            opacity: 0;
            filter: alpha(opacity=0);

        }

        .box-upload-file {

            display: block;
            width: 100%;
            height: 100%;
            cursor: pointer;

        }


        .comment-title {

            padding-bottom: 14px;
            border-bottom: 1px solid #f0f0f0;
            margin-top: 48px;
            overflow: hidden;

        }

        .comment-all {

            font-size: 20px;
            color: #2a2a2a;
            float: left;

        }

        .comment-center {

            float: left;
            overflow: hidden;
            position: relative;
            top: 4px;

        }

        .comment-center-slash {

            display: inline-block;
            margin: 0 6px;
            color: #999;
            font-size: 14px;

        }

        .comment-my {

            font-size: 14px;
            color: #379be9;
            cursor: pointer;

        }

        .comment-sort, .comment-sort span {

            cursor: pointer;

        }

        .comment-sort {

            float: right;
            font-size: 14px;
            color: #999;
            position: relative;

        }

        .comment-sort, .comment-sort span {

            cursor: pointer;

        }

        .comment-sort .comment-sort-cur {

            color: #2a2a2a;
            cursor: auto;

        }

        .comment-sort, .comment-sort span {

            cursor: pointer;

        }

        .comment {

            position: relative;
            margin-top: 32px;
            padding: 0 0 32px 68px;
            border-bottom: 1px solid #f0f0f0;

        }




        .comment-user {

            height: 32px;
            line-height: 32px;
            font-size: 12px;

        }

        .comment-username {

            font-weight: 600;
            color: #379be9;

        }

        .comment-time {

            color: #999;
            display: inline-block;
            margin-left: 12px;

        }

        .comment-short .comment-content {

            margin-top: 2px;

        }

        .comment-content {

            font-size: 14px;
            color: #4b4b4b;
            line-height: 24px;
            margin-top: 10px;
            word-wrap: break-word;

        }

        .comment_box {
            margin-top: 100px;
            margin-bottom: 50px;
            margin-left: 85px;
        }

    </style>
</head>
<body style="color: #797979;background: #eaeaea;font-family: 'Ruda', sans-serif;padding: 0px !important;margin: 0px !important;
    font-size: 13px;">
<div>
    <!--main content start-->
    <section id="main-content">
        <section class="wrapper site-min-height">
            <div class="LEFT">
                <h1>${detaildata.aNew.title}</h1>
                <h3><i class="fa fa-angle-right"></i>${detaildata.user.username}</h3>
                <div class="row mt">
                    <div class="col-lg-12">
                        <p>${detaildata.aNew.content}</p>
                    </div>
                </div>
            </div>
            <div class="comment_box">
                <div class="new_comment">
                    <div class="header1" id="J_Header"><h1 class="header-title">网友评论</h1>
                        <p class="header-protocol">文明上网理性发言，请遵守新闻评论服务协议</p>
                        </div>
                    <div class="box" id="J_Post">

                        <div class="common-avatar my-avatar J_userCenter"><img
                                src="${pageContext.request.contextPath}/resource/img/friends/avatar.png"
                                width="100%" height="100%"></div>
                        <div>${insertComment.errMes}</div>
                        <div class="box-content box-login">
                            <form action="${pageContext.request.contextPath}/new/submitcomment" method="post">
                                <input name="newId" value="${detaildata.aNew.newId}" style="display: none">

                                <div class="box-textarea-block">
                                <textarea class="box-textarea J_Textarea" name="commentContent" id="J_Textarea"
                                          placeholder="说两句吧..."></textarea></div>
                                <button class="btn btn-danger btn-xs" type="submit" onclick="return checkInput()">提交评论</button>
                                <span class="help-block" id="tip_inputText"></span>
                            </form>
                            </li>
                            </ul>
                        </div>
                    </div>
                    <div class="comment-short" id="J_Short">
                        <div class="comment-title"><p class="comment-all">全部评论</p>
                            <div class="comment-center" id="J_CommentCenter"><span class="comment-center-slash">/</span><span
                                    class="comment-my J_userCenter">我的评论</span></div>
                            <p class="comment-sort"><span data-targetid="3604043448" data-sort="1"
                                                          class="J_CommentSort ">最新</span> &nbsp;/&nbsp; <span
                                    data-targetid="3604043448" data-sort="0"
                                    class="J_CommentSort comment-sort-cur">最热</span></p></div>
                        <div id="J_ShortComment">
                            <div class="comment" id="J_Comment6484404667763062250">
                                <c:forEach var="comment" items="${commentlist}">
                                    <div class="comment-block" id="J_CommentBlock6484404667763062250">
                                        <div class="common-avatar J_User" data-userid="692770537">
                                            <img src="${pageContext.request.contextPath}/resource/img/friends/avatar.png"
                                                 width="100%" height="100%">
                                        </div>
                                        <p
                                            class="comment-user"><span class="comment-username J_User"
                                                                       data-userid="692770537"> ${comment.username}</span>
                                        <span class="comment-time"><fmt:formatDate value="${comment.comment.createTime}" type="both"/>
                                        </span></p>
                                        <div class="comment-content J_CommentContent">
                                                ${comment.comment.content}
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </section>
        <!-- /wrapper -->
    </section>
</div>
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-backstretch/2.0.4/jquery.backstretch.min.js"></script>
<script>
    function checkInput() {
        //原生js的获取节点的方式
        var text = document.getElementById("J_Textarea").value;
        var tip = document.getElementById("tip_inputText");
        //不能为空
        if (text.length==0) {
            tip.innerHTML = "输入内容不能为空";
            return false;
        } else {
            tip.innerHTML = " ";
            return true;
        }
    }
    // function reply(obj) {
    //     /*var commentId = $(obj).data('commentid');
    //     var commentNickname = $(obj).data('commentnickname');
    //     $("[name='content']").attr("placeholder", "@"+commentNickname).focus();
    //     $("[name='parentComment.id']").val(commentId);*/
    //     $(window).scrollTo($('#J_Short'),500);//没用
    // }
    // $('.')
</script>
</body>
</html>
