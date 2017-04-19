<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String baseUri = request.getContextPath();	
	String pUserCode = request.getParameter("puserCode")==null?"":request.getParameter("puserCode");
%>

<div class="tpl-content-wrapper">           
            <ol class="am-breadcrumb">
                <li><a href="#" class="am-icon-home">首页</a></li>
                <li><a href="#">团队管理</a></li>
                <li class="am-active">用户注册</li>
            </ol>
            <div class="tpl-portlet-components">
                <div class="portlet-title">
                    <div class="caption font-green bold">
                        <span class="am-icon-code"></span>注册信息
                    </div>
                </div>

                <div class="tpl-block">

                    <div class="am-g">
                        <div class="tpl-form-body tpl-form-line">
                            <form class="am-form tpl-form-line-form" id="registerForm">
                                <div class="am-form-group">
                                    <label for="user-email" class="am-u-sm-4 am-form-label">用户账号 </label>
                                    <div class="am-u-sm-8">
                                        <input type="text" class="am-form-field tpl-form-no-bg" db_field="user.user_code"  name="user.user_code" placeholder="请输入用户账号" data-required="true" data-descriptions="user.user_code" data-describedby="user.user_code-description"/> 
                                        <small id="user.user_code-description"></small>                                       
                                    </div>
                                </div> 
                                 <div class="am-form-group">
                                    <label for="user-email" class="am-u-sm-4 am-form-label">登录密码 </label>
                                    <div class="am-u-sm-8">
                                        <input type="password" class="am-form-field tpl-form-no-bg" db_field="user.password"  name="user.password" placeholder="请输入登录密码" data-pattern="[0-9]{6,6}" data-required="true" data-descriptions="user.password" data-describedby="user.password-description"/>  
                                        <small id="user.password-description"></small>                                        
                                    </div>
                                </div>
                                 <div class="am-form-group">
                                    <label for="user-email" class="am-u-sm-4 am-form-label">确认登录密码 </label>
                                    <div class="am-u-sm-8">
                                        <input type="password" class="am-form-field tpl-form-no-bg"  name="user.confirm_password" placeholder="请输入确认登录密码" data-pattern="[0-9]{6,6}" data-required="true" data-descriptions="user.confirm_password" data-describedby="user.confirm_password-description" data-conditional="confirmPwd"/>                                        
                                    	<small id="user.confirm_password-description"></small>  
                                    </div>
                                </div>
                                 <div class="am-form-group">
                                    <label for="user-email" class="am-u-sm-4 am-form-label">交易密码 </label>
                                    <div class="am-u-sm-8">
                                        <input type="password" class="am-form-field tpl-form-no-bg" db_field="user.trans_pwd"  name="user.trans_pwd" placeholder="请输入交易密码" data-pattern="[0-9]{6,6}" data-required="true" data-descriptions="user.trans_pwd" data-describedby="user.trans_pwd-description"/>                                        
                                    	<small id="user.trans_pwd-description"></small>  
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <label for="user-email" class="am-u-sm-4 am-form-label">确认交易密码 </label>
                                    <div class="am-u-sm-8">
                                        <input type="password" class="am-form-field tpl-form-no-bg"   name="user.confirm_trans_pwd" placeholder="请输入确认交易密码" data-pattern="[0-9]{6,6}" data-required="true" data-descriptions="user.confirm_trans_pwd" data-describedby="user.confirm_trans_pwd-description" data-conditional="confirmTransPwd"/>                                        
                                    	<small id="user.confirm_trans_pwd-description"></small>  
                                    </div>
                                </div>   
                                <div class="am-form-group">
                                    <label for="user-name" class="am-u-sm-4 am-form-label">手机号码</label>
                                    <div class="am-u-sm-8">
                                        <input type="text" class="tpl-form-input" data-validate="phone" db_field="user.user_phone" name="user.user_phone" placeholder="请输入手机号码，为必填" data-required="true" data-descriptions="user.user_phone" data-describedby="user.user_phone-description">                                        
                                    	<small id="user.user_phone-description"></small>  
                                    </div>
                                </div>
								<div class="am-form-group">
                                    <label for="user-email" class="am-u-sm-4 am-form-label">姓名 </label>
                                    <div class="am-u-sm-8">
                                        <input type="text" class="am-form-field tpl-form-no-bg" db_field="user.name"  name="user.name" placeholder="请输入用户账号" data-required="true" data-descriptions="user.name" data-describedby="user.name-description"/>                                        
                                    	<small id="user.name-description"></small>  
                                    </div>
                                </div>                              
                                 <div class="am-form-group">
                                    <label for="user-email" class="am-u-sm-4 am-form-label">接点人账号</label>
                                    <div class="am-u-sm-8">
                                        <input type="text" class="am-form-field tpl-form-no-bg" db_field="user.pid" value="<%=pUserCode %>" name="user.pid" placeholder="请输入接点人账号" data-required="true" data-descriptions="user.pid" data-describedby="user.pid-description"/>                                        
                                    	<small id="user.pid-description"></small>  
                                    </div>
                                </div>
                                <div class="am-form-group">
                                    <div class="am-u-sm-8 am-u-sm-push-3">
                                        <input type="submit" id="registerSubmit" class="am-btn am-btn-primary tpl-btn-bg-color-success " value="提交"></input>                                    	
                                    </div>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>


            </div>










        </div>
        
      <script language="javascript" type="text/javascript" >
			$(function() {	
				$("#registerForm").mvalidate({
			            type:2,
			            onKeyup:true,
			            sendForm:false,
			            firstInvalidFocus:true,
			            valid:function(event,options){
			                //点击提交按钮时,表单通过验证触发函数
			                 var params = CommonUtils.getParam("registerForm",false);
			                 CommonUtils.showLoading();
							 CommonUtils.invokeAsyncAction(base+'/Sys/User!registerUser.do', params, function (reply) {           
				  	            CommonUtils.closeLoading();
								if ((reply || '') != '') {
				  	               var code = reply._code;               
				  	               if (code == '0') {  
				  	            	   CommonUtils.showAlert('操作成功!');
				  	                      	                   
				  	               } else  {
				  	            	  CommonUtils.showAlert(reply._msg);
				  	             }              
				  	           } else  {
				  	        	      CommonUtils.showAlert('操作失败!');
				  	           }
				  	         });
					 
			                event.preventDefault();
			                return false;
			            },
			            conditional:{
			                confirmPwd:function(val){			                	
			                	return (val==$("#registerForm input[name='user.password']").val()) ? true :false;
			                },
			                confirmTransPwd:function(val){			                    
			                    return (val==$("#registerForm input[name='user.trans_pwd']").val()) ? true :false; 
			                },
			            },
			            descriptions:{
			                "user.user_code":{
			                    required : '<div class="field-invalidmsg">请输入用户账号</div>',
			                    valid : '<div class="field-validmsg">验证通过</div>'
			                },
			                "user.password" : {
			                    required : '<div class="field-invalidmsg">请输入登录密码</div>',
			                    pattern : '<div class="field-invalidmsg">密码格式不对，必须为6位数字</div>',
			                    valid : '<div class="field-validmsg">验证通过</div>'
			                },
			                "user.confirm_password":{
			                     required : '<div class="field-invalidmsg">请输入确认登录密码</div>',
			                     conditional : '<div class="field-validmsg">两次登录密码不一样</div>',
			                     pattern : '<div class="field-invalidmsg">密码格式不对，必须为6位数字</div>',
			                     valid : '<div class="field-validmsg">验证通过</div>'
			                },
			                "user.trans_pwd" : {
			                    required : '<div class="field-invalidmsg">请输入交易密码</div>', 
			                    pattern : '<div class="field-invalidmsg">密码格式不对，必须为6位数字</div>',
			                    valid : '<div class="field-validmsg">验证通过</div>'
			                },
			                "user.confirm_trans_pwd":{
			                    required : '<div class="field-invalidmsg">请输入确认交易密码</div>',
			                    conditional : '<div class="field-invalidmsg">两次交易密码不一样</div>',
			                    pattern : '<div class="field-invalidmsg">密码格式不对，必须为6位数字</div>',
			                    valid : '<div class="field-validmsg">验证通过</div>'
			                },
			                "user.user_phone":{
			                    required : '<div class="field-invalidmsg">请输入手机号码</div>',
			                    pattern : '<div class="field-invalidmsg">您输入的手机号码格式不正确</div>',
			                    valid : '<div class="field-validmsg">验证通过</div>'
			                },
			                "user.name":{
			                    required : '<div class="field-invalidmsg">请输入用户姓名</div>',
			                    valid : '<div class="field-validmsg">验证通过</div>'
			                },
			                "user.pid":{
			                    required : '<div class="field-invalidmsg">请输入接点人账号</div>',
			                    valid : '<div class="field-validmsg">验证通过</div>'
			                }
			            }
			     });
			   
				
				// $("#registerSubmit").bind("click",function(){
				//	 
				// });
				 
			});
		</script>	