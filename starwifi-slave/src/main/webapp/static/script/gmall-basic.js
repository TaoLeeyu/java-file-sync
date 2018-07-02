/**
 * Created by XMing on 2017-03-09.
 */

$(document).ready(function () {

    /*样式文件添加随机版本号*/
   $('link').each(function () {

        if ($(this).attr('rel') == 'stylesheet'){
            var linkHref = $(this).attr('href');

            $(this).attr('href',linkHref+'?v'+Math.random());
        };

    });


    /*导航跟随*/
    var urlHref = window.location.pathname;
    var urlHrefSearch = window.location.search;
    $('.gui_leftnav').find('li a[href]').each(function () {

        if ($(this).attr('href').indexOf(urlHref) == 0){
            $('.gui_leftnav').find('li a[href]').removeClass('gui_lnavcur');
            $(this).addClass('gui_lnavcur');
        };

    });

    $('#gmallnav').find('li a[href]').each(function () {
        // console.log(urlHref == '/');
        if (urlHref == '/'){
            $('#gmallnav').find('li').removeClass('gui_navcur');
            $('#gmallnav').find('li').eq(0).addClass('gui_navcur');
        }else if ($(this).attr('href').indexOf(urlHref+urlHrefSearch) == 0){
            $('#gmallnav').find('li').removeClass('gui_navcur');
            $(this).parent('li').addClass('gui_navcur');
        };

    });

    var navName = urlHref.substring(1,urlHref.indexOf('/',1));
    switch (navName){
        case 'member':
            if (urlHref == '/member/allorder'){
                $('#membernavbar').children('li').removeClass('gui_navbarcur');
                $('#membernavbar').children('li').eq(1).addClass('gui_navbarcur');
            }else {
                $('#membernavbar').children('li').removeClass('gui_navbarcur');
                $('#membernavbar').children('li').eq(0).addClass('gui_navbarcur');
            };
            break;
        case 'safety':
            $('#membernavbar').children('li').removeClass('gui_navbarcur');
            $('#membernavbar').children('li').eq(2).addClass('gui_navbarcur');
    };






    /*导航下拉展开*/
    $('#gmnavshowbtn').click(function (e) {
        var event = e||window.event;
        $(this).parents('dl').find('ol').slideToggle();
        event.stopPropagation();
    });
    $('#gmnavshowbtnbox').hover(function () {
        $(this).find('ol').slideToggle();
    });


    $('#gmhint_colse').click(function () {
        $(this).parents('#gmall_hint').hide();
    });

    $('#gmconfirm_colse').click(function () {
        $(this).parents('#gmall_confirm').hide();
    });

    $('#gmconfirmbtn').click(function () {
        var confirmType = $(this).attr('name');
        var oid =$(this).attr('oid');
        if(null==oid){
            gmConfirm(confirmType);
        }
        else{
            gmConfirm(confirmType,oid);
        }

    });

});


function gmallHint(txt) {
    $('#gmall_hint').show();
    $('#gmall_hint').find('.gmhint_msg').text(txt);
};

function gmallConfirm(txt,type) {
    $('#gmall_confirm').show();
    $('#gmall_confirm').find('.gmhint_msg').text(txt);
    $('#gmall_confirm').find('#gmconfirmbtn').attr('name',type);
};

//传订单ID的提示框 新加
function gmallConfirm2(txt,type,oid) {
    $('#gmall_confirm').show();
    $('#gmall_confirm').find('.gmhint_msg').text(txt);
    $('#gmall_confirm').find('#gmconfirmbtn').attr('name',type);
    $('#gmall_confirm').find('#gmconfirmbtn').attr('oid',oid);
};


