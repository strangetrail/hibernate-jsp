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
