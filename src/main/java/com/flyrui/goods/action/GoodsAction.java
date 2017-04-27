package com.flyrui.goods.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.flyrui.common.DateUtil;
import com.flyrui.common.action.BaseAction;
import com.flyrui.common.service.CommonService;
import com.flyrui.common.uuid.UUIDHexGenerator;
import com.flyrui.dao.common.page.PageModel;
import com.flyrui.exception.ErrorConstants;
import com.flyrui.exception.FRError;
import com.flyrui.exception.FRException;
import com.flyrui.financMgmt.pojo.AccoutInfoDto;
import com.flyrui.financMgmt.service.AccoutInfoService;
import com.flyrui.goods.pojo.Goods;
import com.flyrui.goods.pojo.GoodsOrder;
import com.flyrui.goods.pojo.GoodsRevAddr;
import com.flyrui.goods.pojo.TbChinaArea;
import com.flyrui.goods.service.GoodsOrderService;
import com.flyrui.goods.service.GoodsRevAddrService;
import com.flyrui.goods.service.GoodsService;
import com.flyrui.goods.service.TbChinaAreaService;

@ParentPackage("frcms_default")
@Namespace("/Goods") //访问路径的包名
@Results({
		@Result(name="goodsList", location = "/wap/goods/goodsList.jsp"),
		@Result(name="goodsDetail", location = "/wap/goods/goodsDetail.jsp"),
		@Result(name="queryUserOrder", location = "wap/goods/queryUserOrder.jsp"),	
		@Result(name="goodsRevAddrEdit", location = "wap/goods/goodsRevAddrEdit.jsp"),
		@Result(name="goodsRevAddrList", location = "wap/goods/goodsRevAddr.jsp"),
		@Result(type="json", params={"root","result"})}) 
public class GoodsAction extends BaseAction {	
		
