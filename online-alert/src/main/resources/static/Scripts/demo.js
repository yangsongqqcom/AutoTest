/*! SmartAdmin - v1.4.1 - 2014-06-26 */
$("#ribbon").append('<div class="demo"><span id="demo-setting"><i class="fa fa-cog txt-color-blueDark"></i></span> <form><legend class="no-padding margin-bottom-10">布局管理</legend><section><label><input name="subscription" id="smart-fixed-header" type="checkbox" class="checkbox style-0"><span>固定顶部</span></label><label><input type="checkbox" name="terms" id="smart-fixed-navigation" class="checkbox style-0"><span>固定导航</span></label><label><input type="checkbox" name="terms" id="smart-fixed-ribbon" class="checkbox style-0"><span>固定网页导航</span></label><label><input type="checkbox" name="terms" id="smart-fixed-footer" class="checkbox style-0"><span>固定底部</span></label> <span id="smart-bgimages"></span></section><section><h6 class="margin-top-10 semi-bold margin-bottom-5">清空本地缓存</h6><a href="javascript:void(0);" data-action="resetWidgets" class="btn btn-xs btn-block btn-primary" id="reset-smart-widget"><i class="fa fa-refresh"></i> 重置网站配置</a></section> <h6 class="margin-top-10 semi-bold margin-bottom-5">主题选择</h6><section id="smart-styles"><a href="javascript:void(0);" id="smart-style-0" data-skinlogo="/Content/themes/smart/img/logo.png" class="btn btn-block btn-xs txt-color-white margin-right-5" style="background-color:#4E463F;"><i class="fa fa-check fa-fw" id="skin-checked"></i>默认</a><a href="javascript:void(0);" id="smart-style-1" data-skinlogo="/Content/themes/smart/img/logo-white.png" class="btn btn-block btn-xs txt-color-white" style="background:#3A4558;">深色</a><a href="javascript:void(0);" id="smart-style-2" data-skinlogo="/Content/themes/smart/img/logo-blue.png" class="btn btn-xs btn-block txt-color-darken margin-top-5" style="background:#fff;">白亮</a><a href="javascript:void(0);" id="smart-style-3" data-skinlogo="/Content/themes/smart/img/logo-pale.png" class="btn btn-xs btn-block txt-color-white margin-top-5" style="background:#f78c40">橙红</a></section></form> </div>');
var smartbgimage = "<h6 class='margin-top-10 semi-bold'>Background</h6><img src='/Content/themes/smart//Content/themes/smart/img/pattern/graphy-xs.png' data-htmlbg-url='/Content/themes/smart/img/pattern/graphy.png' width='22' height='22' class='margin-right-5 bordered cursor-pointer'><img src='/Content/themes/smart/img/pattern/tileable_wood_texture-xs.png' width='22' height='22' data-htmlbg-url='/Content/themes/smart/img/pattern/tileable_wood_texture.png' class='margin-right-5 bordered cursor-pointer'><img src='/Content/themes/smart/img/pattern/sneaker_mesh_fabric-xs.png' width='22' height='22' data-htmlbg-url='/Content/themes/smart/img/pattern/sneaker_mesh_fabric.png' class='margin-right-5 bordered cursor-pointer'><img src='/Content/themes/smart/img/pattern/nistri-xs.png' data-htmlbg-url='/Content/themes/smart/img/pattern/nistri.png' width='22' height='22' class='margin-right-5 bordered cursor-pointer'><img src='/Content/themes/smart/img/pattern/paper-xs.png' data-htmlbg-url='/Content/themes/smart/img/pattern/paper.png' width='22' height='22' class='bordered cursor-pointer'>";
$("#smart-bgimages").fadeOut();
$("#demo-setting").click(function () {
    $("#ribbon .demo").toggleClass("activate")
});
$('input[type="checkbox"]#smart-fixed-header').click(function () {
    if ($(this).is(":checked")) {
        $.root_.addClass("fixed-header")
        localStorage.setItem("smart-fixed-header", "1");
    }
    else {
        $('input[type="checkbox"]#smart-fixed-ribbon').prop("checked", !1);
        $('input[type="checkbox"]#smart-fixed-navigation').prop("checked", !1);
        $.root_.removeClass("fixed-header");
        $.root_.removeClass("fixed-navigation");
        $.root_.removeClass("fixed-ribbon");
        localStorage.removeItem("smart-fixed-header");
        localStorage.removeItem("smart-fixed-navigation");
        localStorage.removeItem("smart-fixed-ribbon");
    }
});
$('input[type="checkbox"]#smart-fixed-navigation').click(function () {
    if ($(this).is(":checked")) {
        $('input[type="checkbox"]#smart-fixed-header').prop("checked", !0);
        $.root_.addClass("fixed-header");
        $.root_.addClass("fixed-navigation");
        $('input[type="checkbox"]#smart-fixed-container').prop("checked", !1);
        $.root_.removeClass("container");
        localStorage.setItem("smart-fixed-navigation", "1");
    } else {
        $('input[type="checkbox"]#smart-fixed-ribbon').prop("checked", !1);
        $.root_.removeClass("fixed-navigation");
        $.root_.removeClass("fixed-ribbon");
        localStorage.removeItem("smart-fixed-navigation");
        localStorage.removeItem("smart-fixed-ribbon");
        localStorage.setItem("smart-fixed-header", "1");
    }
});
$('input[type="checkbox"]#smart-fixed-ribbon').click(function () {
    if ($(this).is(":checked")) {
        $('input[type="checkbox"]#smart-fixed-header').prop("checked", !0);
        $('input[type="checkbox"]#smart-fixed-navigation').prop("checked", !0);
        $('input[type="checkbox"]#smart-fixed-ribbon').prop("checked", !0);
        $.root_.addClass("fixed-header");
        $.root_.addClass("fixed-navigation");
        $.root_.addClass("fixed-ribbon");
        $('input[type="checkbox"]#smart-fixed-container').prop("checked", !1);
        $.root_.removeClass("container");
        localStorage.setItem("smart-fixed-ribbon", "1");
    }
    else {
        $.root_.removeClass("fixed-ribbon")
        localStorage.removeItem("smart-fixed-ribbon");
        localStorage.setItem("smart-fixed-navigation", "1");
    }
});
$('input[type="checkbox"]#smart-fixed-footer').click(function () {
    if ($(this).is(":checked")) {
        $.root_.addClass("fixed-page-footer");
        localStorage.setItem("smart-fixed-footer", "1");
    }
    else {
        $.root_.removeClass("fixed-page-footer");
        localStorage.removeItem("smart-fixed-footer");
    }
});
$('input[type="checkbox"]#smart-rtl').click(function () {
    $(this).is(":checked") ? $.root_.addClass("smart-rtl") : $.root_.removeClass("smart-rtl")
});
$("#smart-topmenu").on("change", function () {
    if ($(this).prop("checked")) {
        localStorage.setItem("sm-setmenu", "top");
        location.reload();
    }
    else {
        localStorage.setItem("sm-setmenu", "left");
        location.reload();
    }
});
if ("top" == localStorage.getItem("sm-setmenu")) {
    $("#smart-topmenu").prop("checked", !0);
}
else {
    $("#smart-topmenu").prop("checked", !1);
}
$('input[type="checkbox"]#smart-fixed-container').click(function () {
    if ($(this).is(":checked")) {
        $.root_.addClass("container");
        $('input[type="checkbox"]#smart-fixed-ribbon').prop("checked", !1);
        $.root_.removeClass("fixed-ribbon");
        $('input[type="checkbox"]#smart-fixed-navigation').prop("checked", !1);
        $.root_.removeClass("fixed-navigation");
        if (smartbgimage) {
            $("#smart-bgimages").append(smartbgimage).fadeIn(1e3);
            $("#smart-bgimages img").bind("click", function () {
                var a = $(this),
                    b = $("html");
                bgurl = a.data("htmlbg-url");
                b.css("background-image", "url(" + bgurl + ")");
            });
            smartbgimage = null
        }
        else {
            $("#smart-bgimages").fadeIn(1e3)
        }
    }
    else {
        $.root_.removeClass("container"), $("#smart-bgimages").fadeOut()
    }
});

