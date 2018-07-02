/*运费模板计算
* */
 //到付按钮
 function daofuFee(x){

     var df=$("#daofu").is(':checked');
     if(df){
         //1 为到付
         $("#daofu").val("1");
         $("#hiddendaofu").val("1");
     }
     else{
         //0 为在线付运费
         $("#daofu").val("0");
         $("#hiddendaofu").val("0");
     }
     //到付
     var result=$("#daofu").val();
     if(result=="1"){
         var totalMoney=$("#inputTotalMoney").val();
         //页面显示
         $("#vPostFee").text("￥0.00");
         $("#postFee").val("0.00");
         var fee="0.00";
         var su=getTotalBonusAndCoupon();
         var uMoney=getUserMoney();
         var realTotalPrice=parseFloat(totalMoney)+parseFloat(fee)-parseFloat(su)-parseFloat(uMoney);
         if(parseFloat(realTotalPrice)>=0){
             $("#totalprice").text(realTotalPrice.toFixed(2));
         }
         else{
             $("#totalprice").text("0.00");
         }
     }
     else{
         if(x=="1"){
             getShipping();
         }
         else if(x=="2"){
             getShippingForcart();
         }
     }

 }


//使用的红包和优惠券
function getTotalBonusAndCoupon(){
    var totalBonus=parseFloat($("#totalBonus").val());
    var totalCoupon=parseFloat($("#couponhidenum").val());
    return totalBonus+totalCoupon;
}
//使用的积分或者余额
function getUserMoney(){
    var inputUserMoney=$("#userAmount").val()
    if(inputUserMoney==undefined||inputUserMoney.length==0||inputUserMoney==""){
        return 0;
    }
    else{
        var userMoney=parseFloat($("#userAmount").val());
        return userMoney;
    }
}
//使用的积分或者余额-提交订单
function getUserMoneyForSubmit(){
    var inputUserMoney=$("#userAmount").val()
    if(inputUserMoney==undefined){
        return 1;
    }
    else{
        if(inputUserMoney.length==0||inputUserMoney==""){
            return -1;
        }
        else{
            var userMoney=parseFloat($("#userAmount").val());
            return 2;
        }
    }
}
//实时计算重置数据
function resetData2(){
    var totalMoney=$("#inputTotalMoney").val();
    //运费
    var fee=$("#postFee").val();
    //使用的红包或者优惠券
    var su=getTotalBonusAndCoupon();
    //使用的积分或者优惠券
    var uMoney=getUserMoney();
    var realTotalPrice=parseFloat(totalMoney)+parseFloat(fee)-parseFloat(su)-parseFloat(uMoney);
    if(parseFloat(realTotalPrice)>=0){
        $("#totalprice").text(realTotalPrice.toFixed(2));
    }
    else{
        $("#totalprice").text("0.00");
    }
}
//到付时，使用积分，数量加 减 时引用
function resetData(){
    var totalMoney=$("#inputTotalMoney").val();
    //页面显示
    $("#vPostFee").text("￥0.00");
    $("#postFee").val("0.00");
    var fee="0.00";
    var su=getTotalBonusAndCoupon();
    var uMoney=getUserMoney();
    var realTotalPrice=parseFloat(totalMoney)+parseFloat(fee)-parseFloat(su)-parseFloat(uMoney);
    if(parseFloat(realTotalPrice)>=0){
        $("#totalprice").text(realTotalPrice.toFixed(2));
    }
    else{
        $("#totalprice").text("0.00");
    }
}
//切换地址
function getProvince(x){

    var _this=$(x);
    var addressId=_this.val();
    var shippingId=$("#shipId").val();
    var goodsId=$("#goodsId").val();
    var goodsNum=$("#goodsNum").val();
    var totalMoney=$("#inputTotalMoney").val();
    //console.log("address:"+addressId,shippingId,goodsId,goodsNum,totalMoney);
    var daofu=$("#daofu").val();
    if(daofu=="0"){
        getPostFee(addressId,shippingId,goodsId,goodsNum,totalMoney);
    }
}
//购物车-切换地址
function getProvinceForCart(x){
    var _this=$(x);
    var addressId=_this.val();
    var shippingId=$("#shipId").val();
    var goodsIdArr=new Array();
    var goodsNumArr=new Array();
    $("input[name='goodsId']").each(function(){
        goodsIdArr.push($(this).val());
    });
    //console.log("goodsIdArr:"+goodsIdArr);
    $("input[name='goodsNumber']").each(function(){
        goodsNumArr.push($(this).val());
    });
    //console.log("goodsNumArr:"+goodsNumArr);
    var totalMoney=$("#inputTotalMoney").val();
    var daofu=$("#daofu").val();
    if(daofu=="0") {
        getPostFeeForCart(addressId, shippingId, goodsIdArr.toString(), goodsNumArr.toString(), totalMoney);
    }
}

