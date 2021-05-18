(function () {
    $.ajaxSetup({
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        cache: false
    });
})();

function Toast(text) {
    $.toast({allowToastClose: false, loader: false, position: 'top-center', text: text});
}

function isNull(val) {
    return val == undefined || val == null || val == '' || val.toString().trim() == '';
}

function countDownTime(ms) {
    let hour, min;
    let sec = parseInt(ms);
    if (sec >= 60) {
        min = parseInt(sec / 60);
        sec = parseInt(sec % 60);
    } else {
        min = parseInt(sec / 60);
    }
    if (min >= 60) {
        hour = parseInt(min / 60);
        min = parseInt(min % 60);
    } else {
        hour = parseInt(sec / 3600);
    }
    if (hour < 10) {
        hour = "0" + hour;
    }
    if (min < 10) {
        min = "0" + min;
    }
    if (sec < 10) {
        sec = "0" + sec;
    }
    return {hour: hour, min: min, sec: sec};
}

// const SERVICE = 'http://199.28.0.118:8099/';
const SERVICE = '';

XBack = {};
(function (XBack) {
    XBack.STATE = 'x - back';
    XBack.element;

    XBack.onPopState = function (event) {
        event.state === XBack.STATE && XBack.fire();
        XBack.record(XBack.STATE); //初始化事件时，push一下
    };

    XBack.record = function (state) {
        history.pushState(state, null, location.href);
    };

    XBack.fire = function () {
        var event = document.createEvent('Events');
        event.initEvent(XBack.STATE, false, false);
        XBack.element.dispatchEvent(event);
    };

    XBack.listen = function (listener) {
        XBack.element.addEventListener(XBack.STATE, listener, false);
    };

    XBack.init = function () {
        XBack.element = document.createElement('span');
        window.addEventListener('popstate', XBack.onPopState);
        XBack.record(XBack.STATE);
    };

})(XBack); // 引入这段js文件

XBack.init();
XBack.listen(function () {
});
