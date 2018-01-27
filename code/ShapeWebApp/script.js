/**
 * Created by andrewlewis on 11/9/17.
 */

var firstEmotion, secondEmotion;
var emotions = ["hoogabooga", "happy", "sad", "angry", "scared"];
var emotionMap = {
    1 : "Angry",
    2 : "Happy",
    3 : "Sad",
    4 : "Scared"
};

function setup(){

    firstEmotion = "none";
    secondEmotion = "none";
    // Get initial value for wall from server
    getCurrentConfig();
}

function selectEmotion(btnID){
    console.log("in 'SelectEmotion'");
    if(firstEmotion == "none"){
        firstEmotion = emotions[btnID];

        switch(btnID){
            case 1:
                document.getElementById("emotionSelector").innerHTML =
                    "<a href=\"#\" id=\"happy\" class=\"list-group-item list-group-item-disabled\" onclick=selectEmotion(1)>Happy<span class=\"badge\" id=\"happyBadge\"></span></a>" +
                    "<a href=\"#\" id=\"sad\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(2)>Sad<span class=\"badge\" id=\"sadBadge\"></span></a>" +
                    "<a href=\"#\" id=\"angry\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(3)>Angry<span class=\"badge\" id=\"angryBadge\"></span></a>" +
                    "<a href=\"#\" id=\"scared\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(4)>Scared<span class=\"badge\" id=\"scaredBadge\"></span></a>";
                document.getElementById("happyBadge").innerHTML = 1;
                break;

            case 2:
                document.getElementById("emotionSelector").innerHTML =
                    "<a href=\"#\" id=\"happy\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(1)>Happy<span class=\"badge\" id=\"happyBadge\"></span></a>" +
                    "<a href=\"#\" id=\"sad\" class=\"list-group-item list-group-item-disabled\" onclick=selectEmotion(2)>Sad<span class=\"badge\" id=\"sadBadge\"></span></a>" +
                    "<a href=\"#\" id=\"angry\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(3)>Angry<span class=\"badge\" id=\"angryBadge\"></span></a>" +
                    "<a href=\"#\" id=\"scared\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(4)>Scared<span class=\"badge\" id=\"scaredBadge\"></span></a>";
                document.getElementById("sadBadge").innerHTML = 1;
                break;

            case 3:
                document.getElementById("emotionSelector").innerHTML =
                    "<a href=\"#\" id=\"happy\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(1)>Happy<span class=\"badge\" id=\"happyBadge\"></span></a>" +
                    "<a href=\"#\" id=\"sad\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(2)>Sad<span class=\"badge\" id=\"sadBadge\"></span></a>" +
                    "<a href=\"#\" id=\"angry\" class=\"list-group-item list-group-item-disabled\" onclick=selectEmotion(3)>Angry<span class=\"badge\" id=\"angryBadge\"></span></a>" +
                    "<a href=\"#\" id=\"scared\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(4)>Scared<span class=\"badge\" id=\"scaredBadge\"></span></a>";
                document.getElementById("angryBadge").innerHTML = 1;
                break;

            case 4:
                document.getElementById("emotionSelector").innerHTML =
                    "<a href=\"#\" id=\"happy\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(1)>Happy<span class=\"badge\" id=\"happyBadge\"></span></a>" +
                    "<a href=\"#\" id=\"sad\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(2)>Sad<span class=\"badge\" id=\"sadBadge\"></span></a>" +
                    "<a href=\"#\" id=\"angry\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(3)>Angry<span class=\"badge\" id=\"angryBadge\"></span></a>" +
                    "<a href=\"#\" id=\"scared\" class=\"list-group-item list-group-item-disabled\" onclick=selectEmotion(4)>Scared<span class=\"badge\" id=\"scaredBadge\"></span></a>";
                document.getElementById("scaredBadge").innerHTML = 1;
                break;
        }

        var displayValue = document.getElementById("ValueToSend");
        displayValue.innerHTML = "I am feeling " + firstEmotion;
    }
    else {
        if(emotions[btnID] == firstEmotion){
            return;
        }

        // Remove '2' badge from the previous selected emotion
        if(secondEmotion != "none"){
            var badgeID = secondEmotion + "Badge";
            console.log(badgeID);
            document.getElementById(badgeID).innerHTML = "";
        }

        // Set secondEmotion to the newly selected emotion
        secondEmotion = emotions[btnID];

        switch(btnID){
            case 1:
                document.getElementById("happyBadge").innerHTML = "2";
                break;

            case 2:
                document.getElementById("sadBadge").innerHTML = "2";
                break;

            case 3:
                document.getElementById("angryBadge").innerHTML = "2";
                break;

            case 4:
                document.getElementById("scaredBadge").innerHTML = "2";
                break;
        }

        document.getElementById("EmotionSeekBar").innerHTML =
            "<div style=\"display: inline-block\"><p id=\"Emotion1\">" + firstEmotion + "</p></div>" +
            "<div class=\"compoundFeeling\" style=\"display: inline-block\">" +
            "<input type=\"range\" id=\"feelingSeekBar\" value=\"50\" min=\"0\" max=\"100\" step=\"50\" id=\"feelingSeekBar\">" +
            "</div>" +
            "<div style=\"display: inline-block\"><p id=\"Emotion2\">" + secondEmotion + "</p></div>";


        displayValue = document.getElementById("ValueToSend");
        displayValue.innerHTML = "I am feeling " + firstEmotion + " and " + secondEmotion;

        var feelingScroller = document.querySelector("#feelingSeekBar");

        feelingScroller.addEventListener("input", scrollFeeling, false);



    }
}

