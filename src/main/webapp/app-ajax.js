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

function send_test () {
	var newcontent = document.createElement('div');
	var chatbox = document.getElementById('chatbox');
	chatbox.appendChild(newcontent);
}