/*计算函数*/
function Calculation(a,b,type){

    var m = 0,
        ma= 0,
        mb= 0,
        pow='',
        result,
        resultLen,
        inta,
        intb,
        numa= a.toString(),
        numb= b.toString();

    ma = (numa.indexOf('.')!=-1 && numa.length-numa.indexOf('.')-1)||0;
    mb = (numb.indexOf('.')!=-1 && numb.length-numb.indexOf('.')-1)||0;

    m=Math.max(ma,mb);//m 为最大小数位

    if (m == 0){
        inta = parseInt(a);
        intb = parseInt(b);
    }else if (m == ma){
        numa= (ma==0 && numa)||numa.split('.');
        numb= (mb==0 && numb)||numb.split('.');
        inta = parseInt(numa[0]+numa[1]);
        for (var i=0;i<m-mb;i++){
            pow += '0';
        };
        intb =(typeof (numb)== "string" && parseInt(numb+pow)) || parseInt(numb[0]+numb[1]+pow);
    }else {
        numa= (ma==0 && numa)||numa.split('.');
        numb= (mb==0 && numb)||numb.split('.');
        intb = parseInt(numb[0]+numb[1]);
        for (var i=0;i<m-ma;i++){
            pow += '0';
        }
        inta =(typeof (numa)== "string" && parseInt(numa+pow)) || parseInt(numa[0]+numa[1]+pow);
    };

    switch (type){
        case 'add':
            result = (inta+intb).toString();
            resultLen = result.length;
            if (resultLen>=m){
                result = result.substr(0,resultLen-m)+'.'+result.substr(resultLen-m);
            }else {
                pow = '';
                for (var i=0;i<m-resultLen;i++){
                    pow += '0';
                }
                result ='0.'+pow+result;
            }
            result = parseFloat(result);
            return result;
            break;
        case 'sub':
            result = (inta-intb).toString();
            resultLen = result.length;
            if (resultLen>=m){
                result = result.substr(0,resultLen-m)+'.'+result.substr(resultLen-m);
            }else {
                pow = '';
                for (var i=0;i<m-resultLen;i++){
                    pow += '0';
                    //console.log(pow + "---"+result+"--"+resultLen);
                }
                result ='0.'+pow+result;
            }
            result = parseFloat(result,2);
            return result;
            break;
        case 'mul':
            inta = (a>0 && inta)||-inta;
            intb = (b>0 && intb)||-intb;
            result = (inta*intb).toString();
            resultLen = result.length;
            pow='';

            if (resultLen>=m*2){
                result = result.substr(0,resultLen-m*2)+'.'+result.substr(resultLen-m*2);
            }else {
                pow = '';
                for (var i=0;i<m*2-resultLen;i++){
                    pow += '0';
                }
                result ='0.'+pow+result;
            }
            result = (a*b>0 && parseFloat(result,2))||-parseFloat(result,2);
            return result;
            break;
    };

};

/*订单结算页面金额计算*/
function orderCalculate(ele,type) {
    var _this = $(ele),
        goodNum = parseInt(_this.parents('.gmgoodslist').find('.gmall_addnum').children('input').val()), //数量
        kcunNum = parseInt(_this.parents('.gmgoodslist').find('.goodinventory').text()),//库存
        unitPrice = parseFloat(_this.parents('.gmgoodslist').find('.unitprice').text()),//单价
        goodPrice = parseFloat(_this.parents('.gmgoodslist').find('.goodprice').text()),//商品价
        totalPrice = parseFloat(_this.parents('.gui_main').find('#totalprice').text()),//总商品价
        totalMoney = parseFloat(_this.parents('.gui_main').find('#inputTotalMoney').val()),//总商品价隐藏域
        realPay = parseFloat(_this.parents('.gui_main').find('#realpay').text()),//实付款
    //dc +商品ID
        goodsId=_this.parents('.gmgoodslist').find('.gmall_addnum').children('input').attr("cid");
    //alert("goodsId:"+goodsId);
    var result = [goodNum,kcunNum,unitPrice,goodPrice,totalPrice,totalMoney,realPay];
    //console.log(result);

    if (type == 'sub'){
        if (goodNum>1){
            goodNum = goodNum -1;
            //kcunNum = kcunNum + 1;
            goodPrice = Calculation(goodPrice,unitPrice,'sub');
            totalPrice = Calculation(totalPrice,unitPrice,'sub');
            totalMoney = Calculation(totalMoney,unitPrice,'sub');
            realPay = Calculation(realPay,unitPrice,'sub');

            var result = [goodNum,kcunNum,goodPrice,totalPrice,totalMoney,realPay];
            //同步更新购物车
            var result2=orderSync(goodsId,goodNum);
            //alert("result2_sub:"+result2);
            if(result2==1){
                writeResult(_this,result);
            }
            else{
                gmallHint("同步购物车失败，请重试");
            }


            /*if (goodNum == 1){
             _this.css('background','#c4c3c3');
             };*/

        }else {
            return false;
        };

    }else{

        if (kcunNum > goodNum){
            goodNum = goodNum +1;
            //kcunNum = kcunNum - 1;
            goodPrice = Calculation(goodPrice,unitPrice,'add');
            totalPrice = Calculation(totalPrice,unitPrice,'add');
            totalMoney = Calculation(totalMoney,unitPrice,'add');
            realPay = Calculation(realPay,unitPrice,'add');

            var result = [goodNum,kcunNum,goodPrice,totalPrice,totalMoney,realPay];
            //同步更新购物车
            var result2=orderSync(goodsId,goodNum);
            //alert("result2_add:"+result2);
            if(result2==1){
                writeResult(_this,result);
            }
            else{
                gmallHint("同步购物车失败，请重试");
            }


        }else {
            _this.parents('.gmgoodslist').find('.gmall_addnum').children('input').val(kcunNum);
            return false;
        };

    };

};

