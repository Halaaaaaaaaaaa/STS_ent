var am = document.getElementById("am").value;
var pm = document.getElementById("pm").value;
var formSubmitted = false;

if(document.getElementById("id").value == null || document.getElementById("id").value == ""){
	window.close();
	window.opener.location.href="login_form";
}

//input number 가 활성화 되었을때의 함수
$('#headvalue').on('input', function(e) {
	// 숫자 높일때마다의 값을 가져옴
	var clickhead = parseInt($(this).val());

	var price = document.getElementById('price').value;
	// 총 가격 표시
	totalAmount = clickhead * price;
	document.getElementById('totalPrice').innerHTML = totalAmount;
});

function submitForm() {
	var form = document.getElementById("museum_booking");
    var totalPriceInput = document.querySelector('input[name="totalPrice"]');
    var totalPrice = parseInt(document.getElementById("totalPrice").textContent);
    var head = parseInt(document.getElementById("headvalue").value);
    var headtrimm = document.getElementById("headvalue").value;
    var seat = document.getElementById("seat").value;
    var morningReservation = parseInt(document.getElementById("head1").value);
    var afternoonReservation = parseInt(document.getElementById("head2").value);
    var mseat=morningReservation + head;
    var aseat=afternoonReservation + head;
    if (document.getElementById("seat").value == "") {
		alert("시간대를 선택해주세요");
		document.getElementById("seat").focus();
		return false;
	}
    else if (seat == "오전" && mseat > am) {
    	alert("오전 예약 가능한 인원을 초과하였습니다. "+"(잔여석 :"+(am-morningReservation)+" 석)");
        return false; // 함수 종료
    }
    // 예약 시간이 오후인 경우
    else if (seat == "오후" && aseat > pm) {
        alert('오후 예약 가능한 인원을 초과하였습니다. (잔여석 :' + (pm-afternoonReservation) +' 석)');
        return false; // 함수 종료
    }
    else if(head == 0){
    	alert("좌석을 선택해주세요");
    }
    else{
		var headtrim = headtrimm.trim();
		
		headtrim = headtrim.replace(/^0+/, '');
		
		var headinput = document.createElement('input');
		headinput.setAttribute("type", "hidden");
		headinput.setAttribute("name", "head");
		headinput.setAttribute("value", headtrim);
		
		form.appendChild(headinput);
		
   		totalPriceInput.value = totalPrice;
   		formSubmitted = true;
    	form.submit();
    }    
}
window.addEventListener('beforeunload', function(event) {
	  if (!formSubmitted) {
	    window.opener.postMessage('windowClosed', '*');
	  }
});

function NotReload(){
    if( (event.ctrlKey == true && (event.keyCode == 78 || event.keyCode == 82)) || (event.keyCode == 116) ) {
        event.keyCode = 0;
        event.cancelBubble = true;
        event.returnValue = false;
    } 
}
document.onkeydown = NotReload;


function onlyNumber(obj) {
	  obj.value = obj.value.replace(/[^0-9]/g, "");
	}