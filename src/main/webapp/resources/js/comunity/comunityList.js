$(document).ready(function(){
	
	$(".comu_info").click(function(){
		event.preventDefault();
		var targetPage = $(this).attr("href");
		self.location.href = "./comuRead?page=" + targetPage;
	});
	
});