function orderCalculate2(ele,type) {
    var _this = $(ele),
        goodNum = parseInt(_this.parents('.gmgoodslist').find('.gmall_addnum').children('input').val()), //数量
        kcunNum = parseInt(_this.parents('.gmgoodslist').find('.goodinventory').text()),//库存
        unitPrice = parseFloat(_this.parents('.gmgoodslist').find('.unitprice').text()),//单价
        goodPrice = parseFloat(_this.parents('.gmgoodslist').find('.goodprice').text()),//商品价
        totalPrice = parseFloat(_this.parents('.gui_main').find('#totalprice').text()),//总商品价
        totalMoney = parseFloat(_this.parents('.gui_main').find('#inputTotalMoney').val()),//总商品价隐藏域
        realPay = parseFloat(_this.parents('.gui_main').find('#realpay').text());//实付款
    //dc +商品ID
    //goodsId=_this.parents('.gmgoodslist').find('.gmall_addnum').children('input').attr("cid");
    //alert("goodsId:"+goodsId);
    var result = [goodNum,kcunNum,unitPrice,goodPrice,totalPrice,totalMoney,realPay];
    //console.log(result);

    if (type == 'sub'){
        if (goodNum>1){
            goodNum = goodNum -1;
            //kcunNum = kcunNum + 1;
            goodPrice = Calculation(goodPrice,unitPrice,'sub');
            totalPrice = Calculation(totalPrice,unitPrice,'sub');
            totalMoney = Calculation(totalMoney,unitPrice,'sub');
            realPay = Calculation(realPay,unitPrice,'sub');

            var result = [goodNum,kcunNum,goodPrice,totalPrice,totalMoney,realPay];
            writeResult(_this,result);


            /*if (goodNum == 1){
             _this.css('background','#c4c3c3');
             };*/

        }else {
            return false;
        };

    }else{

        if (kcunNum > goodNum){
            goodNum = goodNum +1;
            //kcunNum = kcunNum - 1;
            goodPrice = Calculation(goodPrice,unitPrice,'add');
            totalPrice = Calculation(totalPrice,unitPrice,'add');
            totalMoney = Calculation(totalMoney,unitPrice,'add');
            realPay = Calculation(realPay,unitPrice,'add');

            var result = [goodNum,kcunNum,goodPrice,totalPrice,totalMoney,realPay];
            writeResult(_this,result);


        }else {
            _this.parents('.gmgoodslist').find('.gmall_addnum').children('input').val(kcunNum);
            return false;
        };

    };

};

function writeResult(ele,array) {//[goodNum,kcunNum,goodPrice,totalPrice,realPay]
    var _this = ele;
    _this.parents('.gmgoodslist').find('.gmall_addnum').children('input').val(array[0]);
    _this.parents('.gmgoodslist').find('.goodinventory').text(array[1]);
    if (_this.parents('.gmgoodslist').find('.goodprice').length > 0){
        _this.parents('.gmgoodslist').find('.goodprice').text(array[2].toFixed(2));
    };
    _this.parents('.gui_main').find('#totalprice').text(array[3].toFixed(2));
    _this.parents('.gui_main').find('#inputTotalMoney').val(array[4].toFixed(2));

    if (_this.parents('.gui_main').find('#realpay').length > 0){
        _this.parents('.gui_main').find('#realpay').text(array[5].toFixed(2));
    };
};

//同步购物车方法
function orderSync(cid,goodsNum){
    var result=-1;
    if(parseInt(cid)>0&&parseInt(goodsNum)>0) {
        //同步更新购物车
        $.ajax({
            type: 'post',
            url: '/cart/cartSynch',
            data: {"cid": cid, "goodsNum": goodsNum},
            async: false,
            success: function (data) {
                //alert("data:"+data);
                if (data == "1") {
                    result = 1
                }
                else {
                    result = 0;
                }
            },
            error: function (data) {
                gmallHint("data-error:" + data);
            }
        });
    }
    return result;
}