//选快递公司计算运费
var getShippingInit = false;
function getShipping(){
    var daofu=$("#daofu").val();
    //获取选中的地址的id
    var addressId=$("input[name='site']:checked").val();
    var shippingId=$("#shipId").val();
    var goodsId=$("#goodsId").val();
    var goodsNum=$("#goodsNum").val();
    var totalMoney=$("#inputTotalMoney").val();
    //地址控件选择的省市区-这里只截取省
    var inputProvince=$("#city-picker3").val();
    var province="";
    //新增地址
    if(addressId==undefined){
        if(inputProvince.indexOf("/")>0){
            //截取第一个
            var arr=inputProvince.split('/');
            province=arr[0];
        }
        else{
            province=inputProvince;
        }
        //有省
        if(province.length>0){
            if(daofu=="0") {
                getPostFeeWithProvince(province, shippingId, goodsId, goodsNum, totalMoney);
            }
        }
    }
    else{//有地址的直接用addressId来取运费
        if(daofu=="0"){
            getPostFee(addressId,shippingId,goodsId,goodsNum,totalMoney);
        }
    }
}

//购物车-选快递公司计算运费
function getShippingForcart(){
    var daofu=$("#daofu").val();
    //获取选中的地址的id
    var addressId=$("input[name='site']:checked").val();
    var shippingId=$("#shipId").val();
    var goodsIdArr=new Array();
    var goodsNumArr=new Array();
    $("input[name='goodsId']").each(function(){
        goodsIdArr.push($(this).val());
    });
    $("input[name='goodsNumber']").each(function(){
        goodsNumArr.push($(this).val());
    });
    //console.log("goodsIdArr:"+goodsIdArr+"---goodsNumArr:"+goodsNumArr);
    var totalMoney=$("#inputTotalMoney").val();
    //地址控件选择的省市区-这里只截取省
    var inputProvince=$("#city-picker3").val();
    var province="";
    //新增地址
    if(addressId==undefined){
        if(inputProvince.indexOf("/")>0){
            //截取第一个
            var arr=inputProvince.split('/');
            province=arr[0];
        }
        else{
            province=inputProvince;
        }
        //有省
        if(province.length>0){
            if(daofu=="0") {
                getPostFeeWithProvinceForCart(province, shippingId, goodsIdArr.toString(), goodsNumArr.toString(), totalMoney);
            }
        }
    }
    else{//有地址的直接用addressId来取运费

        if(daofu=="0") {
            getPostFeeForCart(addressId, shippingId, goodsIdArr.toString(), goodsNumArr.toString(), totalMoney);
        }
    }
}
//ajax获取运费的函数-有地址
function getPostFee(addressId,shippingId,goodsId,goodsNum,totalMoney){

    var param={
        "addressId":addressId,
        "shippingId":shippingId,
        "goodsId":goodsId,
        "goodsNum":goodsNum
    };
    //切换地址获取运费
    $.ajax({
        type: 'post',
        url: '/web/order/getPostFee',
        data: param,
        dataType:"json",
        async:false,
        success: function (data) {
            //console.log(data);
            if(data.success=="true"){
                var fee=data.fee;
                if(fee=="0"){
                    //页面显示
                    $("#vPostFee").text("￥0.00");
                    $("#postFee").val("0.00");
                }
                else{
                    //页面显示
                    $("#vPostFee").text("￥"+fee.toFixed(2));
                    $("#postFee").val(fee.toFixed(2));
                }
                var su=getTotalBonusAndCoupon();
                var uMoney=getUserMoney();
                var realTotalPrice=parseFloat(totalMoney)+parseFloat(fee)-parseFloat(su)-parseFloat(uMoney);
                if(parseFloat(realTotalPrice)>=0){
                    $("#totalprice").text(realTotalPrice.toFixed(2));
                }
                else{
                    $("#totalprice").text("0.00");
                }
            }
            else{
                gmallHint(data.errMsg+"请选择合适的配送方式，以免影响发货！");
            }
        }
    });
}
//ajax获取运费的函数-购物车-有地址
function getPostFeeForCart(addressId,shippingId,goodsId,goodsNum,totalMoney){

    var param={
        "addressId":addressId,
        "shippingId":shippingId,
        "goodsId":goodsId,
        "goodsNum":goodsNum
    };
    //切换地址获取运费
    $.ajax({
        type: 'post',
        url: '/web/order/getPostFeeForCart',
        data: param,
        dataType:"json",
        async:false,
        success: function (data) {
            //console.log(data);
            if(data.success=="true"){
                var fee=data.fee;
                if(fee=="0"){
                    //页面显示
                    $("#vPostFee").text("￥0.00");
                    $("#postFee").val("0.00");
                }
                else{
                    //页面显示
                    $("#vPostFee").text("￥"+fee.toFixed(2));
                    $("#postFee").val(fee.toFixed(2));
                }
                //总的红包或者优惠券
                var su=getTotalBonusAndCoupon();
                var uMoney=getUserMoney();
                var realTotalPrice=parseFloat(totalMoney)+parseFloat(fee)-parseFloat(su)-parseFloat(uMoney);
                if(parseFloat(realTotalPrice)>=0){
                    $("#totalprice").text(realTotalPrice.toFixed(2));
                }
                else{
                    $("#totalprice").text("0.00");
                }
            }
            else{
                gmallHint(data.errMsg+"请选择合适的配送方式，以免影响发货！");
            }
        }
    });
}
//ajax获取运费的函数-新增地址
function getPostFeeWithProvince(province,shippingId,goodsId,goodsNum,totalMoney){

    var param={
        "province":province,
        "shippingId":shippingId,
        "goodsId":goodsId,
        "goodsNum":goodsNum
    };
    //切换地址获取运费
    $.ajax({
        type: 'post',
        url: '/web/order/getPostFeeWithProvince',
        data: param,
        dataType:"json",
        async:false,
        success: function (data) {
            //console.log(data);
            if(data.success=="true"){
                var fee=data.fee;
                if(fee=="0"){
                    //页面显示
                    $("#vPostFee").text("￥0.00");
                    $("#postFee").val("0.00");
                }
                else{
                    //页面显示
                    $("#vPostFee").text("￥"+fee.toFixed(2));
                    $("#postFee").val(fee.toFixed(2));
                }
                //总的红包和优惠券
                var su=getTotalBonusAndCoupon();
                var uMoney=getUserMoney();
                var realTotalPrice=parseFloat(totalMoney)+parseFloat(fee)-parseFloat(su)-parseFloat(uMoney);
                if(parseFloat(realTotalPrice)>=0){
                    $("#totalprice").text(realTotalPrice.toFixed(2));
                }
                else{
                    $("#totalprice").text("0.00");
                }
            }
            else{
                gmallHint(data.errMsg+"请选择合适的配送方式，以免影响发货！");
            }
        }
    });
}
//ajax获取运费的函数-购物车-新增地址
function getPostFeeWithProvinceForCart(province,shippingId,goodsId,goodsNum,totalMoney){

    var param={
        "province":province,
        "shippingId":shippingId,
        "goodsId":goodsId,
        "goodsNum":goodsNum
    };
    //切换地址获取运费
    $.ajax({
        type: 'post',
        url: '/web/order/getPostFeeWithProvinceForCart',
        data: param,
        dataType:"json",
        async:false,
        success: function (data) {
            //console.log(data);
            if(data.success=="true"){
                var fee=data.fee;
                if(fee=="0"){
                    //页面显示
                    $("#vPostFee").text("￥0.00");
                    $("#postFee").val("0.00");
                }
                else{
                    //页面显示
                    $("#vPostFee").text("￥"+fee.toFixed(2));
                    $("#postFee").val(fee.toFixed(2));
                }
                //总的红包和优惠券
                var su=getTotalBonusAndCoupon();
                var uMoney=getUserMoney();
                var realTotalPrice=parseFloat(totalMoney)+parseFloat(fee)-parseFloat(su)-parseFloat(uMoney);
                if(parseFloat(realTotalPrice)>=0){
                    $("#totalprice").text(realTotalPrice.toFixed(2));
                }
                else{
                    $("#totalprice").text("0.00");
                }
            }
            else{
                gmallHint(data.errMsg+"请选择合适的配送方式，以免影响发货！");
            }
        }
    });
}
//提交订单前检验
function checkPostFeeBeforeSubmit(){

    //获取选中的地址的id
    var addressId=$("input[name='site']:checked").val();
    //配送方式id
    var shippingId=$("#shipId").val();
    //商品id
    var goodsId=$("#goodsId").val();
    //商品数量
    var goodsNum=$("#goodsNum").val();
    //商品总价
    var totalMoney=$("#inputTotalMoney").val();

    //地址控件选择的省市区-这里只截取省
    var inputProvince=$("#city-picker3").val();
    var province="";
    //新增地址
    if(addressId==undefined){
        if(inputProvince.indexOf("/")>0){
            //截取第一个
            var arr=inputProvince.split('/');
            province=arr[0];
        }
        else{
            province=inputProvince;
        }
        //有省
        if(province.length>0){
            return getPostFeeWithProvinceReturn(province,shippingId,goodsId,goodsNum,totalMoney);
        }
        else{
            return -1;
        }
        //console.log("province:"+province);
    }
    else{//有地址的直接用addressId来取运费
        return getPostFeeWithReturn(addressId,shippingId,goodsId,goodsNum,totalMoney);
    }
    //console.log("checkPostFee:"+addressId,shippingId,goodsId,goodsNum,totalMoney);

}

