function UploadImg(obj) {
    var file = obj.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function (e) {
        var img = document.getElementById("myAvator");
        img.src = e.target.result;
    };
}

$('input[type=radio][name="nav"]').change(function () {
    var myvalue = $('input:radio[name="nav"]:checked').val();
    var choose;
    var table;
    do {
        if (myvalue == "userRadio") {
            var choose = document.getElementById("usersO");
        } else {
            if (myvalue == "movieRadio") {
                choose = document.getElementById("movieO");
                table = document.getElementById("movieTable");
            }
            if (myvalue == "cinemaRadio") {
                choose = document.getElementById("cinemaO");
                table = document.getElementById("cinemaTable");
            }
            if (myvalue == "NewsRadio") {
                var choose = document.getElementById("NewsO");
                table = document.getElementById("NewsTable");

            }
            if (myvalue == "rankRadio") {
                var choose = document.getElementById("rankO");
                table = document.getElementById("rankTable");

            }
            $('.pure-table').css('display', 'none');
            table.style.display = "inline-block";
            console.log(table.id);
            $('[name="tableName"]').val(table.id);
        }
    } while (false);

    $('.titleBar').css('display', 'none');
    choose.style.display = "inline-block";
    // console.log("here :" + tableName);
    updateQueryInput(table)
});

function sendInfo(obj) {
    var name = obj.id;
    console.log("name:" + name);
    var table;
    $('.subTitle').css('display', 'none');
    $('.pure-table').css('display', 'none');
    var subtitle;
    if (name == "cusRadio") {
        subtitle = document.getElementById("cusTitle");
        table = document.getElementById("cusTable");
    }
    if (name == "staffRadio") {
        subtitle = document.getElementById("staffTitle");
        table = document.getElementById("staffTable");
    }
    subtitle.style.display = "inline-block";
    table.style.display = "inline-block";
    console.log(table.id);
    $('[name="tableName"]').val(table.id);
    console.log("here :" + tableName + ";here: " + table);
    updateQueryInput(table)
};

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //??????????????????????????????????????????????????????
    var r = window.location.search.substr(1).match(reg);  //??????????????????
    // console.log("here " + decodeURI(r[2]));
    if (r != null) return decodeURI(r[2]);
    else return null; //???????????????
}

const tableName = getUrlParam("tableName");

function updateQueryInput(table) {
    if (table.id == tableName) $('[name="query"]').val(getUrlParam("query"));
    else $('[name="query"]').val("");
}

function alter(o) {
    $(o).parents("tr").find("input").removeAttr("readOnly");
    $(o).css("display", "none");
    $(o).next().css("display", "");
};

function confirm(o) {
    updateTable(o);
    $(o).parents("tr").find("input").attr("readOnly", true);
    $(o).css("display", "none");
    $(o).prev().css("display", "");
}

function deleteIt(o) {
    uniOpe("delOpe", o);
}

function higher(o) {
    uniOpe("higherOpe", o);
}

function lower(o) {
    uniOpe("lowerOpe", o);
}

function uniOpe(type, o) {
    const checked = $('table[class="pure-table"][style="display: inline-block;"]');
    let temp = document.createElement('form');
    temp.action = '/cinema/test';
    temp.method = 'POST';
    let inputType = document.createElement('input');
    let inputTb = document.createElement('input');
    let inputID = document.createElement('input');

    inputType.type = 'hidden';
    inputType.name = 'type';
    inputType.value = type;

    inputTb.type = 'hidden';
    inputTb.name = 'id';
    inputTb.value = $(o).parent().parent().parent()[0].id;

    inputID.type = 'hidden';
    inputID.name = 'tableName';
    inputID.value = checked[0].id;

    temp.appendChild(inputType);
    temp.appendChild(inputTb);
    temp.appendChild(inputID);
    document.body.appendChild(temp);
    console.log(inputType.value + ";" + inputTb.value + ";" + inputID.value);
    temp.submit();
}


