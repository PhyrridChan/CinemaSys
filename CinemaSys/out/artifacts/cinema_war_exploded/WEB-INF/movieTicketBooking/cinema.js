//Total amount
function Totalamount(sc) {
    var totalamount = 0;
    sc.find('selected').each(function () {
        totalamount += price;
    });

    return totalamount;
}

function submitTics() {
    var selected = $('#selected-seats').children();
    var tics = [];
    var total = $('#total')[0].innerText;
    var counter = $('#counter')[0].innerText;
    var bid = $('.screen')[0].id;
    for (let i = 0; i < selected.length; i++) {
        tics[i] = selected[i].id.substring(($('#selected-seats').children()[0].id).lastIndexOf('-') + 1);
    }
    postOpenWindow('/cinema/payTheBill', {'tics': tics.toString(), 'total': total, 'counter': counter, 'bid': bid});
}

function postOpenWindow(URL, PARAMS) {
    var temp_form = document.createElement("form");
    temp_form.action = URL;
    temp_form.target = "_blank";
    temp_form.method = "post";
    temp_form.style.display = "none";
    for (var x in PARAMS) {
        var opt = document.createElement("textarea");
        opt.name = x;
        opt.value = PARAMS[x];
        temp_form.appendChild(opt);
    }
    document.body.appendChild(temp_form);
    temp_form.submit();
    document.body.removeChild(temp_form);
}

