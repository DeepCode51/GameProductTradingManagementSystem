function addToCart(bookId){
	$.ajax({
		url:"CartServlet?action=add",
		dataType:"json",
		async:true,
		data:{"bookId":bookId},
		type:"POST",
		success:function(data){
			$("#cart .num").html(data);
		}
			
	})
}



