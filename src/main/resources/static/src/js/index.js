$(function () {
    //判断是否需要倒计时
    let startTime = localStorage.getItem('startTime');
    const majorQuestionIdList = JSON.parse(localStorage.getItem('majorQuestionIdList'));
    const questionNum = 50 + majorQuestionIdList.length;
    let totalTime = 60 * 60;
    let useTime = 0;
    // startTime = 111110;
    let index = 1;
    let startTimer;
    let totalTimer;
    let useTimer;
    if (startTime == null) {
        window.location.href = 'login.html';
    } else {
        startTime = parseInt(startTime / 1000);
        $('#useTime').hide();
        if (startTime == 0) {
            start();
        } else if (startTime > 0) {
            $('.container').show();
            $('.topic').hide();
            $('#name').html(localStorage.getItem('userName'));
            //开始倒计时
            $('#time').html(formatTime(countDownTime(startTime)));
            startTimer = setInterval(function () {
                startTime--;
                $('#time').html(formatTime(countDownTime(startTime)));
                if (startTime <= 0) {
                    start();
                    clearInterval(startTimer);
                }
            }, 1000);
        } else {
            //答题已经开始
            totalTime = totalTime + startTime;
            start();
        }
    }


    function start() {
        $('#total').html(questionNum);
        $('.container').hide();
        $('.topic').show();
        let countDown = countDownTime(totalTime);
        $('#hour').html(countDown.hour);
        $('#min').html(countDown.min);
        $('#sec').html(countDown.sec);
        totalTimer = setInterval(function () {
            totalTime--;
            countDown = countDownTime(totalTime);
            $('#hour').html(countDown.hour);
            $('#min').html(countDown.min);
            $('#sec').html(countDown.sec);
            if (totalTime <= 0) {
                clearInterval(useTimer);
                clearInterval(totalTimer);
                end();
            }
        }, 1000);
        //加载题目
        getQuestion();
    }

    function getQuestion(result) {
        let questionId = index;
        if (index > 50) {
            questionId = majorQuestionIdList[index - 51]
        }
        $.ajax({
            url: SERVICE + 'question/getQuestionById',
            type: "get",
            data: {questionId: questionId},
            success: function (res) {
                $('#number').html(index);
                $('#type').html(res.data.type == 1 ? '单选题' : res.data.type == 2 ? '多选题' : '判断题');
                $('#type').attr('data-type', res.data.type);
                $('#subject').html(res.data.subject);
                $('#useTime').hide();
                let html = '';
                let detailList = [];
                if (res.data.type == 3) {
                    detailList.push({logo: 'A', content: '对'}, {logo: 'B', content: '错'})
                } else {
                    detailList = res.data.detailList;
                }
                detailList.forEach(function (item) {
                    html += '<div class="entrance-bottom-frame-line-button">';
                    html += ' <div class="entrance-bottom-frame-line-button-id">' + item.logo + '</div>';
                    html += ' <div class="entrance-bottom-frame-line-button-frame">' + item.content + '</div>';
                    html += '</div>';
                });
                $('#detailList').html(html);
                bindLineButton();
                if (result !== false) {
                    $('.confirm-btn').attr('disabled', false);
                } else {
                    startUseTimer();
                }
            }
        });
    }

    function startUseTimer() {
        useTime = 15;
        $('#useTime').show();
        $('#useTime').html(useTime);
        clearInterval(useTimer);
        useTimer = setInterval(function () {
            useTime--;
            $('#useTime').html(useTime);
            if (useTime <= 0) {
                clearInterval(useTimer);
                $('.confirm-btn').attr('disabled', false);
                $('#useTime').hide();
            }
        }, 1000);
    }

    function formatTime(countDown) {
        return countDown.hour + '时' + countDown.min + '分' + countDown.sec + '秒'
    }

    $('.confirm-btn').click(function () {
        //判断选项
        const selectedDiv = $('.entrance-bottom-frame-line-button.active');
        if (selectedDiv.length == 0) {
            return;
        }
        $('.confirm-btn').prop('disabled', true);
        const answer = [];
        $.each(selectedDiv, function (i, item) {
            answer.push($(item).find('.entrance-bottom-frame-line-button-id').text().trim());
        });
        let isFinish = false;
        let questionId = index;
        if (index > 50) {
            questionId = majorQuestionIdList[index - 51]
        }
        if (index == questionNum) {
            isFinish = true;
        }
        $.ajax({
            url: SERVICE + 'question/answer',
            type: "post",
            data: JSON.stringify({
                userId: localStorage.getItem('userId'),
                questionId: questionId,
                answer: answer.join(""),
                isFinish: isFinish
            }),
            success: function (res) {
                if (res.data) {
                    Toast('答题正确');
                    index++;
                } else {
                    Toast('答题错误');
                }
                if (index > questionNum) {
                    end();
                } else {
                    getQuestion(res.data);
                }
            }
        });
    });

    function bindLineButton() {
        $('.entrance-bottom-frame-line-button').unbind();
        $('.entrance-bottom-frame-line-button').bind('click', function (e) {
            const item = $(e.currentTarget);
            const type = $('#type').attr('data-type')
            if (type == 2) {
                if (item.hasClass('active')) {
                    item.removeClass('active');
                } else {
                    item.addClass('active');
                }
            } else {
                if (item.hasClass('active')) {
                    item.removeClass('active');
                } else {
                    $('.entrance-bottom-frame-line-button').removeClass('active');
                    item.addClass('active');
                }
            }
        });
    }

    function end() {
        $('.topic').html('<div style="color:#FF0000;font-size: 40px;text-align: center;padding: 200px 0">答题结束</div>')
    }

});
