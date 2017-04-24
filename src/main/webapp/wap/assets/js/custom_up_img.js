$(document).ready(function(){
        $("#up-img-touch").click(function(){
        	$("#up-modal-frame").modal({});
        });
});
$(function() {
    'use strict';
    // 初始化
    var $image = $('#up-img-show');
    $image.cropper({
        aspectRatio: '1',
        autoCropArea:0.8,
        preview: '.up-pre-after',
        responsive:true,
    });

    // 上传图片
    var $inputImage = $('.up-modal-frame .up-img-file');
    var URL = window.URL || window.webkitURL;
    var blobURL;

    if (URL) {
        $inputImage.change(function () {
        	
            var files = this.files;
            var file;

            if (files && files.length) {
               file = files[0];

               if (/^image\/\w+$/.test(file.type)) {
                    blobURL = URL.createObjectURL(file);
                    $image.one('built.cropper', function () {
                        // Revoke when load complete
                       URL.revokeObjectURL(blobURL);
                    }).cropper('reset').cropper('replace', blobURL);
                    $inputImage.val('');
                } else {
                    window.alert('Please choose an image file.');
                }
            }
        });
    } else {
        $inputImage.prop('disabled', true).parent().addClass('disabled');
    }
    
    //绑定上传事件
    $('.up-modal-frame .up-btn-ok').on('click',function(){
    	
    	var img_src=$image.attr("src");
    	if(img_src==""){
    		CommonUtils.showAlert("没有选择上传的图片");
    		return false;
    	}
    	CommonUtils.showLoading("上传中");
    	
    	var url=$(this).attr("url");
    	//parameter
    	var parameter=$(this).attr("parameter");
    	//console.log(parameter);
    	var parame_json = eval('(' + parameter + ')');
    	var width=parame_json.width;
    	var height=parame_json.height;
    	console.log(parame_json.width);
    	console.log(parame_json.height);

    	//控制裁剪图片的大小
    	var canvas=$image.cropper('getCroppedCanvas',{width: width,height: height});
    	var data=canvas.toDataURL(); //转成base64
        $.ajax( {  
                url:base+url,  
                dataType:'json',  
                type: "POST",  
                data: {"image":data.toString()},  
                success: function(data){
                	CommonUtils.closeLoading();
                	if(data.ret && data.ret.code=="0"){
                		$("#up-modal-frame").modal('close')
                		$("#up-img-touch").attr("src",base+data.ret.fileName);   
                		$("#userProfileForm input[name='user.head_img']").val(data.ret.fileName);
                	}else{
                		CommonUtils.showAlert("上传文件失败了！");
                	}
                },
                error: function(){
                	CommonUtils.closeLoading();
                	CommonUtils.showAlert("上传文件失败了！");
               }  
         });  
    	
    });
    
    $('#up-btn-left').on('click',function(){
    	$("#up-img-show").cropper('rotate', 90);
    });
    $('#up-btn-right').on('click',function(){
    	$("#up-img-show").cropper('rotate', -90);
    });
});


function set_alert_info(content){
	$("#alert_content").html(content);
}



 
