/**
 * Created by cmi  getBrowser and browserVersion on 2016-10-21.
 */
function browserInfo() {

    var browser = { name: 'unknown',version: 0 };
    var userAgent = window.navigator.userAgent.toLowerCase();
//        console.log(userAgent);

    var regexpopr = /(opr)\D+(\d[\d.]*)|(opera)\D+(\d[\d.]*)/;

    var regexsafari = /(version)\D+(\d[\d.]*).*safari/;

    var regexpfirefox = /(firefox)\D+(\d[\d.]*)/;

    var regexchrome = /(chrome)\D+(\d[\d.]*)/;

    var regexpIE = /(msie)\D+(\d[\d.]*)|(rv)\D+(\d[\d.]*)/;


    if (regexpopr.test(userAgent)){
        browser.name = 'opera';
        if (RegExp.$2){
            browser.version = RegExp.$2;
        }else {
            browser.version = RegExp.$4;
        }

    }
    else if(regexsafari.test(userAgent)){
        browser.name = 'safari';
        browser.version = RegExp.$2;
    }
    else if (regexpfirefox.test(userAgent)){
        browser.name = 'firefox';
        browser.version = RegExp.$2;
    }
    else if(regexchrome.test(userAgent)){
        browser.name = 'chrome';
        browser.version = RegExp.$2;
    }
    else if (regexpIE.test(userAgent)){
        browser.name = 'IE';
        if (userAgent.match(regexpIE)[2]){
            browser.version = userAgent.match(regexpIE)[2];
        }else {
            browser.version = userAgent.match(regexpIE)[4];
        }

    }
    return browser;

}