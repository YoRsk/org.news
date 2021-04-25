<%@ page import="entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp" %>
<html>
<head>
    <title>后台主页-用户列表</title>
    <%@include file="common/header.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/to-do.css">
</head>
<body>
<section id="container">
    <!--header start-->
    <header class="header black-bg">
        <div class="sidebar-toggle-box">
            <div class="fa fa-bars tooltips" data-placement="right" data-original-title="Toggle Navigation"></div>
        </div>
        <!--logo start-->
        <a href="${pageContext.request.contextPath}/index.html" class="logo"><b><span>新闻页</span></b></a>
        <!--logo end-->
        <div class="top-menu">
            <ul class="pull-right top-menu">
                <br>
                <%
                    User user=(User)session.getAttribute("user");
                    if(user!=null){
                %>
                <li><a class="logout" href="${pageContext.request.contextPath}/user/Logout?username=<%=user.getUsername()%>"
                >Logout</a></li>
                <%
                }else{
                %>
                <li><a class="logout" href="${pageContext.request.contextPath}/user/adminLogin">ToLogin</a></li>
                <%
                    }
                %>

            </ul>
        </div>
    </header>
    <!--header end-->
    <!--sidebar start-->

    <aside>
        <div id="sidebar" class="nav-collapse " tabindex="5000" style="overflow: hidden; outline: none;">
            <!-- sidebar menu start-->
            <ul class="sidebar-menu" id="nav-accordion">
                <%
                    if(user!=null) {/*说明已登录*/
                %>
                <p class="centered"><a href="${pageContext.request.contextPath}/user/center?userId=<%=user.getUserId()%>">
                    <img src="${pageContext.request.contextPath}/resource/img/ui-sam.jpg" class="img-circle" width="80">
                    <%
                    }else{
                    %>
                    <img src="" class="img-circle" width="80">
                    <%
                        }
                    %>

                </a>
                </p>
                <%
                    if(user!=null){/*说明已登录*/
                %>
                <h5 class="centered">管理员:<%=user.getUsername()%></h5>
                <%
                }else{
                %>
                <h5 class="centered">请登录！！</h5>
                <%
                    }
                %>
                <li class="mt">
                    <a href="${pageContext.request.contextPath}/new/adminIndex">
                        <i class="fa fa-dashboard"></i>
                        <span>首 页</span>
                    </a>
                </li>
                <li class="sub-menu dcjq-parent-li">
                    <a href="${pageContext.request.contextPath}/new/adminIndex" class="dcjq-parent">
                        <i class="fa fa-desktop"></i>
                        <span>文章列表</span>
                        <span class="dcjq-icon"></span></a>
                </li>
                <li class="sub-menu dcjq-parent-li">
                    <a href="${pageContext.request.contextPath}/new/commentList" class="dcjq-parent">
                        <i class="fa fa-desktop"></i>
                        <span>评论列表</span>
                        <span class="dcjq-icon"></span></a>
                </li>
                <li class="sub-menu dcjq-parent-li">
                    <a href="${pageContext.request.contextPath}/new/userList" class="dcjq-parent">
                        <i class="fa fa-desktop"></i>
                        <span>用户列表</span>
                        <span class="dcjq-icon"></span></a>
                </li>
                <li class="sub-menu dcjq-parent-li">
                    <a href="${pageContext.request.contextPath}/new/categoryList" class="dcjq-parent">
                        <i class="fa fa-desktop"></i>
                        <span>目录列表</span>
                        <span class="dcjq-icon"></span></a>
                </li>
                <li class="sub-menu dcjq-parent-li">
                    <a href="${pageContext.request.contextPath}/new/editor.html?index=2" class="dcjq-parent">
                        <i class="fa fa-desktop"></i>
                        <span>添加文章</span>
                        <span class="dcjq-icon"></span></a>
                </li>
            </ul>
            <!-- sidebar menu end-->
        </div>
    </aside>




    <!--sidebar end-->
    <!--main content start-->
    <section id="main-content">
        <div class="mt" style="margin-right: 5px;margin-left: 5px;margin-top: 5px;margin-bottom: 5px">
            <div class="col-md-12">

                <div class="content-panel">
                    <h4><i class="fa fa-angle-right"></i> 用户列表</h4>
                    <hr>
                    <div class="form-panel">
                        <form class="form-inline"  role="form"
                              action="/new/selectUserByLike"
                              style="display: contents">
                            <div class="form-group" style="display: contents">
                                <label class="sr-only" for="exampleInput1">模糊查询</label>
                                <span style="color: rebeccapurple" id="tip1"></span>
                                <input type="text" class="form-control"  id="exampleInput1" name="selectkey"
                                       placeholder="模糊查询">
                            </div>
                            <button type="submit" class="btn btn-theme">查找</button>
                        </form>

                    </div>
                    <!-- /form-panel -->


                    <table class="table table-striped table-advance table-hover">
                        <thead>
                        <tr>
                            <th><i class="fa fa-bullhorn"></i> 编号</th>
                            <th><i class="fa fa-tags"></i> 用户昵称</th>
                            <th><i class="fa fa-user"></i> 用户类型</th>
                            <th><i class="fa fa-tasks"></i> 用户邮箱</th>
                            <th><i class="fa fa-tasks"></i> 用户年龄</th>
                            <th class="hidden-phone"><i class="fa fa-bars"></i> 创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="user" items="${userList}">
                            <tr>
                                <td>
                                        ${user.userId}
                                </td>
                                <td class="hidden-phone">${user.username}</td>
                                <td>${user.userType}</td>
                                <td>${user.userEmail}</td>
                                <td>${user.userAge}</td>
                                <td><fmt:formatDate value="${user.createTime}"
                                                    pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.isOnline==1}">
                                            <a href="${pageContext.request.contextPath}/admin/ForceLogout?userId=${user.userId}">
                                                <button class="toast-btn btn btn-success" id="goToPageB">
                                                    <i class="fa fa-check">
                                                        下线</i></button>
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="">
                                                <button class="btn btn-facebook" id="">
                                                    <i class="fa fa-check">
                                                        未上线</i></button>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>

                               <%--     <a href="${pageContext.request.contextPath}/user/profile?userId=${user.userId}">
                                        <button class="btn btn-primary"><i class="fa fa-pencil">修改
                                        </i></button>
                                    </a>--%>

                                    <a href="">
                                        <a href="${pageContext.request.contextPath}/admin/DeleteUser?userId=${user.userId}">
                                            <button class="btn btn-danger">
                                                <i class="fa fa-trash-o ">
                                                    删除</i></button>
                                        </a>

                                    </a>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>

                    </div>
                </div>
                <!-- /content-panel -->
            </div>
            <!-- /col-md-12 -->
    </section>
    <!-- /MAIN CONTENT -->
    <!--main content end-->
    <!--footer start-->






    <footer class="site-footer">
        <div class="text-center">
            <p>
                © Copyrights <strong>彭刘羿</strong>. All Rights Reserved
            </p>
            <div class="credits">
                Created by <a href="https://github.com/YoRsk">MyGithub</a>
            </div>
            <a href="adminIndex#" class="go-top">
                <i class="fa fa-angle-up"></i>
            </a>
        </div>
    </footer>
    <!--footer end-->
