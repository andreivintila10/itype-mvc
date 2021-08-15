// TYPING MECANICS SCRIPT

$(document).ready(function() {

    // Elements
    var keyboard, clock;
    var wordScreen, wordsList, letters;
    var buttonStart, buttonReset, buttonRestart;
    var wpmEl, characterEl, myForm;

    // Global variables
    var isRunning, isShowingResults;
    var minutes, seconds;
    var words;
    var current, position, noOfWords;
    var wpm;

    function initElements() {
        keyboard = document.getElementById("keyboard");
        clock = document.getElementById("timer");
        wordScreen = document.getElementById("current-word");
        wordsList = document.getElementsByClassName("word");
        buttonStart = document.getElementById("btn-start");
        buttonReset = document.getElementById("btn-reset");
        buttonRestart = document.getElementById("btn-restart");
        controlButtons = document.getElementsByClassName("btn-type-control");

        wpmEl = document.getElementById("wpmField");
        characterEl = document.getElementById("lastCharacterField");
        myForm = document.getElementById("typeSessionForm");
    }

    function initGlobalVariables() {
        isRunning = false;
        minutes = 1;
        seconds = 0;
        words = new Array();
        noOfWords = words.length;
        position = 0;
        current = 0;
        wpm = 0;

        isShowingResults();
    }

    function init() {
        initElements();
        initGlobalVariables();
    }

    init();

    // Events
    document.addEventListener('keydown', run, false);
    buttonStart.addEventListener('click', startButtonClick, false);

    function startButtonClick() {
        if (!isRunning)
            start();
    }

    function showButton(button) {
        for (let index = 0; index < controlButtons.length; index++)
            controlButtons[index].style.display = "none";
        button.style.display = "inline-block";
    }

    function start() {
        if (isShowingResults)
            return false;

        getWords();

        isRunning = true;
        timer = setInterval(myTimer, 1000);
        displayWord(words[current]);
        updateVirtualKeyboard(words[current][position]);

        showButton(buttonReset);
        
        return true;
    }

    function end() {
        clearInterval(timer);
        wpm = wpm + position;
        if (wpm > 0)
            wpm /= 5.0;
        isRunning = false;

        console.log("Words per minute: " + wpm);
        wordScreen.innerHTML = wpm + " WPM";
        wordScreen.style.color = "green";

        document.removeEventListener('keydown', run, false);
        buttonStart.removeEventListener('click', startButtonClick, false);

        showButton(buttonRestart);

        wpmEl.value = wpm;
        characterEl.value = keyboard.src;
        myForm.submit();
    }

    function getWords() {
        for (let index = 0; index < wordsList.length; index++)
            words.push(wordsList[index].innerHTML);
    }

    function updateLetterColor(position, color) {
        letters[position].style.color = color;
    }

    function getLetterColor(position) {
        return letters[position].style.color;
    }

    function displayWord(word) {
        wordScreen.innerHTML = "";

        let letter;
        for (let index = 0; index < word.length; index++) {
            letter = document.createElement("div");
            letter.innerHTML = word[index];
            letter.classList.add("letter");
            wordScreen.appendChild(letter);
        }

        letters = document.getElementsByClassName("letter");
    }

    function isValidWord() {
        for (let index = 0; index < position; index++)
            if (getLetterColor(index) === 'red')
                return false;

        return true;
    }

    function run(e) {
        var x = e.which || e.keyCode;
        var y = String.fromCharCode(x);

        if (isRunning) {
            e.preventDefault();

            if (x === 8 && position > 0) {
                position--;

                if (isValidWord())
                    updateVirtualKeyboard(words[current][position]);

                updateLetterColor(position, "black");
            }
            else if (x === 32 && position === words[current].length) {
                if (isValidWord()) {
                    // Wpm is the size of the word + 1 for the space key press.
                    wpm += words[current].length + 1;
                    current++;
                    // Less likely however, theoretically possible.
                    if (current === noOfWords) {
                        end();
                    }
                    else {
                        position = 0;
                        displayWord(words[current]);
                    }

                    updateVirtualKeyboard(words[current][position]);
                }
            }
            else if (x >= 32 && x <= 126 && position < words[current].length) {
                if (y.toLowerCase() === words[current][position]) {
                    updateLetterColor(position, "#00cc00");
                }
                else {
                    updateLetterColor(position, "red");
                    updateVirtualKeyboard("BACKSPACE");
                }
                position++;

                if (isValidWord()) {
                    if (position < words[current].length)
                        updateVirtualKeyboard(words[current][position]);
                    else
                        updateVirtualKeyboard("SPACE");
                }
            }
        }
        else if (x === 13) {
            start();
        }
    }

    function updateVirtualKeyboard(key) {
        keyboard.src = "/my-itype/images/keyboard/keyboard" + key.toUpperCase() + ".png";
    }

    // Timer logic
    function twoDigits(number) {
        if (number <= 9)
            return "0" + number;

        return number;
    }

    function setTime(minutes, seconds) {
        clock.innerHTML = twoDigits(minutes) + ':' + twoDigits(seconds);
    }

    function myTimer() {
        if (seconds > 0) {
            seconds--;
            setTime(minutes, seconds);
        }
        else if (minutes > 0) {
            minutes--;
            seconds = 59;
            setTime(minutes, seconds);
        }
        else {
            end();
        }
    }

    function isShowingResults() {
        let wpmString = wpmEl.value;
        if (wpmString !== "") {
            wpm = parseInt(wpmString);
            isShowingResults = true;
            showButton(buttonRestart);
            keyboard.src = "/my-itype/images/keyboard/keyboard.png";
            setTime(0, 0);
            wordScreen.innerHTML = wpmString + " WPM";
            wordScreen.style.color = "green";
        }
        else
            isShowingResults = false;
    }
});
