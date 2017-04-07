

/**
 * ��������������
 */
(function($){
$.extend($.fn.combobox, {
	oldParseOptions:$.fn.combobox.parseOptions,
	parseOptions:function(element){
		var t=$(element);
		return $.extend({},$.fn.combobox.oldParseOptions(element),
			{code:t.attr("code"),
			valueField:t.attr("valueField")!=null?t.attr("valueField"):"id",
			textField:t.attr("textField")!=null?t.attr("textField"):"text"});
	}
});

/**
 * ��չdatagrid.columns������code���ԣ�����ֵ
 */
$.extend($.fn.datagrid.defaults.view, {
	onBeforeRender:function(target, rows){
		var opts = $.data(target, 'datagrid').options;
		var columns = opts.columns;
		
		for ( var i = 0; i < columns.length; i++) {
			for ( var j = 0; j < columns[i].length; j++) {
				if (columns[i][j].code && !columns[i][j].formatter) {
					columns[i][j].formatter = function (value,rowData,rowIndex){
						var valueName = '';
						var column = this;
						if (!column.codeList) {
							var checkCallBack = function(reply) {
								data = reply.result;
								column.codeList = data;
							}
							Service.sync('sys', 'getStaticData',{code:column.code}, checkCallBack);
						}
						
						//����ֵ
						for ( var k = 0; column.codeList && k < column.codeList.length;k++) {
							if(column.codeList[k].id==value){
								valueName = column.codeList[k].text;
								break;
							}
						}
						
						return valueName;
					};
				}
			}
		}
	}
});

/**
 * datagridĬ��ֵ
 */
$.extend($.fn.datagrid.defaults,{
	fit:true
})

/**
 * ��������չ����
 */
$.extend($.fn.form.methods, {
	/**
	 * ȡ�ñ�����
	 * @param jq
	 * @returns ���ؼ���JSON����
	 */
	getData:function(jq){
		var data = {};
		$("input,select,textarea",jq).each(function(){
    		if(this.name)
    			data[this.name] = this.value;
    	});
		return data;
	},
	/**
	 * ������Ϊ���ɱ༭
	 * @param jq
	 */
	disable:function(jq){
		$("label",jq).attr("disabled",true);
		$(".easyui-validatebox",jq).validatebox("disable");
		$(".easyui-numberbox",jq).numberbox("disable");
		$(".easyui-datebox",jq).datebox("disable");
		$(".easyui-combobox",jq).combobox("disable");
		$(".easyui-combotree",jq).combotree("disable");
	},
	/**
	 * ������Ϊ�ɱ༭
	 * @param jq
	 */
	enable:function(jq){
		$("label",jq).removeAttr("disabled");
		$(".easyui-validatebox",jq).validatebox("enable");
		$(".easyui-numberbox",jq).numberbox("enable");
		$(".easyui-datebox",jq).datebox("enable");
		$(".easyui-combobox",jq).combobox("enable");
		$(".easyui-combotree",jq).combotree("enable");
	}
});

/**
 * ��չvalidatebox���Ƿ�ɱ༭
 */
$.extend($.fn.validatebox.methods, {
	/**
	 * �����Ƿ�ɱ༭
	 * @param jq
	 * @param disabled
	 */
	setDisable:function(jq,disabled){
		var opt=$.data(jq,"validatebox").options;
		if(disabled){
			opt.disabled=true;
			$(jq).attr("disabled",true);
		}else{
			opt.disabled=false;
			$(jq).removeAttr("disabled");
		}
	},
		
	/**
	 * ��validatebox��Ϊ���ɱ༭
	 * @param jq
	 */
	disable:function(jq){
		jq.each(function(){
			$.fn.validatebox.methods.setDisable(this,true);
		})
	},
	/**
	 * ��validatebox��Ϊ�ɱ༭
	 * @param jq
	 */
	enable:function(jq){
		jq.each(function(){
			$.fn.validatebox.methods.setDisable(this,false);
		})
	}
});



/**
 * ������ʾǰ�����������첽����
 */
$.extend($.fn.menu.defaults,{
    onBeforeShow:function(jq){
		
	}
});

$.extend($.fn.menubutton.methods,{
	oldEnable:$.fn.menu.methods.enable,
	enable:function(jq){
		return $.fn.menu.methods.enable(jq);
	}
});

/**
 * ����show����
 */
$.extend($.fn.menu.methods, {
	oldShow:$.fn.menu.methods.show,
	show:function(jq,pos){
		$.data(jq[0],"menu").options.onBeforeShow(jq);
		return $.fn.menu.methods.oldShow(jq,pos);
	}
});

$.extend($.fn.menu.methods, {
    appendChild : function (jq, param) {
        return jq.each(function () {
            var parent = $(this).menu("findItem", param.parentText);
            
            if (!parent.target.submenu) {
                var submenu = $("<div/>").width($(this).width()).menu();
                parent.target.submenu = submenu;
                $("<div class=\"menu-rightarrow\"></div>").appendTo($(parent.target).parent());
            }
            
            param = $.extend({},param,{parent:parent.target});
            $(this).menu("appendItem", param);
        });
    }
});

})(jQuery);