//提交订单前检验-有地址-通过addressId-ajax获取运费-有返回值
function getPostFeeWithReturn(addressId,shippingId,goodsId,goodsNum,totalMoney){
    var res=0;
    var param={
        "addressId":addressId,
        "shippingId":shippingId,
        "goodsId":goodsId,
        "goodsNum":goodsNum
    };
    //切换地址获取运费
    $.ajax({
        type: 'post',
        url: '/web/order/getPostFee',
        data: param,
        dataType:"json",
        async:false,
        success: function (data) {
            //console.log(data);
            if(data.success=="true"){
                res+=1;
                var fee=data.fee;
                if(fee=="0"){
                    //页面显示
                    $("#vPostFee").text("￥0.00");
                    $("#postFee").val("0.00");
                }
                else{
                    //页面显示
                    $("#vPostFee").text("￥"+fee.toFixed(2));
                    $("#postFee").val(fee.toFixed(2));
                }
                //总的红包和优惠券
                var su=getTotalBonusAndCoupon();
                var uMoney=getUserMoney();
                var realTotalPrice=parseFloat(totalMoney)+parseFloat(fee)-parseFloat(su)-parseFloat(uMoney);
                if(parseFloat(realTotalPrice)>=0){
                    $("#totalprice").text(realTotalPrice.toFixed(2));
                }
                else{
                    $("#totalprice").text("0.00");
                }
            }
        }
    });
    return res;
}