</section>

<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<%--toasts框所需组件--%>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://cdn.bootcss.com/twitter-bootstrap/4.2.1/js/bootstrap.min.js"></script>
<script src="https://cdn.bootcss.com/jquery-backstretch/2.0.4/jquery.backstretch.min.js"></script>

<script class="include" type="text/javascript"
        src="${pageContext.request.contextPath}/resource/lib/jquery.dcjqaccordion.2.7.js"></script>
<script src="https://cdn.bootcss.com/jquery-scrollTo/2.1.2/jquery.scrollTo.min.js"></script>
<script src="https://cdn.bootcss.com/jquery.nicescroll/3.7.6/jquery.nicescroll.js"></script>
<script src="${pageContext.request.contextPath}/resource/lib/common-scripts.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="../../resource/lib/toast.min.js"></script>
<%--
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/css/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/css/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
--%>
<script src="${pageContext.request.contextPath}/resource/script/tasks.js" type="text/javascript"></script>
<script>
    jQuery(document).ready(function () {
        TaskList.initTaskWidget();
    });

    function checktext1() {
        text = document.getElementById("exampleInput1");
        tip = document.getElementById("tip1");
        if(text.value.toString()==""){
            tip.innerHTML="输入框不能为空";
            return false;
        }else{
            tip.innerHTML="";
            return true;
        }
    }
    function checktext2() {
        text = document.getElementById("exampleInput2");
        tip = document.getElementById("tip2");
        if(text.value.toString()==""){
            tip.innerHTML="输入框不能为空";
            return false;
        }else{
            tip.innerHTML="";
            return true;
        }
    }

    function detail(id) {
        location.href = "/new/delete?newId=" + id;
    }

    $('.toast-btn').on('click',function() {
        sessionStorage.setItem("from","pageA");
    })

    $.toastDefaults.position = 'bottom-center';
    $.toastDefaults.dismissible = true;
    $.toastDefaults.stackable = true;
    $.toastDefaults.pauseDelayOnHover = true;
    window.onload = function() {
        var from = sessionStorage.getItem("from");
        if( from == 'pageA') {
            var type = 'info',
                title = '下线提醒',
                content = '${result.errMes}';
                $.toast({
                    type: type,
                    title: title,
                    subtitle: 'now',
                    content: content,
                    delay: 5000
                });
            }
            //balabala  要触发的点击事件  $('#xxx').click()
            sessionStorage.setItem("from",""); //销毁 from 防止在b页面刷新 依然触发$('#xxx').click()
    }

    /*    const TYPES = ['info', 'warning', 'success', 'error'],
            TITLES = {
                'info': 'Notice!',
                'success': 'Awesome!',
                'warning': 'Watch Out!',
                'error': 'Doh!'
            },
            CONTENT = {
                'info': 'Hello, world! This is a toast message.',
                'success': 'The action has been completed.',
                'warning': 'It\'s all about to go wrong',
                'error': 'It all went wrong.'
            },
            POSITION = ['top-right', 'top-left', 'top-center', 'bottom-right', 'bottom-left', 'bottom-center'];*/

    /*   var desc = document.getElementById('desc').innerHTML;
   var text = desc.toString().substring(0, 20) + "......";
   var desc1 = document.getElementById('desc');

   desc1.innerHTML = text;*/
    $(function () {
        $("#sortable").sortable();
        $("#sortable").disableSelection();
    });
</script>

</body>
</html>
