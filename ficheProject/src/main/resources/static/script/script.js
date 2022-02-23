/**
 * Script JavaScript Anita
 */
$(document).ready(function() {
	var showPass = 0;
	$('.btn-show-pass').on('click', function() {
		if (showPass == 0) {
			$(this).next('input').attr('type', 'text');
			$(this).find('i').removeClass('mdi-eye');
			$(this).find('i').addClass('mdi-eye-off');
			showPass = 1;
		}
		else {
			$(this).next('input').attr('type', 'password');
			$(this).find('i').addClass('mdi-eye');
			$(this).find('i').removeClass('mdi-eye-off');
			showPass = 0;
		}

	});
});

$(document).ready(function(){
	  $("#myInput").on("keyup", function() {
		    var value = $(this).val().toLowerCase();
		    $("#myList li").filter(function() {
		      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		    });
		  });
		});