//提交订单前检验-新增地址-通过addressId-ajax获取运费-有返回值
function getPostFeeWithProvinceReturn(province,shippingId,goodsId,goodsNum,totalMoney){
    var res=0;
    var param={
        "province":province,
        "shippingId":shippingId,
        "goodsId":goodsId,
        "goodsNum":goodsNum
    };
    //新增地址获取运费
    $.ajax({
        type: 'post',
        url: '/web/order/getPostFeeWithProvince',
        data: param,
        dataType:"json",
        async:false,
        success: function (data) {
            //console.log(data);
            if(data.success=="true"){
                res+=1;
                var fee=data.fee;
                if(fee=="0"){
                    //页面显示
                    $("#vPostFee").text("￥0.00");
                    $("#postFee").val("0.00");
                }
                else{
                    //页面显示
                    $("#vPostFee").text("￥"+fee.toFixed(2));
                    $("#postFee").val(fee.toFixed(2));
                }
                //总的红包和优惠券
                var su=getTotalBonusAndCoupon();
                var uMoney=getUserMoney();
                var realTotalPrice=parseFloat(totalMoney)+parseFloat(fee)-parseFloat(su)-parseFloat(uMoney);
                if(parseFloat(realTotalPrice)>=0){
                    $("#totalprice").text(realTotalPrice.toFixed(2));
                }
                else{
                    $("#totalprice").text("0.00");
                }
            }
        }
    });
    return res;
}

