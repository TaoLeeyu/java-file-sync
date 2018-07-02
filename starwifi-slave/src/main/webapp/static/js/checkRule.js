/*验证规则代码*/
var regExp = {
    default: {},
    phone: function (ele) {
        var elemVal = $(ele).val();
        if ($.trim(elemVal).length != 0) {
            if (elemVal.match(/^13[0-9]{9}$|14[0-9]{9}$|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$/)) {
                validateRight('', ele);
                return regExp.default.phone = true;
            } else {
                validateError("手机格式错误", ele);
            }
        } else {
            validateError("手机号不能为空", ele);
        }
    },
    name: function (ele) {
        var eleLen=getByteLen($(ele).val());
        if (eleLen == 0){
            //不能为空
            validateError("用户名不能为空!", ele);
            return regExp.default.name = false;
         }else if (0<eleLen && eleLen<4){
            //不能小于4个字符
            validateError("用户名不能小于4个字符！", ele);
            return regExp.default.name = false;
        } else if (eleLen>16){
            validateError("用户名不能16个字符！", ele);
            return regExp.default.name = false;
            //不能超过16个字符
        }else {
            //合法
            validateRight("", ele);
            return regExp.default.name = true;
        }
    },

    //name: function (ele) {
    //    var elemVal = $(ele).val();
    //    var reg = /.*\u4e2d\u56fd.*/g;
    //    if ($.trim(elemVal).length != 0) {
    //        if ($.trim(elemVal).length < 4 || 15 <$.trim(elemVal).length ) {
    //            validateError("用户名4-15个字符，一个汉字为2个字符！", ele);
    //            return regExp.default.name = false;
    //        }else {
    //            validateRight("", ele);
    //            return regExp.default.name = true;
    //        }
    //    } else {
    //        validateError("用户名不能为空!", ele);
    //        return regExp.default.name = false;
    //    }
    //},




   //昵称
    nickname: function (ele) {
        var elemVal = $(ele).val();
        if ($.trim(elemVal).length != 0) {
            if ($.trim(elemVal).length < 4 || 15 <$.trim(elemVal).length ) {
                validateError("昵称4-15个字符，一个汉字为2个字符！", ele);
                return regExp.default.nickname = false;
            }else {
                validateRight("", ele);
                return regExp.default.nickname = true;
            }
        } else {
            validateError("昵称不能为空!", ele);
            return regExp.default.nickname = false;
        }
    },

    //生日
    CreatedTime: function (ele) {
        var elemVal = $(ele).val();
        if ($.trim(elemVal).length != 0) {
            if ($.trim(elemVal).length < 4 || 15 <$.trim(elemVal).length ) {
                validateError("生日格式不正确！", ele);
                return regExp.default.CreatedTime = false;
            }else {
                validateRight("", ele);
                return regExp.default.CreatedTime = true;
            }
        } else {
            validateError("生日不能为空!", ele);
            return regExp.default.CreatedTime = false;
        }
    },


    //出生地
    residence: function (ele) {
        var elemVal = $(ele).val();
        if ($.trim(elemVal).length != 0) {
            if ($.trim(elemVal).length < 0 || 45 <$.trim(elemVal).length ) {
                validateError("小于45位！", ele);
                return regExp.default.residence = false;
            }else {
                validateRight("", ele);
                return regExp.default.residence = true;
            }
        } else {
            validateError("出生地不能为空!", ele);
            return regExp.default.residence = false;
        }
    },

    //公司名称
    company: function (ele) {
        var elemVal = $(ele).val();
        if ($.trim(elemVal).length != 0) {
            if ($.trim(elemVal).length < 0 || 35 <$.trim(elemVal).length ) {
                validateError("小于35位！", ele);
                return regExp.default.company = false;
            }else {
                validateRight("", ele);
                return regExp.default.company = true;
            }
        } else {
            validateError("公司名称不能为空!", ele);
            return regExp.default.company = false;
        }
    },

   //qq
    qq: function (ele) {
        var elemVal = $(ele).val();
        if ($.trim(elemVal).length != 0) {
            if ($.trim(elemVal).length < 0 || 20 <$.trim(elemVal).length) {
                validateError("小于20位！", ele);
                return regExp.default.qq = false;
            }else {
                validateRight("", ele);
                return regExp.default.qq = true;
            }
        } else {
            validateError("QQ不能为空!", ele);
            return regExp.default.qq = false;
        }
    },

    //推荐人Id

//    parentid: function (ele) {
//    var elemVal = $(ele).val();
//     if($.trim(elemVal).length>0&&$.trim(elemVal).length<15){
//      if(isNaN(elemVal)){
//
//       }
//     }
//},




    password:function(ele){
        var elemVal = $(ele).val();
        if ($.trim(elemVal).length != 0 == 0) {
            validateError("密码不能为空！", ele);
            return regExp.default.password = false;
        } else if (!isNaN(elemVal)) {
            validateError("密码不能为纯数字！", ele);
            return regExp.default.password = false;

        } else if ($.trim(elemVal).length < 6 || $.trim(elemVal).length > 16) {
            validateError("密码长度为6至18位！", ele);
            return regExp.default.password = false;
        }
        else {
            validateRight("", ele);
            return regExp.default.password = true;
        }
    },
    passwordone:function(ele){
        var elemVal = $(ele).val();
        if ($.trim(elemVal).length != 0 == 0) {
            validateError("密码不能为空！", ele);
            return regExp.default.passwordone = false;
        } else if (!isNaN(elemVal)) {
            validateError("密码不能为纯数字！", ele);
            return regExp.default.passwordone = false;

        } else if ($.trim(elemVal).length < 6 || $.trim(elemVal).length > 16) {
            validateError("密码长度为6至18位！", ele);
            return regExp.default.passwordone = false;
        }
        else {
            validateRight("", ele);
            return regExp.default.passwordone = true;
        }
    },
    passwordtwo:function(ele,eles){
        var elemVal = $(ele).val();
        var elemVals = $(eles).val();
        if ($.trim(elemVals).length != 0 == 0) {
            validateError("确认密码不能为空！", eles);
            return regExp.default.passwordtwo = false;
        } else if ( $.trim(elemVal) != $.trim(elemVals)) {
            validateError("两次密码不一致！", eles);
            return regExp.default.passwordtwo = false;

        }  else if (!isNaN(elemVals)  ) {
            validateError("确认密码有误请检查！", eles);
            return regExp.default.passwordtwo = false;

        } else if ($.trim(elemVals).length < 6 || $.trim(elemVals).length > 16) {
            validateError("确认密码有误请检查！", eles);
            return regExp.default.passwordtwo = false;
        }else {
            validateRight("", eles);
            return regExp.default.passwordtwo = true;
        }
    },
    email:function(ele){
        var elemVal = $(ele).val();
        if ($.trim(elemVal).length != 0) {
            if (elemVal.match(/^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@(vip.qq|qq|163|126)\.com/)) {
                validateRight('', ele);
                return regExp.default.email = true;
            } else {
                validateError("不合法的邮箱", ele);
            }
        } else {
            validateError("邮箱不能为空", ele);
        }
    },
    vcode:function(ele){
        var elemVal = $(ele).val();
        if ($.trim(elemVal).length != 0) {
            if (elemVal.match(/^\d{4}$/)) {
                validateRight('', ele);
                return regExp.default.vcode = true;
            } else {
                validateError("验证码错误", ele);
            }
        } else {
            validateError("验证码不能为空", ele);
        }
    }

};


function getByteLen(val) {

    var len = 0;
    for (var i = 0; i < val.length; i++) {
        var length = val.charCodeAt(i);
        if (length >= 0 && length <= 128) {
            len += 1;
        }
        else {
            len += 2;
        }
    }
    return len;
}
/*验证失败代码*/
function validateError(msg,ele) {
    var ele = $(ele);
    if (ele.parents('li').find('p').length == 0){
        var html = "<p class='validate_error'><i class='iconfont'>&#xe601;</i>"+msg+"</p>";
        ele.parents('li').append(html);
    }else {
        ele.parents('li').find('p').attr('class','validate_error').html("<i class='iconfont'>&#xe601;</i>"+msg);
    };
};

/*验证成功代码*/
function validateRight(msg,ele) {
    var ele = $(ele);
    if (ele.parents('li').find('p').length == 0){
        var html = "<p class='validate_right'><i class='iconfont'>&#xe623;</i>"+msg+"</p>";
        ele.parents('li').append(html);
    }else {
        ele.parents('li').find('p').attr('class','validate_right').html("<i class='iconfont'>&#xe623;</i>"+msg);
    };
};