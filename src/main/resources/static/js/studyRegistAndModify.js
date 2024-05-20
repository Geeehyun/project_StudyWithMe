function addInput(element) {
    let type = element.dataset.type;
    let divEl = document.createElement('div');
    let inputEl = document.createElement('input');
    let buttonEl = document.createElement('button');
    divEl.classList.add('input-group', 'mb-1', 'w-25');
    inputEl.classList.add('form-control');
    inputEl.type = 'text';
    inputEl.name = type;
    inputEl.maxLength = 10;
    buttonEl.classList.add('btn', 'btn-outline-secondary');
    buttonEl.onclick = ()=> (deleteThis(buttonEl));
    buttonEl.type = 'button';
    buttonEl.innerText = 'X';
    divEl.append(inputEl);
    divEl.append(buttonEl);
    if(element.parentElement.querySelectorAll('div.input-group').length <4) {
        element.parentElement.append(divEl);
    } else {
        alert("최대 4개까지 추가할 수 있습니다.")
    }
}

function deleteThis(element) {
    if(element.parentElement.parentElement.querySelectorAll('div.input-group').length > 1) {
        element.parentElement.remove();
    } else {
        alert("최소 1개의 입력칸이 필요합니다.")
    }
}
function deleteThisToShare(element) {
    element.parentElement.parentElement.remove();
}
function deleteThis2(element) {
    element.parentElement.remove();
    $('#id-' + element.parentElement.dataset.id).prop("checked", false);
    if(document.querySelectorAll('span.memLi').length == 0) {
        $('#selected-list').text("리스트가 없습니다.");
    }
}

function changeDisplay(element) {
    let displayDates = document.querySelectorAll('.displayDate');
    if(element.value == 'N') {
        for (let displayDate of displayDates) {
            displayDate.value = "";
            displayDate.setAttribute('disabled','disabled');
        }
    } else {
        for (let displayDate of displayDates) {
            displayDate.removeAttribute('disabled');
        }
    }
}

function addMemberList(data) {
    let memberList = document.querySelectorAll('input[name=sharedMemberId]');
    for(let mem of memberList) {
        if(mem.value == data.memberId) {
            alert("이미 추가된 멤버 입니다.");
            return;
        }
    }
    let divEl = document.createElement('div');
    let spanEl = document.createElement('span');
    let spanEl2 = document.createElement('span');
    let buttonEl = document.createElement('button');
    let inputEl = document.createElement('input');
    let inputEl2 = document.createElement('input');
    spanEl.classList.add('d-block');
    spanEl2.innerText = data.memberName+" ";
    buttonEl.classList.add('btn', 'badge', 'bg-primary-dark');
    buttonEl.onclick = ()=> (deleteThisToShare(buttonEl));
    buttonEl.type = 'button';
    buttonEl.innerText = 'X';
    inputEl.type = 'hidden';
    inputEl.name = 'sharedMemberId';
    inputEl.value = data.memberId;
    inputEl2.type = 'hidden';
    inputEl2.name = 'sharedMemberName';
    inputEl2.value = data.memberName;
    spanEl.append(spanEl2);
    spanEl.append(buttonEl);
    divEl.append(spanEl);
    divEl.append(inputEl);
    divEl.append(inputEl2);
    document.getElementById('memberList').append(divEl);
}

function addMember() {
    let memberId = document.getElementById('memberId');
    let memberIdVal = document.getElementById('memberId').value;
    if(memberIdVal.length > 0) {
        $.ajax({
            url: "/mystudy/selectMember",
            method: 'post',
            dataType : 'json',
            data : {
                "memberId" : memberIdVal
            },
            success: function (data) {
                console.log(data);
                if(data.message == '조회 성공') {
                    addMemberList(data);
                    memberId.value = '';
                } else {
                    memberId.value = '';
                    alert(data.message);
                }
            },
            error : function(xhr, status, error) {
                console.log("xhr! : " + xhr);
                console.log("status! : " + status);
                console.log("error! : " + error);
            }
        })
    } else {
        alert("아이디를 입력해주세요");
    }
}