    /**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(GoodsAction.class);	
    	  
    public String operType;
    
    public Goods goods = new Goods(); 
    public GoodsOrder goodsOrder = new GoodsOrder();
    public GoodsRevAddr goodsRevAddr = new GoodsRevAddr();
    
    public int rows; //每页大小
	
	public int page;//当前页号
	
	public String ids;
	
	@Autowired
	public GoodsService goodsService;	
	
	@Autowired
	AccoutInfoService accoutInfoService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	GoodsRevAddrService goodsRevAddrService;
	
	@Autowired
	TbChinaAreaService tbChinaAreaService;
	
	@Autowired
	GoodsOrderService goodsOrderService;
	
	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	@Action(value="goodsList")
    public String goodsList() throws FRException{    	
		if(goods.getCatalog_id()==null){
			throw new FRException(new FRError(ErrorConstants.PARAM_ERROR));
		}
		goods.setState("1");
		goods.setEff_date(new Date());
		goods.setExp_date(new Date());
		if(rows==0){
    		rows=5;
    	}
    	if(page==0){
    		page = 1;
    	}
    	PageModel retPage = goodsService.getPagerListByCon(goods, page, rows);
    	setResult(retPage);
    	return "goodsList";
    }
    
	public String formatDouble(double s){
	    DecimalFormat fmt = new DecimalFormat("###0.00");
	    return fmt.format(s);
	}
	

	@Action(value="goodsDetail")
    public String goodsDetail() throws FRException{    	
		if(goods.getGoods_id()==null){
			throw new FRException(new FRError(ErrorConstants.PARAM_ERROR));
		}
		goods.setState("1");
		goods.setEff_date(new Date());
		goods.setExp_date(new Date());
		 
    	List<Goods> goodsList = goodsService.getListByCon(goods);
    	if(goodsList.size()==0){
    		throw new FRException(new FRError("GOODS_001"));
    	}
    	Integer goodsAmount = goods.getGoods_amount();
    	goods = goodsList.get(0);    	
    	goods.setGoods_amount(goodsAmount);   	
    	
    	return "goodsDetail";
    }
	
	@Action("checkCoin")
	public String checkCoin() throws FRException{
		
		if(goods.getPay_type()==null && goods.getGoods_amount() == null && goods.getGoods_price()==null){
			throw new FRException(new FRError(ErrorConstants.PARAM_ERROR));
		}
		
		AccoutInfoDto accoutInfo = new AccoutInfoDto();
    	accoutInfo.setUser_id(Integer.valueOf(getUserId()));
    	AccoutInfoDto retAccoutInfoDto = accoutInfoService.queryAccountInfo(accoutInfo);
    	String payType = goods.getPay_type();
    	String checkCoin = "0";
    	Double totalFee = goods.getGoods_amount()*goods.getGoods_price()/100;
    	//查询用户的账户信息，提示用户的
    	if(retAccoutInfoDto!=null){
    		Double electCoin = (Double)retAccoutInfoDto.getElect_coin();
    		Double reconsmpCoin = (Double)retAccoutInfoDto.getReconsmp_coin();
    		if("2".equals(payType)){//电子币
    			if(electCoin>=totalFee){
    				checkCoin = "1";
    			}
    		}else if("3".equals(payType)){//重销币
    			if(reconsmpCoin>=totalFee){
    				checkCoin = "1";
    			}
    		}
    	}
    	Map<String, String> retMap = new HashMap<String, String>();
    	retMap.put("check", checkCoin);
    	setResult(retMap);
		return SUCCESS;
	}
	
	@Action("accept")
	public String accept() throws FRException{
		if(goodsOrder.getGoods_id()==null && goodsOrder.getGoods_amount()==null && goodsOrder.getPay_type()==null
				){
			throw new FRException(new FRError(ErrorConstants.PARAM_ERROR));
		}
		if(!"2".equals(goodsOrder.getPay_type()) && !"3".equals(goodsOrder.getPay_type())){
			throw new FRException(new FRError(ErrorConstants.PARAM_ERROR));
		}
		if(goodsOrder.getGoods_amount()<1){
			throw new FRException(new FRError(ErrorConstants.PARAM_ERROR));
		}
		
		goods.setGoods_id(goodsOrder.getGoods_id());
		goods.setState("1");
		goods.setEff_date(new Date());
		goods.setExp_date(new Date());
		//orderCode的组成规则 YYYYMMDDHH+8位序列
		String goodsOrderCode = "00000000"+commonService.getSequence("seq_goods_order");
		goodsOrderCode = DateUtil.formatDate(new Date(), "yyyyMMddHH")+goodsOrderCode.substring(goodsOrderCode.length()-8);
		goodsOrder.setOrder_code(goodsOrderCode);
		goodsOrder.setOrder_id(UUIDHexGenerator.generator());
		goodsOrder.setUser_id(getUserId());
		goodsOrder.setUser_name(getLoginUserInfo().getName());
		goodsOrder.setCreate_date(new Date());
		goodsOrder.setOrd_ip(getIp());
		goodsOrder.setState("0");
    	goodsService.accept(goods, goodsOrder,getLoginUserInfo());//用于事务
    	setCommonSuccessReturn();
		return SUCCESS;
	}
	
	@Action("goodsRevAddrList")
	public String goodsRevAddrList(){
		goodsRevAddr.setUser_id(getUserId());
		if(rows==0){
    		rows=5;
    	}
    	if(page==0){
    		page = 1;
    	}
		PageModel pageModel = goodsRevAddrService.getPagerListByCon(goodsRevAddr, page, rows);
		setResult(pageModel);
		return "goodsRevAddrList";
	}
	
	@Action("goodsRevAddrEdit")
	public String goodsRevAddrEdit(){
		
		if(goodsRevAddr.getAddr_id()!=null){
			List<GoodsRevAddr> goodsRevAddrList = goodsRevAddrService.getListByCon(goodsRevAddr);
			if(goodsRevAddrList.size()>0){
				goodsRevAddr = goodsRevAddrList.get(0);
			}
		}
		Map<String,List<TbChinaArea>> areaMap = new HashMap<String,List<TbChinaArea>>();
		
		//查询全国的省区县配置信息
		TbChinaArea tbChinaArea = new TbChinaArea();
		tbChinaArea.setLevel(2);
		List<TbChinaArea> provList = tbChinaAreaService.getListByCon(tbChinaArea);
		areaMap.put("prov",provList);
		if(provList.size()>0){
			TbChinaArea provAare = provList.get(0);
			tbChinaArea.setPid(provAare.getId());
			tbChinaArea.setLevel(3);
			List<TbChinaArea> provList2 = tbChinaAreaService.getListByCon(tbChinaArea);
			areaMap.put("zone",provList2);
			if(provList.size()>0){
				TbChinaArea zoneAare = provList.get(0);
				tbChinaArea.setPid(zoneAare.getId());
				tbChinaArea.setLevel(4);
				List<TbChinaArea> provList3 = tbChinaAreaService.getListByCon(tbChinaArea);
				areaMap.put("xian",provList3);
			}
		}
		
		return "goodsRevAddrEdit";
	}
	
	@Action("goodsRevAddrUpdate")
    public String goodsRevAddrUpdate(){
		if(goodsRevAddr.getAddr_id() !=null){
			goodsRevAddr.setUser_id(getUserId());
			goodsRevAddrService.update(goodsRevAddr);
		}else{
			String addrId = commonService.getSequence("seq_goods_revaddr_id");
			goodsRevAddr.setUser_id(getUserId());
			goodsRevAddr.setAddr_id(Integer.parseInt(addrId));
			goodsRevAddr.setCreate_date(new Date());
			goodsRevAddr.setState("1");
			goodsRevAddrService.insert(goodsRevAddr);
		}
		setCommonSuccessReturn();
    	return SUCCESS;
    }
	
	@Action("getNextLevelAddr")
    public String getNextLevelAddr(){
		List<TbChinaArea> retList = new ArrayList<TbChinaArea>();
		if(ids !=null){
			TbChinaArea tbChinaArea = new TbChinaArea();
			retList = tbChinaAreaService.getListByCon(tbChinaArea);
		}
		setResult(retList);
    	return SUCCESS;
    }
	
	@Action("queryUserOrder")
    public String queryUserOrder(){
		GoodsOrder  goodsOrder = new GoodsOrder();
		if(rows==0){
    		rows=5;
    	}
    	if(page==0){
    		page = 1;
    	}
		PageModel pageModel = goodsOrderService.getPagerListByCon(goodsOrder, page, rows);
		setResult(pageModel);
    	return "queryUserOrder";
    }
    
}