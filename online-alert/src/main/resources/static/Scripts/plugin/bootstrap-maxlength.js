/*! SmartAdmin - v1.4.1 - 2014-06-26 */
!function (a) {
    "use strict";
    a.fn.extend({
        maxlength: function (b, c) {
            function d(a) {
                var c = a.val(),
                    d = c.match(/\n/g),
                    e = 0,
                    f = 0;
                b.utf8 ? (e = d ? getUTF8Length(d) : 0, f = getUTF8Length(a.val()) + e) : (e = d ? d.length : 0, f = a.val().length + e);
                return f
            }
            function e(a, c, e) {
                var f = !0; return !b.alwaysShow && e - d(a) > c && (f = !1), f
            }
            function f(a, b) {
                var c = b - d(a); return c
            }
            function g(a) {
                a.css({ display: "block" })
            }
            function h(a) {
                a.css({ display: "none" })
            }
            function i(a, c) {
                var d = "";
                b.message ? d = b.message.replace("%charsTyped%", c).replace("%charsRemaining%", a - c).replace("%charsTotal%", a) : (b.preText && (d += b.preText),
                d += b.showCharsTyped ? c : a - c, b.showMaxLength && (d += b.separator + a), b.postText && (d += b.postText));
                return d
            }
            function j(a, c, d, f) {
                f.html(i(d, d - a)),
                a > 0 ? e(c, b.threshold, d) ? g(f.removeClass(b.limitReachedClass).addClass(b.warningClass)) : h(f) : g(f.removeClass(b.warningClass).addClass(b.limitReachedClass))
            }
            function k(b) {
                var c = b[0];
                return a.extend({}, "function" == typeof c.getBoundingClientRect ? c.getBoundingClientRect() : { width: c.offsetWidth, height: c.offsetHeight }, b.offset())
            }
            function l(a, c) {
                var d = k(a),
                    e = a.outerWidth(),
                    f = c.outerWidth(),
                    g = c.width(),
                    h = c.height();
                switch (b.placement) {
                    case "bottom":
                        c.css({ top: d.top + d.height, left: d.left + d.width / 2 - g / 2 });
                        break;
                    case "top":
                        c.css({ top: d.top - h, left: d.left + d.width / 2 - g / 2 });
                        break;
                    case "left":
                        c.css({ top: d.top + d.height / 2 - h / 2, left: d.left - g });
                        break;
                    case "right":
                        c.css({ top: d.top + d.height / 2 - h / 2, left: d.left + d.width });
                        break;
                    case "bottom-right":
                        c.css({ top: d.top + d.height, left: d.left + d.width });
                        break;
                    case "top-right":
                        c.css({ top: d.top - h, left: d.left + e });
                        break;
                    case "top-left":
                        c.css({ top: d.top - h, left: d.left - f });
                        break;
                    case "bottom-left":
                        c.css({ top: d.top + a.outerHeight(), left: d.left - f });
                        break;
                    case "centered-right":
                        c.css({ top: d.top + h / 2, left: d.left + e - f - 3 })
                }
            }
            function m(a) {
                return a.attr("maxlength") || a.attr("size")
            }
            var n = a("body");
            var o = {
                alwaysShow: !1,
                threshold: 10,
                warningClass: "label label-success",
                limitReachedClass: "label label-important",
                separator: " / ",
                preText: "",
                postText: "",
                showMaxLength: !0,
                placement: "bottom",
                showCharsTyped: !0,
                validate: !1,
                utf8: !1
            };
            a.isFunction(b) && !c && (c = b, b = {}), b = a.extend(o, b);
            return this.each(function () {
                var c = a(this),
                    d = m(c),
                    e = a("<span></span>").css({ display: "none", position: "absolute", whiteSpace: "nowrap", zIndex: 50 }).html(i(d, "0"));
                if (c.is("textarea")) {
                    c.data("maxlenghtsizex", c.outerWidth()),
                    c.data("maxlenghtsizey", c.outerHeight()),
                    c.mouseup(function () {
                        if (c.outerWidth() !== c.data("maxlenghtsizex") || c.outerHeight() !== c.data("maxlenghtsizey"))
                            l(c, e);
                        c.data("maxlenghtsizex", c.outerWidth()),
                        c.data("maxlenghtsizey", c.outerHeight())
                    })
                }
                n.append(e),
                c.focus(function () {
                    var a = f(c, m(c));
                    j(a, c, d, e), l(c, e)
                }),
                a(window).resize(function () {
                    l(c, e)
                }),
                c.blur(function () {
                    e.css("display", "none")
                }),
                c.keyup(function (a) {
                    var g = f(c, m(c)),
                        h = !0,
                        i = a.keyCode || a.which;
                    0 !== g || 9 !== i || a.shiftKey || c.attr("maxlength", m(c) + 1).trigger({ type: "keypress", which: 9 }).attr("maxlength", m(c) - 1),
                    b.validate && 0 > g ? h = !1 : j(g, c, d, e);
                    return h
                })
            })
        }
    })
}(jQuery);