//$("#reset-smart-widget").bind("click", function () {
//    localStorage.removeItem("smart-fixed-ribbon");
//    localStorage.removeItem("smart-fixed-navigation");
//    localStorage.removeItem("smart-fixed-header");
//    localStorage.removeItem("smart-fixed-footer");
//    localStorage.removeItem("smart-skin-id");
//    $("#refresh").click();
//    return !1
//});

$("#smart-styles > a").on("click", function () {
    var a = $(this),
        b = $("#logo img");
    localStorage.setItem("smart-skin-id", a.attr("id"));
    $.root_.removeClassPrefix("smart-style").addClass(a.attr("id"));
    b.attr("src", a.data("skinlogo"));
    $("#smart-styles > a #skin-checked").remove();
    a.prepend("<i class='fa fa-check fa-fw' id='skin-checked'></i>");
});
$(function () {
    if ("1" == localStorage.getItem("smart-fixed-ribbon"))
        $("#smart-fixed-ribbon").click();
    else if ("1" == localStorage.getItem("smart-fixed-navigation"))
        $("#smart-fixed-navigation").click();
    else if ("1" == localStorage.getItem("smart-fixed-header"))
        $("#smart-fixed-header").click();
    if ("1" == localStorage.getItem("smart-fixed-footer"))
        $("#smart-fixed-footer").click();
    var skinId = localStorage.getItem("smart-skin-id");
    if (skinId) {
        $("#" + skinId).click();
    }
});