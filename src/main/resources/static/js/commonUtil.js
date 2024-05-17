// --------------------------------------------------- 체크용 함수 (boolean 값 리턴)
// 공란 or 띄어쓰기만 들어간 값 체크 (null 체크용)
function nullCheck(str) {
    if ($(str).val()) {
        if($(str).val().indexOf(" ") < 0) {
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }
}
// 전부 공란인것만 체크
function nullCheck2(str) {
    if ($(str).val()) {
        if($(str).val().trim()) {
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }
}
// 전부 공란인것만 체크 ver Array
function nullCheck2Arr(arr) {
    for(let e of $(arr)) {
        if ($(e).val()) {
            if($(e).val().trim()) {
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    return true;
}

// 길이 체크용 str => 비교 대상 문자열 / min => 최소 글자 수 (min 값보다 str이 작으면 false로 체크) / max => 최대 글자 수 (max 값 보다 str이 크면 false로 체크)
function lengthCheck(min, max, str) {
    if($(str).val().length < min || $(str).val().length > max) {
        return false;
    } else {
        return true;
    }
}


// 비밀번호 체크 - 영문대소문자 + 0~9자리 + 특수문자 (!@#$%^*+=-)
function passwordRegCheck(element) {
    let reg = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/;
    if (!reg.test($(element).val())){
        return false;
    } else {
        return true;
    }
}

// 아이디 체크
function idRegCheck(element) {
    let reg = /^[a-z0-9]{6,20}$/;
    if (!reg.test($(element).val())){
        return false;
    } else {
        return true;
    }
}

// 이름 체크
function nameRegCheck(element) {
    let reg = /^[ㄱ-ㅎ|가-힣]{2,20}$/;
    if (!reg.test($(element).val())){
        return false;
    } else {
        return true;
    }
}

// 비밀번호 일치 여부 체크
function passwordMatch(element, target) {
    if($(element).val() != $(target).val()) {
        return false;
    } else {
        return true;
    }
}

// 핸드폰 번호 체크
function phoneRegCheck(element) {
    let reg = /^[0-9]{10,11}$/;
    if (!reg.test($(element).val())){
        return false;
    } else {
        return true;
    }
}

function emailRegCheck(element) {
    let reg = /^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6})*$/;
    if (!reg.test($(element).val())){
        return false;
    } else {
        return true;
    }
}

function dateCheck(element) {
    let today = new Date();
    let target = new Date($(element).val());
    if ( target > today) {
        return false;
    } else {
        return true;
    }
}

function dateCheck2(element1, element2) {
    let before = new Date($(element1).val());
    let after = new Date($(element2).val());
    if ( before > after) {
        return false;
    } else {
        return true;
    }
}

// --------------------------------------------------- replace용 함수 - 요소에 직접 걸어서 값을 바로 조작하는 용도
// 공백 문자 제거용 input에 onKeyUp 이벤트로 인자로 this 로 넣어주면 됨.
function replaceBlank(element) {
    let removedValue = element.value.replaceAll(" ", "");
    element.value = removedValue;
}

// 핸드폰 번호 replace 요소에 onkeyup 으로 걸면 됨 (숫자만 입력 가능하도록!!!)
function replacePhone(element) {
    $(element).val($(element).val().replace(/[^0-9]/g, ""));
}

// 한글만 입력하도록 다른 글자 다 제거
function replaceName(element) {
    replaceBlank(element);
    element.value = element.value.replace(/[^ㄱ-ㅎ가-힣]/g, "");
}