//提交订单前检验-购物车
function checkPostFeeBeforeSubmitForCart(){

    //获取选中的地址的id
    var addressId=$("input[name='site']:checked").val();
    //配送方式id
    var shippingId=$("#shipId").val();
    //商品id
    //var goodsId=$("#goodsId").val();
    //商品数量
    //var goodsNum=$("#goodsNum").val();

    var goodsIdArr=new Array();
    var goodsNumArr=new Array();
    $("input[name='goodsId']").each(function(){
        goodsIdArr.push($(this).val());
    });
    $("input[name='goodsNumber']").each(function(){
        goodsNumArr.push($(this).val());
    });
    //console.log("goodsIdArr:"+goodsIdArr+"---goodsNumArr:"+goodsNumArr);
    //商品总价
    var totalMoney=$("#inputTotalMoney").val();

    //地址控件选择的省市区-这里只截取省
    var inputProvince=$("#city-picker3").val();
    var province="";
    //新增地址
    if(addressId==undefined){
        if(inputProvince.indexOf("/")>0){
            //截取第一个
            var arr=inputProvince.split('/');
            province=arr[0];
        }
        else{
            province=inputProvince;
        }
        //有省
        if(province.length>0){
            return getPostFeeWithProvinceReturnForCart(province,shippingId,goodsIdArr.toString(),goodsNumArr.toString(),totalMoney);
        }
        else{
            return -1;
        }
        //console.log("province:"+province);
    }
    else{//有地址的直接用addressId来取运费
        return getPostFeeWithReturnForCart(addressId,shippingId,goodsIdArr.toString(),goodsNumArr.toString(),totalMoney);
    }
    //console.log("checkPostFee:"+addressId,shippingId,goodsId,goodsNum,totalMoney);

}

