Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


//将form转为AJAX提交
function ajaxSubmit(frm, fn) {
    var dataPara = getFormJson(frm);
    $.ajax({
        url: frm.action,
        type: frm.method,
        data: dataPara,
        success: fn
    });
}

//将form中的值转换为键值对。
function getFormJson(frm) {
    var o = {};
    var a = $(frm).serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });

    return o;
}

function checkedStr(str) {
    str = str == 'undefined' ? "" : str;
    str = str == null ? "" : str;
    return str;
    //return str || ''
}

//判断是否是json
var isJson = function (obj) {
    var isjson = typeof (obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length
    return isjson;
}
//判断是否是json数组
var isJsonArr = function (obj) {
    var isJsonArr = Object.prototype.toString.call(obj).toLowerCase() == "[object array]"
    return isJsonArr;
}

function isEmptyObject(e) {
    var t;
    for (t in e)
        return !1;
    return !0
}

//刷新当前页面
function refresh() {
    window.location.reload();
}

// 关闭当前弹窗
function closeCurrentIndex() {
    var index = parent.layer.getFrameIndex(window.name); //获取当前窗体索引
    parent.layer.close(index); //执行关闭
}

// 获取用户的权限
var getAuthoritiesByLocal = function () {
    if (window.localStorage) {
        return localStorage.getItem("authorities");
    } else {
        return $.cookie('authorities');
    }
}
