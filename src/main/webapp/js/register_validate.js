/*
				表单校验：
					1.用户名：单词字符，长度8到20位
					2.密码：单词字符，长度8到20位
					3.email：邮件格式
					4.姓名：非空
					5.手机号：手机号格式
					6.出生日期：非空
					7.验证码：非空
			 */
//数据校验, 异步提交表单
$(function () {
    //失去焦点时,进行数据校验
    $("#username").blur(checkUsername);
    $("#password").blur(checkPassword);
    $("#email").blur(checkEmail);
    //表单提交,进行数据校验
    $("#registerForm").submit(function () {
        if (checkUsername() && checkPassword() && checkEmail()) {
            $.post(
                "user/regist",
                $("#registerForm").serialize(),
                function (data) {
                    var flag = data.flag;
                    if (flag) {
                        location.href = "register_ok.html";
                    } else {
                        var msg = data.msg;
                        $("#msg").text(msg);
                    }
                }, "json");
        }
        return false;
    });
});

function checkUsername() {
    //校验用户名
    //正则校验
    var reg_name = /^\w{8,20}$/;
    var value = $("#username").val();
    var flag = reg_name.test(value);
    if (flag) {
        //正则验证通过
        $("#username").css("border", "");
        $.post(
            "user/checkUserExist",
            {"username": value},
            function (data) {
                var msg = data.msg;
                if (!msg) {
                    $("#msg").text("用户已存在");
                } else {
                    $("#msg").text("");
                }
            });
    } else {
        $("#username").css("border", "1px solid red");
    }
    return flag;

}

function checkPassword() {
    //校验用户名
    //正则校验
    var reg_name = /^\w{8,20}$/;
    var value = $("#password").val();
    var flag = reg_name.test(value);
    if (flag) {
        //正则验证通过
        $("#password").css("border", "");
    } else {
        $("#password").css("border", "1px solid red");
    }
    return flag;
}

function checkEmail() {
    //校验用户名
    //正则校验
    var reg_name = /^\w+@\w+(\.\w+)+$/;
    var value = $("#email").val();
    var flag = reg_name.test(value);
    if (flag) {
        //正则验证通过
        $("#email").css("border", "");
    } else {
        $("#email").css("border", "1px solid red");
    }
    return flag;
}