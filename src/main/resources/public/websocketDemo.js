/**
 * Created by aga on 25.01.17.
 */
//Establish the WebSocket connection and set up event handlers
var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chat/");
var currentChat="main";
insert("channel-items","<li>" + currentChat + "</li>")
webSocket.onmessage = function (msg) { updateChat(msg); };
webSocket.onclose = function () { alert("WebSocket connection closed") };
webSocket.onopen = function() {



//Send message if "Send" is clicked
    id("enter").addEventListener("click", function () {
        var username = id("message").value;
        var message = {type: "message", text: username, chatName: currentChat};
        sendJson(message);
        id("message").value ="";
    });

    id("chatbox").addEventListener("click", function() {
        currentChat ="chatbox";
        id("channel-items").innerHTML = "";
        insert("channel-items","<li>" + currentChat + "</li>");
        id("chat").innerHTML ="";
    });

    id("accept").addEventListener("click", function () {
        var name = id("add").value;
        var message = {type: "createRoom", text: name, chatName : currentChat};
        sendJson(message);
        id("add").value ="";

    });

    id("leave").addEventListener("click",function () {
        console.log('selected chat: ' + chat);
        currentChat ="main";
        id("channel-items").innerHTML = "";
        insert("channel-items","<li>" + currentChat + "</li>");
        id("chat").innerHTML ="";
    })


//id("wprowadz").addEventListener("keypress", function (e) {
    //  if(e.keyCode==13)
    //    document.cookie = id("wprowadz").value;
//});

    var person = prompt("Please enter your name", "Your name");
    document.cookie = "username=" + person;
    var login ={type: "login", username: person,chatName: "main"};
    sendJson(login);

    function sendJson(tosend){
        webSocket.send(JSON.stringify(tosend));
    }

}



//Send a message if it's not empty, then clear the input field
    function sendMessage(message) {
        if (message !== "") {
            webSocket.send(message);
            id("message").value = "";
        }
    }

//Update the chat-panel, and the list of connected users
    function updateChat(msg) {
        var data = JSON.parse(msg.data);

        if(data.userMessage != null && currentChat == data.chatName) {
            insert("chat", data.userMessage);
        }


        id("userlist-items").innerHTML = "";
        data.userlist.forEach(function (user) {
            insert("userlist-items", "<li>" + user + "</li>");
        });

        id("chatlist-items").innerHTML = "";
        data.chatlist.forEach(function (chat) {
            var el = insertElement("chatlist-items", "li", chat);
            el.addEventListener('click', function() {
                console.log('selected chat: ' + chat);
                currentChat = chat;
                id("channel-items").innerHTML = "";
                insert("channel-items","<li>" + currentChat + "</li>");
                id("chat").innerHTML ="";


            });


        });

    }


//Helper function for inserting HTML as the first child of an element
    function insert(targetId, message) {
        id(targetId).insertAdjacentHTML("afterbegin", message);
    }

    function insertElement(targetId, tag, text) {
        var el = document.createElement(tag);
        el.innerHTML = text;
        id(targetId).insertAdjacentElement("afterbegin", el);
        return el;
    }

//Helper function for selecting element by id
    function id(id) {
        return document.getElementById(id);
    }


