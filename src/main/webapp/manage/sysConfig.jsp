<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="plugin.jsp"%>
<html>
	<head>
		<link rel="stylesheet" href="css/util.css" />
		<link rel="stylesheet" href="css/standard.css" />
		<link rel="stylesheet" href="manage/css/syscon.css" />
		<script src="manage/lhgdialog/lhgcore.min.js"></script>
		<script src="manage/lhgdialog/lhgdialog.min.js?s=default"></script>
		<script src="manage/js/syscon.js"></script>
		<title><s:text name="sys.config" /></title>

		<script>		
			var picture= "<s:text name='picture'/>";
			var loading= "<s:text name='loading'/>";
			var choose = "<s:text name='choose'/>";
			var language = "<s:text name='language'/>";
			//init checkbox/radio
			var takeId = "<s:property value='takeId'/>";
			var deskId = "<s:property value='deskId'/>";			
			var pay = "<s:property value='pay'/>";			
			var alltype = "<s:property value='alltype'/>";			
			var shoporder = "<s:property value='shoporder'/>";			
			var kitchenorder = "<s:property value='kitchenorder'/>";			
			//alert info
			var message = "<s:property value='message'/>";
			var modsuccess = "<s:text name='sys.modsuccess'/>";
		</script>
	</head>
	<body>
		<div class="title">
			<h3>
				<s:text name="sys.config" />
			</h3>
		</div>
		<div class="content">
			<form method="post">
				<table class="table table-hover table-config">
					<!-- index logo -->
					<tr>
						<th>
							<s:text name="index" />
							Logo
						</th>
						<td class="logo">
							<input name="indexId" id="index" type="hidden" value="<s:property value='indexId'/>" />
							<img class="inline" src="<s:if test='indexId == 0'>sysimg/logo/index<s:text name='language' />.jpg</s:if><s:else><s:property value='indexUrl' /></s:else>" />
							<button class="btn btn-primary inline" type="button">
								<s:text name="choose" />
							</button>
							<button id="indexDefault" class="btn btn-success inline" type="button">
								<s:text name="default" />
							</button>
							<div class="inline">
								<s:text name="sys.index" />
							</div>
						</td>
					</tr>
					<!-- login logo -->
					<tr>
						<th>
							<s:text name="login" />
							Logo
						</th>
						<td class="logo">
							<input name="loginId" id="login" type="hidden" value="<s:property value='loginId'/>" />
							<img class="inline" src="<s:if test='loginId == 0'>sysimg/logo/login<s:text name='language' />.jpg</s:if><s:else><s:property value='loginUrl' /></s:else>" />
							<button class="btn btn-primary inline" type="button">
								<s:text name="choose" />
							</button>
							<button id="loginDefault" class="btn btn-success inline" type="button">
								<s:text name="default" />
							</button>
							<div class="inline">
								<s:text name="sys.login" />
							</div>
						</td>
					</tr>
					<!-- take and desk -->
					<tr>
						<th>
							<s:text name="sys.take" />
						</th>
						<td class="take">
							<!-- take -->
							<input type="hidden" name="takeId" value="<s:property value='takeId'/>">
							<label>
								<input type="radio" name="take" />
								<s:text name="sys.hide" />
							</label>
							<label>
								<input type="radio" name="take" />
								<s:text name="sys.show" />
							</label>
							<select id="takeSelect">
								<option value="1">
									<s:text name="sys.inside" />
								</option>
								<option value="2">
									<s:text name="sys.outside" />
								</option>
							</select>
							<div class="inline">
								<s:text name="sys.takeDesc" />
							</div>
						</td>
					</tr>
					<tr class="desk">
						<th>
							<s:text name="sys.desk" />
						</th>
						<td>
							<label>
								<input type="radio" name="deskId" value="0" />
								<s:text name="sys.hide" />
							</label>
							<label>
								<input type="radio" name="deskId" value="1" />
								<s:text name="sys.show" />
							</label>
						</td>
					</tr>

					<!-- deposit -->
					<tr>
						<th>
							<s:text name="sys.deposit" />
						</th>
						<td class="deposit">
							<!-- d1:nd100 -->
							<ul>
								<li>
									<label>
										<s:text name="sys.nd100"></s:text>
										<span class="emphasis">600</span>
										<s:text name="sys.nd1002"></s:text>
										<span class="emphasis">100</span>
										<s:text name="sys.nd1003"></s:text>
									</label>
								</li>
							</ul>
							<ul class="tw nd">
								<li>
									<span class="dep-name"> $100:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['nd100tw100'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['nd100tw100'].min" />">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['nd100tw100'].max" />" readonly="readonly">
								</li>

							</ul>
							<ul class="cn nd">
								<li>
									<span class="dep-name"> ￥10:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['nd100cn10'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['nd100cn10'].min" />">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['nd100cn10'].max" />" readonly="readonly">
								</li>

							</ul>

							<!-- d2:nv9 -->
							<ul>
								<li>
									<label>
										<s:text name="sys.nv9" />
										<span class="emphasis">500</span>
										<s:text name="sys.nv92" />
									</label>
								</li>
							</ul>

							<ul class="tw nv">
								<li>
									<span class="dep-name"> $100:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['nv9tw100'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['nv9tw100'].min" />" readonly="readonly">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['nv9tw100'].max" />">
								</li>
								<li>
									<span class="dep-name">$500:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['nv9tw500'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['nv9tw500'].min" />" readonly="readonly">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['nv9tw500'].max" />">
								</li>
								<li>
									<span class="dep-name">$1000:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['nv9tw1000'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['nv9tw1000'].min" />" readonly="readonly">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['nv9tw1000'].max" />">
								</li>
							</ul>

							<ul class="cn nv">
								<li>
									<span class="dep-name"> ￥1:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['nv9cn1'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['nv9cn1'].min" />" readonly="readonly">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['nv9cn1'].max" />">
								</li>
								<li>
									<span class="dep-name">￥5:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['nv9cn5'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['nv9cn5'].min" />" readonly="readonly">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['nv9cn5'].max" />">
								</li>
								<li>
									<span class="dep-name">￥10:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['nv9cn10'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['nv9cn10'].min" />" readonly="readonly">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['nv9cn10'].max" />">
								</li>
								<li>
									<span class="dep-name">￥20:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['nv9cn20'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['nv9cn20'].min" />" readonly="readonly">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['nv9cn20'].max" />">
								</li>
								<li>
									<span class="dep-name">￥50:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['nv9cn50'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['nv9cn50'].min" />" readonly="readonly">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['nv9cn50'].max" />">
								</li>
								<li>
									<span class="dep-name">￥100:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['nv9cn100'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['nv9cn100'].min" />" readonly="readonly">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['nv9cn100'].max" />">
								</li>
							</ul>


							<!-- d3:hop -->
							<ul>
								<li>
									<label>
										<s:text name="sys.hop"></s:text>
										<span class="emphasis">1000</span>
										<s:text name="sys.hop2"></s:text>
									</label>
								</li>
							</ul>
							<ul class="tw hop">
								<li>
									<span class="dep-name"> $1:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['hoppertw1'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['hoppertw1'].min" />">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['hoppertw1'].max" />">
								</li>
								<li>
									<span class="dep-name">$5:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['hoppertw5'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['hoppertw5'].min" />">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['hoppertw5'].max" />">
								</li>
								<li>
									<span class="dep-name">$10:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['hoppertw10'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['hoppertw10'].min" />">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['hoppertw10'].max" />">
								</li>
								<li>
									<span class="dep-name">$50:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['hoppertw50'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['hoppertw50'].min" />">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['hoppertw50'].max" />">
								</li>
							</ul>
							<ul class="cn hop">
								<li>
									<span class="dep-name"> ￥0.1: </span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['hoppercn0.1'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['hoppercn0.1'].min" />">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['hoppercn0.1'].max" />">
								</li>
								<li>
									<span class="dep-name">￥0.5:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['hoppercn0.5'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['hoppercn0.5'].min" />">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['hoppercn0.5'].max" />">
								</li>
								<li>
									<span class="dep-name">￥1:</span>
									<input type="hidden" name="depId" value="<s:property value="depositMap['hoppercn1'].id" />">
									<input class="form" name="depMin" value="<s:property value="depositMap['hoppercn1'].min" />">
									<span class="separator"></span>
									<input class="form" name="depMax" value="<s:property value="depositMap['hoppercn1'].max" />">
								</li>
							</ul>
						</td>
					</tr>
					<tr class="pay">
						<th>
							支付方式
							<input type="hidden" name="pay" value="<s:property value='pay'/>">
							<!-- TODO -->
						</th>
						<td>
							<label class="inline">
								<input class="inline" type="checkbox" value="cash">
								現金
							</label>
							<label class="inline">
								<input class="inline" type="checkbox" value="creditcard">
								信用卡
							</label>
							<label class="inline">
								<input class="inline" type="checkbox" value="wechat">
								微信
							</label>
							<label class="inline">
								<input class="inline" type="checkbox" value="alipay">
								支付寶
							</label>
							<label class="inline">
								<input class="inline" type="checkbox" value="member">
								會員
							</label>
							<label class="inline">
								<input class="inline" type="checkbox" value="metrocard">
								一卡通
							</label>
							<label class="inline">
								<input class="inline" type="checkbox" value="easycard">
								悠遊卡
							</label>
						</td>
					</tr>
					<tr class="alipay">
						<th>
							支付寶配置
						</th>
						<td>
							<div style="color:#f00;font-weight: 600;margin-left: 20px">
								相關配置請聯繫技術人員進行填寫
							</div>
							<div>
								<label>
									<span>支付抬头:</span>
									<input id="zfb_title" name="zfb_title" value="<s:property value='zfb_title'/>">
								</label>
							</div>
							<div>
								<label>
									<span>PARTNER:</span>
									<input id="zfb_partner" name="zfb_partner" value="<s:property value='zfb_partner'/>">
								</label>
							</div>
							<div>
								<label>
									<span>APP_ID:</span>
									<input id="zfb_appid" name="zfb_appid" value="<s:property value='zfb_appid'/>">
								</label>
							</div>
							<div>
								<label>
									<span>私鑰:<input type="hidden" value="<s:property value='zfb_privatekey_status'/>" id="zfb_privatekey_status"> </span>
									<textarea rows="10" cols="80" name="zfb_privatekey" id="zfb_privatekey"></textarea>
								</label>
							</div>
							<div>
								<label>
									<span>公鑰:<input type="hidden" value="<s:property value='zfb_publickey_status'/>" id="zfb_publickey_status"> </span>
									<textarea rows="5" cols="80" name="zfb_publickey" id="zfb_publickey"></textarea>
								</label>
							</div>
						</td>
					</tr>
					<tr class="wechat">
						<th>
							微信配置
						</th>
						<td>
							<div style="color:#f00;font-weight: 600;margin-left: 20px">
								相關配置請聯繫技術人員進行填寫
							</div>
							<div>
								<label>
									<span>支付抬头:</span>
									<input id="wx_title" name="wx_title" value="<s:property value='wx_title'/>">
								</label>
							</div>
							<div>
								<label>
									<span>商戶號(mchID):</span>
									<input id="wx_mchID" name="wx_mchID" value="<s:property value='wx_mchID'/>">
								</label>
							</div>
							<div>
								<label>
									<span>公眾號(appID):</span>
									<input id="wx_appID" name="wx_appID" value="<s:property value='wx_appID'/>">
								</label>
							</div>
							<div>
								<label>
									<span>密鑰(key):<input type="hidden" value="<s:property value='wx_key_status'/>" id="wx_key_status"> </span>
									<textarea rows="1" cols="80" name="wx_key" id="wx_key"></textarea>
								</label>
							</div>
						</td>
					</tr>

					<tr class="alltype">
						<th>
							分類顯示
						</th>
						<td>
							<label>
								<input type="radio" name="alltype" value="0" checked>
								否
							</label>
							<label>
								<input type="radio" name="alltype" value="1">
								是
							</label>
						</td>
					</tr>
					<tr class="showorder">
						<th>
							單據顯示
						</th>
						<td>
							<label>
								<input type="checkbox" name="shoporder" value="1">
								點餐端
							</label>
							<label>
								<input type="checkbox" name="kitchenorder" value="1">
								廚房端
							</label>
						</td>
					</tr>
					<tr class="accessory">
						<th>
							附加費
						</th>
						<td>
							<div>
								是否啟用:
								<label>
									是
									<input type="radio" value="1" name="used" <s:if test="used == 1">checked</s:if>>
								</label>
								<label>
									否
									<input type="radio" value="0" name="used" <s:if test="used != 1">checked</s:if>>
								</label>
							</div>
							<div>
								<label>
									名稱:
									<input type="text" name="name" id="name" value="<s:property value='name'/>">
								</label>
							</div>
							<div>
								<label>
									比例:
									<input type="text" name="percent" id="percent" value="<s:property value='percent'/>">
								</label>
								<span style="color:#00a;font-weight: 500;font-size:15px;">%</span>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="button">
			<button class="btn btn-primary" id="save" type="button">
				<s:text name="submit" />
			</button>
			<button class="btn btn-success" id="reset" type="button">
				<s:text name="reset" />
			</button>
		</div>
	</body>
</html>
