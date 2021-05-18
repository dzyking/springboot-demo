$(function () {
    $('.confirm-btn').click(function () {
        const account = $('#account').val();
        const password = $('#password').val();
        if (isNull(account)) {
            Toast('用户名不能为空');
            return;
        }
        if (isNull(password)) {
            Toast('密码不能为空');
            return;
        }
        $.ajax({
            url: SERVICE + 'user/login',
            type: "post",
            data: JSON.stringify({account: account, password: password}),
            success: function (res) {
                if (res.resultCode == 0) {
                    window.location.href = 'index.html';
                    localStorage.setItem('userId', res.data.userId);
                    localStorage.setItem('userName', res.data.userName);
                    localStorage.setItem('startTime', res.data.startTime);
                    localStorage.setItem('majorQuestionIdList', JSON.stringify(res.data.majorQuestionIdList ? res.data.majorQuestionIdList : []));
                } else {
                    Toast(res.message);
                }
            }
        });
    })
});