// 썸네일 이미지 변경
function changeProfileImg(e) {
    let files = e.target.files;
    let reader = new FileReader();
    reader.onload = (e)=>{
        $('#thumbnail_img').attr('src', e.target.result);
    }
    reader.readAsDataURL(files[0]);
}



// Front 유효성검사
let checkTarget = ['title', 'displayYn', 'displayStartDate', 'displayEndDate', 'category', 'tags', 'content'];
document.querySelector('#frmPost').addEventListener('submit', checkForm);
function checkForm() {
    event.preventDefault();
    for (let info of document.querySelectorAll('.info')) {
        $(info).text("");
    }
    // 공란 검사
    for (let element of checkTarget) {
        let target = $('input[name=' + element + ']');
        if (element == 'displayYn') {
            if(!$('input#shareN').is(":checked") && !$('input#shareY').is(":checked")) {
                $('#err_'+element).text($(target).data('name') + "을 선택해주세요");
                $(target).focus();
                return false;
            }
        } else if(element == 'displayStartDate' || element == 'displayEndDate') {
            if($('input#shareY').is(":checked")) {
                if (!nullCheck2($(target))) {
                    $('#err_displayDate').text($(target).data('name') + "을/를 입력해주세요");
                    $(target).focus();
                    return false;
                }
            }
        } else if(element == 'category' || element == 'tags') {
            if (!nullCheck2Arr($(target))) {
                $('#err_' + element).text($(target).data('name') + "을/를 최소 1건 이상 입력해주시고, 추가한 모든 칸을 입력해주세요");
                $(target).focus();
                return false;
            }
        } else if(element == 'content') {
            if (!nullCheck2($('#content'))) {
                $('#err_' + element).text($('#content').data('name') + "을/를 입력해주세요");
                $('#content').focus();
                return false;
            }
        } else if (!nullCheck2($(target))) {
            $('#err_' + element).text($(target).data('name') + "을/를 입력해주세요");
            $(target).focus();
            return false;
        }
    }
    // 길이 검사
    if(!lengthCheck(1, 100, $('input#title'))){
        $('#err_title').text("제목은 1글자 이상 100글자 미만으로 입력해주세요");
        $('#title').focus();
        return false;
    }
    if(!lengthCheck(1, 1000, $('textarea#content'))){
        $('#err_content').text("내용은 1글자 이상 1000글자 미만으로 입력해주세요");
        $('#content').focus();
        return false;
    }
    // 날짜 체크
    if($('input#shareY').is(":checked")) {
        if(!dateCheck2($('#displayStartDate'), $('#displayEndDate'))) {
            $('#err_displayDate').text("시작 날짜는 종료날짜 보다 미래일 수 없습니다.");
            $('#displayStartDate').focus();
            return false;
        }
    }
    if(confirm("저장하시겠습니까?")) {
        document.getElementById('frmPost').submit();
    }
}

function addOrRemove(element) {
    let targetId = element.dataset.id;
    let targetName = element.dataset.name;
    let memLies = document.querySelectorAll('span.memLi');
    if($(element).is(":checked")) {
        if(memLies.length > 0) {
            for(let mem of memLies) {
                if(mem.dataset.id == targetId) {
                    alert("이미 추가된 회원 입니다.");
                    return false;
                }
            }
        } else {
            $('#selected-list').html("");
        }
        $('#selected-list').append(`<span class="memLi" data-id="${targetId}" data-name="${targetName}">${targetName} <button class="btn badge bg-primary-dark" onclick="deleteThis2(this)">x</button></span>`);
    } else {
        for(let mem of memLies) {
            if(mem.dataset.id == targetId) {
                $(mem).remove();
            }
        }
    }
    if(document.querySelectorAll('span.memLi').length == 0) {
        $('#selected-list').text("리스트가 없습니다.");
    }
}

