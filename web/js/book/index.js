$.ajax({
	url:"ShopIndex",
	dataType:"json",
	async:true,
	data:{},
	type:"POST",
	success:function(data){
		datalist(data);
	}
		
})

function datalist(data){
	
	
	//推荐书籍
	if(data.recBooks!=null){
		$.each(data.recBooks,function(i,n){ 
			var tag="<li class='col-md-3'><div class='list'>" +
			"<a href='bookdetail?bookId="+n.bookId+"'><img class='img-responsive' src='"+n.upLoadImg.imgSrc+"'/></a>"+
			"<div class='proinfo'><h2><a class='text-center' href='bookdetail?bookId="+n.bookId+"'>"+n.bookName+"</a></h2>"+
			"<p><i>￥"+n.price+"</i><a class='btn btn-danger btn-xs' href='javascript:void(0)' onclick='addToCart("+n.bookId+")' " +
				"data-toggle='modal' data-target='.bs-example-modal-sm'>加入购物车</a></p></div></div></li>";
			
			$("#recBooks ul").append(tag);
		})
	}
	
	//新增加的书
	if(data.newBooks!=null){
		$.each(data.newBooks,function(i,n){ 
			var tag="<li class='col-md-3'><div class='list'>" +
			"<a href='bookdetail?bookId="+n.bookId+"'><img class='img-responsive' src='"+n.upLoadImg.imgSrc+"'/></a>"+
			"<div class='proinfo'><h2><a class='text-center' href='bookdetail?bookId="+n.bookId+"'>"+n.bookName+"</a></h2>"+
			"<p><i>￥"+n.price+"</i><a class='btn btn-danger btn-xs' href='javascript:void(0)' onclick='addToCart("+n.bookId+")' " +
				"data-toggle='modal' data-target='.bs-example-modal-sm'>加入购物车</a></p></div></div></li>";
			
			$("#newBooks ul").append(tag);
			
		})
	}
	
	
}
