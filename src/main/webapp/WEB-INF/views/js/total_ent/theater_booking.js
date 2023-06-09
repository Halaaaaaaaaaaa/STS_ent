
if(document.getElementById("id").value == ""){
	window.close();
	window.opener.location.href="login_form";
}

const container = document.querySelector('.container');
const seats = document.querySelectorAll('.row .seat:not(.occupied)');
const count = document.getElementById('count');
const total = document.getElementById('total');
const movieSelect = document.getElementById('movie');
const selectedSeats = [];
var formSubmitted = false;
populateUI();

let ticketPrice = +movieSelect.value;

//save selected movie index and price
function setMovieData(movieIndex, moviePrice){
    localStorage.setItem('selectedMovieIndex', movieIndex);
    localStorage.setItem('selectedMoviePrice', moviePrice);
}

//update total and count
function updateSelectedCount() {
    const selectedSeats = document.querySelectorAll('.row .seat.selected');

    const seatsIndex = [...selectedSeats].map(seat => [...seats].indexOf(seat));

    localStorage.setItem('selectedSeats', JSON.stringify(seatsIndex));
    
    const selectedSeatsCount = selectedSeats.length;

    count.innerText = selectedSeatsCount;
    total.innerText = selectedSeatsCount * ticketPrice;
    
   
}
//get data from local storage & populate UI
function populateUI() {
    const selectedSeats = JSON.parse(localStorage.getItem('selectedSeats'));
    
    if (selectedSeats !== null && selectedSeats.length > 0) {
        seats.forEach((seat, index) => {
            if (selectedSeats.indexOf(index) > -1) {
                seat.classList.add('selected');
                seat.classList.remove('selected');
            }
        });
    } else {
        seats.forEach((seat) => {
            seat.classList.remove('selected');
        });
    }

    const selectedMovieIndex = localStorage.getItem('selectedMovieIndex');
    
    if (selectedMovieIndex !== null) {
        movieSelect.selectedIndex = selectedMovieIndex;
    }
}

//movie select event
movieSelect.addEventListener('change', e => {
    ticketPrice = +e.target.value;
    setMovieData(e.target.selectedIndex, e.target.value)
    updateSelectedCount();
});

//seat click event
container.addEventListener('click', e => {
    if (e.target.classList.contains('seat') && !e.target.classList.contains('occupied')) {
        const seatIdentifier = e.target.querySelector('input').value;	
        const isSelected = e.target.classList.contains('selected');
        
        // Check if the seat is already selected
        if (isSelected) {
            e.target.classList.remove('selected');
            // Remove the seat from the selectedSeats array
            const seatIndex = selectedSeats.indexOf(seatIdentifier);
            if (seatIndex !== -1) {
                selectedSeats.splice(seatIndex, 1);
            }
        } else {
            e.target.classList.add('selected');
            // Add the seat to the selectedSeats array
            selectedSeats.push(seatIdentifier);
            
        }

        // Display the updated selected seats information in the console

        updateSelectedCount();
    }
});

function go_save(){
	
	const selectedSeatsCount = selectedSeats.length;
	
	if(selectedSeatsCount == 0){
		alert("좌석을 선택해 주세요");
		return false;
	}
	else {
		var form = document.getElementById("thbooking");
		formSubmitted = true;
        // 예약자 수
        const hiddenInput1 = document.createElement("input");
        hiddenInput1.type = "hidden";
        hiddenInput1.name = "head";
        hiddenInput1.value = selectedSeatsCount;
        form.appendChild(hiddenInput1);
        
        // 좌석에 대한 정보 배열
        const hiddenInput2 = document.createElement("input");
        hiddenInput2.type = "hidden";
        hiddenInput2.name = "seat";
        hiddenInput2.value = JSON.stringify([...selectedSeats]);
        form.appendChild(hiddenInput2);
        
        form.submit();
        
	}
}

//initial count and total set
updateSelectedCount();

window.addEventListener('beforeunload', function(event) {
	  if (!formSubmitted) {
	    // Send a message to the opener window indicating that it is being closed
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