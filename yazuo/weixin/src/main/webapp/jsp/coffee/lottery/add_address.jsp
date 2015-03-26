<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="popUp" id="buy_address_div" style='display:none'>
	<div class="popInfo">
    <div>
    	<input type="hidden" name="lotterName" id="lotterName" value="${lottery.award.lottery_coupon_name }"/>
		<input type="hidden" name="orderId" id="orderId" value="${lottery.award.orderId }"/>
    	  <section class="buy-module" > 
        	<p class="buy-pop-p">你获得：<em id="address_show_lottery"></em></p>
        	<p class="buy-pop-p">请填写收货地址</p>
            <ul>
            	<li><p class="clear-wrap"><span class="left">收&nbsp;货&nbsp;人：</span>
            		<span class="left"><input type="text" class="customer-add-input"  name="receiver" id="receiver" value="" /></span></p>
            	</li>
                <li><p class="clear-wrap"><span class="left">联系方式：</span>
                	<span class="left"><input type="text" class="customer-add-input" name="mobile" id="mobile" value="${memberAward.phoneNo}" /></span></p>
                </li>
                <li><p class="clear-wrap"><span class="left">详细地址：</span>
                	<span class="left">
                	<textarea style="line-height: 18px; height: 70px;" name="detail_address" id="detail_address" class="customer-add-input"></textarea>
                </span></p>
                </li>
            </ul>
        </section>
        <span id="span_error"></span>
         <div class="popBtns">
         <a class="btn2 draw-submit" id="btn_shop_submit">提交</a>
        <a href="javascript:luckyDraw();"  class="grayBtn2" id='btn_close'>关闭</a>
        </div>
    </div>
    </div>
 </div>

<div class="popUp" id="comfirm_address_div" style='display:none'>
	<div class="popInfo pop-cont">
		<h1 class="pop-h1">领奖确认</h1>
		<p class="pop-tit">确认要提交收货信息吗？</p>
		<p class="gray999">收货信息十分重要，我们将按照信息中的地址发送奖品。收货信息提交后无法再修改。</p>
	</div>
    <div class="popBtns">
	   <a class="btn2 draw-submit" id="btn_ok" href="javascript:saveAddress();">确定</a>
	   <a href="javascript:btnCancle();"  class="grayBtn2" id='btn_close'>取消</a>
	</div>
 
 </div>