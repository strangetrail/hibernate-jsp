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

function listchats (user_1, user_2) {
	$.ajax({
		url : 'getchat',
		data : {
			action: "list",
			user_1: user_1,
			user_2: user_2
		},
		success : function(responseJSON) {
			var chatArray = responseJSON;
			var chatbox = document.getElementById('chatbox');
			chatbox.innerHTML = "";
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

function selectchat(login) {
	var currentLogin = getCookie("login");
	listchats(currentLogin, login);
}

function send_test () {
	var newcontent = document.createElement('div');
	newcontent.classList.add('question');
	newcontent.innerHTML = 'question';
	var chatbox = document.getElementById('chatbox');
	chatbox.appendChild(newcontent);
}