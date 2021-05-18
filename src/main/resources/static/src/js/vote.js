$(function () {
    if (localStorage.getItem('userId') == null) {
        window.location.href = 'login.html';
    }
    $.ajax({
        url: SERVICE + 'vote/getGroupList',
        type: "get",
        data: {
            userId: localStorage.getItem('userId')
        },
        success: function (res) {
            let html = '';
            for (let i = 0; i < res.data.length; i++) {
                html += '<tr><td><div class="checkbox"><input type="checkbox" data-id="' + res.data[i].id + '"/><label></label></div></td><td>' + res.data[i].name + '</td></tr>'
            }
            $('tbody').html(html);
            $('tbody tr').click(function (e) {
                $('tbody tr input').prop('checked', false);
                const obj = $(e.currentTarget);
                obj.find('input').prop('checked', true);
            });
        }
    });
    $('.confirm-btn').click(function () {
        const checkedInput = $('tbody tr input:checked');
        if (checkedInput.length == 0) {
            Toast('请选择一个组织部门');
            return;
        }
        $.ajax({
            url: SERVICE + 'vote/add',
            type: "post",
            data: JSON.stringify({userId: localStorage.getItem('userId'), groupId: checkedInput.attr('data-id')}),
            success: function (res) {
                Toast(res.message);
                if (res.resultCode == 0) {
                    $('.confirm-btn').prop('disabled', true);
                    $('tbody tr').unbind();
                    $('input').prop('disabled', true);
                }
            }
        });
    })
});
