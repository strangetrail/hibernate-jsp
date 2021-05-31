/**
 * 
 */
 


function showpage (val) {
	$.ajax({
		url : 'gettable',
		data : {
			page: val
		},
		success : function(responseText) {
			$('#ajaxTable').html(responseText);
		}
	});
	document.getElementById('listindex').value = val;
}

function listchats (user_1, user_2, chat_length) {
	$.ajax({
		url : 'getchat',
		data : {
			action: "list",
			user_1: user_1,
			user_2: user_2,
			chat_length: chat_length
		},
		success : function(responseJSON) {
			var chatArray = responseJSON;
			var chatbox = document.getElementById('chatbox');
			//chatbox.innerHTML = "";
			for (i=0; i<chatArray.chats.length; i++) {
				var element = chatArray.chats[i];
				var sibling = document.createElement("div");
				if (element.a === undefined && typeof element.a == 'undefined') {
					sibling.classList.add('question');
					sibling.innerHTML = element.q;
				}
				else {
					sibling.classList.add('answer');
					sibling.innerHTML = element.a;
				}
				chatbox.appendChild(sibling);
			}
		}
	});
}

function createCookie(name,value,days) {
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 *1000));
        var expires = "; expires=" + date.toGMTString();
    } else {
        var expires = "";
    }
    document.cookie += ";" + name + "=" + value + expires + "; path=/";
}

function getCookie(cname) {
  var name = cname + "=";
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(';');
  for(var i = 0; i <ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

function showuserlist () {
var x = document.getElementById("userlist");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

var globalCheckSign = 0;

function selectchat(login) {
	var currentLogin = getCookie("login");
	var chatbox = document.getElementById('chatbox');
	if (currentLogin != "") {
		chatbox.innerHTML = "";
		listchats(currentLogin, login, "0");
		clearInterval(globalCheckSign);
	 	globalCheckSign = setInterval(checkNewMessages, 2000);
 	}
 	document.getElementById("userlist").style.display="none";
}

function checkNewMessages() {
	var currentlogin = getCookie("login");
	var recipient = getCookie("recipient");
	listchats(currentlogin, recipient, getCookie("chat_length"));
}

function clean_all() {
	var currentlogin = getCookie("login");
	var recipient = getCookie("recipient");
	var chatbox = document.getElementById('chatbox');
	chatbox.innerHTML = "";
		$.ajax({
			url : 'getchat',
			data : {
				action: 'delete',
				user_1: currentlogin,
				user_2: recipient
			},
			success : function(responseText) {
				
			}
		});
}

function send_test () {
	if (globalCheckSign != 0) {
		var message = document.getElementById('chatinput').value;
		/*var newcontent = document.createElement('div');
		newcontent.classList.add('question');
		newcontent.innerHTML = message;
		var chatbox = document.getElementById('chatbox');
		chatbox.appendChild(newcontent);*/
		$.ajax({
			url : 'getchat',
			data : {
				action: 'add',
				message: message
			},
			success : function(responseText) {
				
			}
		});
	}
}