console.log("Welcome");

function popAlert() {
	alert("Hello");
}

var loggEl = document.getElementById("is-logged-in");
var loggedIn = document.getElementsByClassName("logged-in");
var loggedInNot = document.getElementsByClassName("not-logged-in");

function isUserLoggedIn() {
	if (loggEl.innerHTML === "true")
		return true;

	return false;
}

function updateNavbarAccountTabs() {
	if (isUserLoggedIn()) {
		for (let index = 0; index < loggedIn.length; index++)
			loggedIn[index].style.display = "inline-block";

		for (let index = 0; index < loggedInNot.length; index++)
			loggedInNot[index].style.display = "none";
	}
	else {
		for (let index = 0; index < loggedIn.length; index++)
			loggedIn[index].style.display = "none";

		for (let index = 0; index < loggedInNot.length; index++)
			loggedInNot[index].style.display = "inline-block";
	}
}

updateNavbarAccountTabs();