function getMemberList() {
    resetModal();
    let memberList = document.querySelectorAll('#memberList > div');
    if(memberList.length > 0) {
        $('#selected-list').html("");
        for(let memLi of memberList) {
            let targetId = memLi.querySelector('input[name=sharedMemberId]').value;
            let targetName = memLi.querySelector('input[name=sharedMemberName]').value;
            $('#selected-list').append(`<span class="memLi" data-id="${targetId}" data-name="${targetName}">${targetName} <button type="button" class="btn badge bg-primary-dark" onclick="deleteThis2(this)">x</button></span>`);
        }
    }
}

function resetModal() {
    $('#idOrName').val("");
    $('#selected-list').text("리스트가 없습니다.");
    $('#modal-tBody').html(`<tr><td class="text-center" colspan="4">아이디 또는 이름으로 검색해주세요</td>`);
}

function saveThisMem() {
    let memLi = document.querySelectorAll('.memLi');
    console.log(memLi);
    // if(memLi.length > 0) {
        $('#memberList').html("");
        for(let mem of memLi) {
            let targetId = mem.dataset.id;
            let targetName = mem.dataset.name;
            $('#memberList').append(`<div><span class="d-block"><span>${targetName} </span><button type="button" class="btn badge bg-primary-dark" onclick="deleteThisToShare(this)">x</button></span><input type="hidden" name="sharedMemberId" value="${targetId}"><input type="hidden" name="sharedMemberName" value="${targetName}"></div>`);
        }
    // } else {
    //     alert("추가할 회원이 없습니다. 기존");
    //     event.preventDefault();
    //     event.stopPropagation();
    // }
    resetModal();
}

function AJAXMemberList() {
    let idOrName = $('#idOrName');
    let idOrNameVal = $('#idOrName').val();
    if(!nullCheck($(idOrName))){
        alert("아이디 또는 이름을 입력해주세요");
        $(idOrName).focus();
    } else {
        $.ajax({
            url: "/member/memberList",
            method: 'post',
            dataType : 'json',
            data : {
                "memberIdOrName" : idOrNameVal
            },
            success: function (data) {
                if(data.length > 0) {
                    let memIdArr = [];
                    let memLies = document.querySelectorAll('span.memLi');
                    for(let mem of memLies) {
                        memIdArr.push(mem.dataset.id);
                    }
                    $('#modal-tBody').html("");
                    let cnt = data.length;
                    for(let i = 0; i < data.length; i++) {
                        let target = data[i].replaceAll("=", ":");
                        let obj = JSON.parse(target);
                        let targetId = obj.memberId;
                        let targetName = obj.memberName;
                        if(memIdArr.indexOf(targetId) >= 0) {
                            $('#modal-tBody').append(`<tr><td class="text-center">${cnt}</td><td class="text-center">${targetId}</td><td class="text-center">${targetName}</td><td class="text-center"><input class="form-check-input" type="checkbox" data-name="${targetName}" data-id="${targetId}" id="id-${targetId}" checked onchange="addOrRemove(this)"></td></tr>`);
                        } else {
                            $('#modal-tBody').append(`<tr><td class="text-center">${cnt}</td><td class="text-center">${targetId}</td><td class="text-center">${targetName}</td><td class="text-center"><input class="form-check-input" type="checkbox" data-name="${targetName}" data-id="${targetId}" id="id-${targetId}" onchange="addOrRemove(this)"></td></tr>`);
                        }
                        cnt--;
                    }
                } else {
                    alert("일치하는 회원이 없습니다. 다시 확인 부탁 드립니다.");
                }
            },
            error : function(xhr, status, error) {
                console.log("xhr! : " + xhr);
                console.log("status! : " + status);
                console.log("error! : " + error);
            }
        })
    }
}

