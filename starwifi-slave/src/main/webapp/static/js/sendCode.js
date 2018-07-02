/**
 * 发送验证码60s倒计时
 * @type {null}
 */


//倒计时60s
var InterValObj = null; //timer变量，控制时间
var count = 60; //间隔函数，1秒执行
var curCount;//当前剩余秒数


function sendMessage(ids) {
    curCount = count;
    // 设置button效果，开始计时
    ids.attr("disabled", "true");
    ids.css("background", "gray");
    ids.text(curCount + "秒");

    if (InterValObj){
        clearInterval(InterValObj);
        InterValObj=null;
    }

    InterValObj = setInterval(function(){
        SetiponeTime(ids);
    },1000); // 启动计时器，1秒执行一次

}

//timer处理函数
function SetiponeTime(ids) {

    if (curCount == 0) {
        clearInterval(InterValObj);// 停止计时器
        InterValObj = null;
        ids.css("background", "#cc0b0b");
        ids.text("重新发送");
        ids.removeAttr("disabled");// 启用按钮
        phonei = 0;
    }
    else {
        curCount--;
        ids.text(curCount + "秒");
    }

}