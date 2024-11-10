$.ajax({
	url:"GetCatalog",
	dataType:"json",
	async:true,
	data:{},
	type:"POST",
	success:function(data){
		//分类信息
		if(data.catalog!=null){
			$.each(data.catalog,function(i,n){
				$("#catalog-list").append("<li><a href='BookList?catalogId="+n.catalogId+"'>"+n.catalogName+"("+n.catalogSize+")</a></li>");
			})
		}
		
	}
		
})