function sendRequest() {

    if(firstEmotion == "none"){
        console.log("first emotion is none");
        return;
    }

    var valueToSend;

    if(secondEmotion == "none"){
        console.log("second emotion is none");
        valueToSend = emotions.indexOf(firstEmotion);
    }
    // There is a first and second emotion selected.
    // Based on the value of the slider we choose firstEmotion, secondEmotion, or both
    else{
        var firstEmotionValue = emotions.indexOf(firstEmotion);
        var secondEmotionValue = emotions.indexOf(secondEmotion);

        var slider = document.getElementById("feelingSeekBar");

        if(slider.value == 0){
            valueToSend = firstEmotionValue;
        }
        else if(slider.value == 100){
            valueToSend = secondEmotionValue;
        }
        // Otherwise need to send a value that represents both feelings
        else{
            // If emotions selected are angry and scared, manually set value to 5 (For Testing)
            if(firstEmotionValue == 3 && secondEmotionValue == 4 ||
                firstEmotionValue == 4 && secondEmotionValue == 3){
                valueToSend = 5;
            }
            // If emotions is set to sad and angry, manually set to 6 (For Testing)
            else if(firstEmotionValue == 2 && secondEmotionValue == 3 ||
                firstEmotionValue == 3 && secondEmotionValue == 2){
                valueToSend = 6;
            }
            else{
                valueToSend = ( firstEmotionValue * 10 ) + secondEmotionValue;
            }

        }
    }

    console.log("value to send = " + valueToSend);

    var wallConfig = {"currentWall" : 0, "deviceID" : 1};
    wallConfig.currentWall = valueToSend;
    var xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://andrewlewis.pythonanywhere.com/currentWall/", true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(JSON.stringify(wallConfig));
    getCurrentConfig();
}

function getCurrentConfig() {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "http://andrewlewis.pythonanywhere.com/currentWall/", true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var st = document.getElementById("status");
            st.textContent = "Current Wall Configuration: " + xhttp.responseText;
        }
    };
    xhttp.send( null );
}

function reset(){

    // Reset the values for the first and second emotion to default values
    firstEmotion = "none";
    secondEmotion = "none";

    // Delete the displayed choice from the screen
    var displayValue = document.getElementById("ValueToSend");
    displayValue.innerHTML = "";

    // Get rid of disabled choices,
    // Delete number '1' and '2' badges,
    // Remove Seek Bar from screen
    document.getElementById("emotionSelector").innerHTML =
        "<a href=\"#\" id=\"happy\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(1)>Happy<span class=\"badge\" id=\"happyBadge\"></span></a>" +
        "<a href=\"#\" id=\"sad\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(2)>Sad<span class=\"badge\" id=\"sadBadge\"></span></a>" +
        "<a href=\"#\" id=\"angry\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(3)>Angry<span class=\"badge\" id=\"angryBadge\"></span></a>" +
        "<a href=\"#\" id=\"scared\" class=\"list-group-item list-group-item-action\" onclick=selectEmotion(4)>Scared<span class=\"badge\" id=\"scaredBadge\"></span></a>";
    document.getElementById("happyBadge").innerHTML = "";
    document.getElementById("sadBadge").innerHTML = "";
    document.getElementById("angryBadge").innerHTML = "";
    document.getElementById("scaredBadge").innerHTML = "";
    document.getElementById("EmotionSeekBar").innerHTML = "";

}



function scrollFeeling(event) {

    var slider = document.getElementById("feelingSeekBar");
    var displayValue = document.getElementById("ValueToSend");

    if(slider.value == 0){
        displayValue.innerHTML = "I am feeling " + firstEmotion;
    }
    else if (slider.value == 100){
        displayValue.innerHTML = "I am feeling " + secondEmotion;
    }
    else{
        displayValue.innerHTML = "I am feeling " + firstEmotion + " and " + secondEmotion;

    }
}

function getStatistics() {
    var xhttp = new XMLHttpRequest();
    xhttp.open("GET", "http://andrewlewis.pythonanywhere.com/statistics/", true);
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var result = JSON.parse(JSON.parse(xhttp.responseText));
            var st = document.getElementById("status");
            st.textContent = "Server Status: Connected";
            st.className = "text-success";
            st = document.getElementById("mLMorning");
            st.textContent = "Morning: " + emotionMap[result["morning"]];
            st = document.getElementById("mLAfternoon");
            st.textContent = "Afternoon: " + emotionMap[result["afternoon"]];
            st = document.getElementById("mLEvening");
            st.textContent = "Evening: " + emotionMap[result["evening"]];
            st = document.getElementById("mLNight");
            st.textContent = "Night: " + emotionMap[result["night"]];
        }
    };
    xhttp.send(null);
}


