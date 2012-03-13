/**
 * Created by IntelliJ IDEA.
 * User: Dzmitry_Stsiapanau
 * Date: 2/24/12
 * Time: 8:19 AM
 */
(function($) {
    var defaults;

    var options;

    $.fn.counterPlugin = function(inputName, initCount, counter) {
        options = $.extend({}, defaults, options, inputName, initCount, counter);
        var koefficent = Math.round(255 / initCount);
        // вычисляем первоначальное значение счетчика и его цвет
        var value = $(inputName).attr('value') || '';
        var initLength = Math.max((value ? (initCount - value.length) : initCount), 0);
        $(counter).css('color', 'rgb(' + (koefficent * value.length) + ', 0, 0)');
        $(counter).html(initLength.toString());

        $(inputName).keyup(function() {
            var value = $(this).attr('value') || '';
            var lengthToGo = Math.max((initCount - value.length) + 1, 0);
            $(counter).css('color', 'rgb(' + (koefficent * value.length) + ', 0, 0)');
            $(counter).html((lengthToGo - 1).toString());
            if (lengthToGo == 0) {
                var alertMessage = $("#alertLastSymb").val();
                alert(alertMessage);
                var valuelast = $(inputName).attr('value');
                var valuenew = valuelast.substr(0, valuelast.length - 1);
                $(inputName).attr('value', valuenew);
                $(counter).css('color', 'rgb(' + (koefficent * value.length) + ', 0, 0)');
                $(counter).html((lengthToGo).toString());
            }

        });
        return this;
    };
})(jQuery);
$(document).ready(function(e) {
    $("#listDelete").click(function(e) {
        var allCheckbox = $(".checkbox:enabled");
        var checked = allCheckbox.is(':checked');
        if (checked) {
            var confirmMessage = $("#confirmFromList").val();
            if (confirm(confirmMessage)) {
                $(this).submit();
            } else {
                e.preventDefault();
            }
        } else {
            var notChecked = $("#notChecked").val();
            alert(notChecked);
            e.preventDefault();
        }
    });
    $("#viewDelete").click(function(e) {
        var confirmMessage = $("#confirmFromView").val();
        if (confirm(confirmMessage)) {
            $(this).submit();
        } else {
            e.preventDefault();
        }
    });
    $("#editCancel").click(function(e) {
        $("#editDate").attr('value', $("#dateCurrent").val());

    });
     $(this).counterPlugin('#editTitle', 100, '#title_counter');
     $(this).counterPlugin('#editDate', 10, '#date_counter');
     $(this).counterPlugin('#editBrief', 500, '#brief_counter');
     $(this).counterPlugin('#editContext', 2048, '#context_counter');
    $("#editSave").click(function(e) {
        var regExpRus = /^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$/;
        var regExpUs = /^(((0?[1-9]|1[012])\/(0?[1-9]|1\d|2[0-8])|(0?[13456789]|1[012])\/(29|30)|(0?[13578]|1[02])\/31)\/(19|[2-9]\d)\d{2}|0?2\/29\/((19|[2-9]\d)(0[48]|[2468][048]|[13579][26])|(([2468][048]|[3579][26])00)))$/;
        var alertMessage = "";
        if ($("#editTitle").val() == "") {
            alertMessage += $("#alertTitle").val();
        }

        if ($("#editBrief").val() == "") {
            alertMessage += $("#alertBrief").val();
        }
        if ($("#editContext").val() == "") {
            alertMessage += $("#alertContent").val();
        }
        var eDate = $("#editDate").val();
        if (eDate == "" ||
                !(((eDate.match(regExpRus)))
                        || (eDate.match(regExpUs)))) {
            alertMessage += $("#alertDate").val();
        } else {
            var month;
            var day;
            var year;
            var date;
            var dateArray;
            var dateOut;
            if (eDate.match(/(\.)/)) {
                dateArray = (eDate.split(/(\.)/));
                day = dateArray[0];
                month = dateArray[1];
                year = dateArray[2];
                date = new Date(year, month, day);
                dateOut = date;
            }
            if (eDate.match(/(-)/)) {
                dateArray = (eDate.split(/(-)/));
                day = dateArray[2];
                month = dateArray[1];
                year = dateArray[0];
                date = new Date(year, month, day);
                dateOut = date;
            }
            if (eDate.match(/(\/)/)) {
                dateArray = (eDate.split(/(\/)/));
                day = dateArray[1];
                month = dateArray[0];
                year = dateArray[2];
                date = new Date(year, month, day);
                dateOut = date;
            }
            //var crD = "" + dateOut.getFullYear() + "-" + (dateOut.getMonth()).toString() + "-" + dateOut.getDay();
            var crD = "" + year + "-" + month + "-" + day;
            $("#editDate").attr('value', crD);
        }
        if (alertMessage == "") {
            $(this).submit();
        } else {
            alert(alertMessage);
            $("#editDate").attr('value', eDate);
            e.preventDefault();
        }

    });

});
