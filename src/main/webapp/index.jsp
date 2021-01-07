<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>用户登录</title>
    <script src="${pageContext.request.contextPath}/js/plugin/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/common/all.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/ui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
</head>
<body>
<c:if test="${requestScope['message']!=null}">
    <script>
        new LightTip().error('${requestScope['message']}')
    </script>
</c:if>
<div style="width: 100vh;height: 100vh;">
    <form action="${pageContext.request.contextPath}/api/login.do"
          method="post"
          class="absolute-center">
        <div class="center mb-15">
            <input type="radio" id="student" name="type" value="student" checked>
            <label for="student" class="ui-radio"></label>
            <label for="student">学生</label>

            <input type="radio" id="teacher" name="type" value="teacher">
            <label for="teacher" class="ui-radio"></label>
            <label for="teacher">教师</label>

            <input type="radio" id="admin" name="type" value="admin">
            <label for="admin" class="ui-radio"></label>
            <label for="admin">管理员</label>
        </div>
        <table>
            <tr class="center mb-15">
                <td><label for="username">用户名</label></td>
                <td><input class="ui-input" id="username" name="username" type="text"></td>
            </tr>
            <tr class="mb-15">
                <td><label for="password">密码</label></td>
                <td><input class="ui-input" id="password" name="password" type="password"></td>
            </tr>
        </table>

        <div class="center mb-15">
        </div>

        <button class="ui-button" id="login-button" data-type="primary" type="submit">登录
        </button>
    </form>
    <script>
        $("#login-button").click(function () {
            $("#login-button").addClass("loading")
        })
    </script>
</div>
</body>
</html>
