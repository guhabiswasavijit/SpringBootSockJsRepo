var stompClient = null;
var stompStandAloneClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
    $("#kubectl").html("");
}

function connect() {
    var socket = new SockJS('/api/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
    var stdSocket = new SockJS('/api/stanalone-websocket');
    stompStandAloneClient = Stomp.over(stdSocket);
    stompStandAloneClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompStandAloneClient.subscribe('/topic/commands', function (kubeCommand) {
	        console.log(kubeCommand)
            //showClusterCommand(JSON.parse(kubeCommand.body).content);
        });
    });
    
}

function disconnect() {
    if (stompClient !== null && stompStandAloneClient !== null) {
        stompClient.disconnect();
        stompStandAloneClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}
function sendCommand() {
    stompStandAloneClient.send("/app/clusterAdm", {}, JSON.stringify({'command': $("#cmd").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}
function showClusterCommand(message) {
    $("#kubectl").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); sendCommand(); });
});