//提交订单前检验-有地址-通过addressId-ajax获取运费-有返回值-购物车
function getPostFeeWithReturnForCart(addressId,shippingId,goodsId,goodsNum,totalMoney){
    var res=0;
    var param={
        "addressId":addressId,
        "shippingId":shippingId,
        "goodsId":goodsId,
        "goodsNum":goodsNum
    };
    //切换地址获取运费
    $.ajax({
        type: 'post',
        url: '/web/order/getPostFeeForCart',
        data: param,
        dataType:"json",
        async:false,
        success: function (data) {
            //console.log(data);
            if(data.success=="true"){
                res+=1;
                var fee=data.fee;
                if(fee=="0"){
                    //页面显示
                    $("#vPostFee").text("￥0.00");
                    $("#postFee").val("0.00");
                }
                else{
                    //页面显示
                    $("#vPostFee").text("￥"+fee.toFixed(2));
                    $("#postFee").val(fee.toFixed(2));
                }
                //总的红包和优惠券
                var su=getTotalBonusAndCoupon();
                var uMoney=getUserMoney();
                var realTotalPrice=parseFloat(totalMoney)+parseFloat(fee)-parseFloat(su)-parseFloat(uMoney);
                if(parseFloat(realTotalPrice)>=0){
                    $("#totalprice").text(realTotalPrice.toFixed(2));
                }
                else{
                    $("#totalprice").text("0.00");
                }
            }
        }
    });
    return res;
}

//提交订单前检验-新增地址-通过addressId-ajax获取运费-有返回值-购物车
function getPostFeeWithProvinceReturnForCart(province,shippingId,goodsId,goodsNum,totalMoney){
    var res=0;
    var param={
        "province":province,
        "shippingId":shippingId,
        "goodsId":goodsId,
        "goodsNum":goodsNum
    };
    //新增地址获取运费
    $.ajax({
        type: 'post',
        url: '/web/order/getPostFeeWithProvinceForCart',
        data: param,
        dataType:"json",
        async:false,
        success: function (data) {
            //console.log(data);
            if(data.success=="true"){
                res+=1;
                var fee=data.fee;
                if(fee=="0"){
                    //页面显示
                    $("#vPostFee").text("￥0.00");
                    $("#postFee").val("0.00");
                }
                else{
                    //页面显示
                    $("#vPostFee").text("￥"+fee.toFixed(2));
                    $("#postFee").val(fee.toFixed(2));
                }
                //总的红包和优惠券
                var su=getTotalBonusAndCoupon();
                var uMoney=getUserMoney();
                var realTotalPrice=parseFloat(totalMoney)+parseFloat(fee)-parseFloat(su)-parseFloat(uMoney);
                if(parseFloat(realTotalPrice)>=0){
                    $("#totalprice").text(realTotalPrice.toFixed(2));
                }
                else{
                    $("#totalprice").text("0.00");
                }
            }
        }
    });
    return res;
}


//监控输入
;(function($) {
    $.fn.monitoringIn = function(option) {
        var defauls = {
            maxNum:0,
            oldVal:0
        };
        var options = $.extend('',defauls,option);
        this.each(function() {
            var _this = $(this);
            options.maxNum = _this.attr('max');
            var fn = function() {
                var curVal = _this.val(),
                    curValN = Number(curVal);

                if (curValN || curValN === 0){
                    if (curValN<0){
                        gmallHint('请输入大于0的数字');
                        _this.val(options.oldVal);
                        return false;
                    }else if (0<=curValN && curValN<options.maxNum){
                        if (curValN%1 === 0){
                            options.oldVal = Number(curVal);
                            //实时修改显示金额
                            resetData2();
                            return true;
                        }else {
                            gmallHint('请输入整数数字');
                            _this.val(options.oldVal);
                            return false;
                        };

                    }else {
                        gmallHint('请输入小于'+options.maxNum+'的数字');
                        _this.val(options.oldVal);
                        return false;
                    }
                }else {
                    gmallHint('请输入整数数字');
                    _this.val(options.oldVal);
                    return false;
                };

            };

            //绑定输入事件监听器
            if (window.addEventListener) { //先执行W3C
                _this.get(0).addEventListener("input", fn, false);
            } else {
                _this.get(0).attachEvent("onpropertychange", fn);
            }
            if (window.VBArray && window.addEventListener) { //IE9
                _this.get(0).attachEvent("onkeydown", function() {
                    var key = window.event.keyCode;
                    (key == 8 || key == 46) && fn(); //处理回退与删除
                });
                _this.get(0).attachEvent("oncut", fn); //处理粘贴
            }
        });
    }
})(jQuery);