function updateTable(o) {
    const num = $(o).parent().parent().parent().children();
    const checked = $('table[class="pure-table"][style="display: inline-block;"]');
    const checkedInfo = checked.children('thead').children().children();
    let temp = document.createElement('form');
    temp.action = '/cinema/test';
    temp.method = 'POST';
    let inputTb = document.createElement('input');
    inputTb.type = 'hidden';
    inputTb.name = 'id';
    inputTb.value = $(o).parent().parent().parent()[0].id;
    console.log("!!!!!!!!!!");
    console.log($(o).parent().parent().parent()[0].id);
    temp.appendChild(inputTb);
    let inputID = document.createElement('input');
    inputID.type = 'hidden';
    inputID.name = 'tableName';
    inputID.value = checked[0].id;
    temp.appendChild(inputID);
    for (let i = 0; i < num.length - 1; i++) {
        let input = document.createElement('input');
        input.type = 'hidden';
        input.name = checkedInfo[i].innerText;
        input.value = $($(num[i]).children()).val();
        console.log(checkedInfo[i].innerText);
        console.log($($(num[i]).children()).val());
        temp.appendChild(input);
    }
    document.body.appendChild(temp);
    temp.submit();
}

function addrow() {
    var checked = $('table[class="pure-table"][style="display: inline-block;"]');
    var str = '';
    switch (checked[0].id) {
        case 'movieTable':
            str = '<tr id="new"><td>**</td><td><input style="width: 50px"type="text"placeholder="??????"></td><td><input type="text"placeholder="????????????????????????"></td><td><input type="text"placeholder="?????????"></td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="????????????"></td><td><input type="text"placeholder="????????????"></td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="????????????"></td><td><input type="text"placeholder="????????????"></td><td><input type="text"placeholder="????????????"></td><td><input type="text"placeholder="??????"></td><td><div style="width: 100px;text-align: center"><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;">??????</a><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;display: none;"onclick="alter(this)">??????</a><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;"onclick="confirm(this)" id="cpdd">??????</a></div></td></tr>';
            break;
        case 'cinemaTable':
            str = '<tr id="new"><td>**</td><td><input style="width: 50px"type="text"placeholder="??????"></td><td><input type="text"placeholder="?????????(engName)"></td><td><input type="text"placeholder="????????????"></td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="?????????"></td><td><div style="width: 100px;text-align: center"><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;">??????</a><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;display: none;"onclick="alter(this)">??????</a><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;"onclick="confirm(this)">??????</a></div></td></tr>';
            break;
        case 'NewsTable':
            alert('??????????????????');
            return;
        case 'rankTable':
            str = '<tr id="new"><td>**</td><td><input type="text"placeholder="211208212321000001"></td><td>itemName(engName)</td><td><div style="width: 190px;text-align: center;display: inline-table;"><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;padding: 5px;">??????</a><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;padding: 5px;display: none;"onclick="alter(this)">??????</a><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;padding: 5px;"onclick="confirm(this)">??????</a><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;padding: 5px;">??????</a><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;padding: 5px;">??????</a></div></td></tr>';
            break;
        case 'cusTable':
            str = '<tr id="new"><td>**</td><td><input type="text"placeholder="??????"></td><td>?????????</td><td>??????</td><td>??????</td><td>??????</td><td>??????</td><td>??????</td><td>????????????</td><td><input type="text"placeholder="property"></td><td>credit</td><td>value</td><td>vip</td><td><div style="width: 100px;text-align: center"><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;">??????</a><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;display: none;"onclick="alter(this)">??????</a><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;"onclick="confirm(this)">??????</a></div></td></tr>';
            break;
        case 'staffTable':
            str = '<tr id="new"><td>**</td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="??????"></td><td>????????????</td><td><input type="text"placeholder="??????"></td><td><input type="text"placeholder="?????????"></td><td><input type="text"placeholder="?????????"></td><td><input type="text"placeholder="???????????????"></td><td>????????????</td><td><div style="width: 100px;text-align: center"><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;padding-right: 5px;">??????</a><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;display: none;"onclick="alter(this)">??????</a><a href="#"style="color: #e0e0e0; text-decoration: none;font-size: 18px;;"onclick="confirm(this)">??????</a></div></td></tr>';
            break;
    }
    checked.prepend(str);
    return false;
}


$(document).ready(
    function () {
        // if (tableName == "cusTable" || tableName == "staffTable"){
        //     const radioName = tableName.replace("Table", "Radio");
        //     sendInfo(document.getElementById(radioName));
        // }
        $('input[type=radio][name="nav"]').trigger('change');
    }
)
