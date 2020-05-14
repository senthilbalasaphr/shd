<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<script
	src="${pageContext.request.contextPath }/resources/js/jquery-1.7.1.min.js"></script>
<script>
window.setInterval("reloadIFrame();", 5000);

function reloadIFrame() {
 //document.frames["logFrame"].location.reload();
	//document.getElementById(logFrame).src="";
	
	
		var e = document.getElementById("Log");
	var log = e.options[e.selectedIndex].value;
	
	if (log=="Yes"){
		
	
	document.getElementById('logFrame').contentDocument.location.reload(true);
	
	 var $contents = $('logFrame').contents();
	    $contents.scrollTop($contents.height());
	}
	    
}
</script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#upload')
								.on(
										'click',
										function() {

											//alert('File:'+$('#file').val());
											if ($('#comboClient').val() == null
													|| $('#comboClient').val() == '0') {
												alert('Please select Client');
												$('#comboClient').focus();
												return false;
											}
											if ($('#comboTemplateGrp').val() == null
													|| $('#comboTemplateGrp')
															.val() == '0') {
												alert('Please select Template Group');
												$('#comboTemplateGrp').focus();
												return false;
											}
											if ($('#comboTemplate').val() == null
													|| $('#comboTemplate')
															.val() == '0') {
												alert('Please select Template');
												$('#comboTemplate').focus();
												return false;
											}
											if ($('#comboCompany').val() == null
													|| $('#comboCompany').val() == '0') {
												alert('Please select Company');
												$('#comboCompany').focus();
												return false;
											}
											if ($('#headerIndex').val() == null
													|| $('#headerIndex').val() == ''
													|| $('#headerIndex').val() <= '0') {
												alert('Please Enter Header Index value between 1 to 10');
												$('#headerIndex').focus();
												return false;
											}
											if ($('#valueIndex').val() == null
													|| $('#valueIndex').val() == ''
													|| $('#valueIndex').val() <= '0') {
												alert('Please Enter Value Index value between 1 to 10');
												$('#valueIndex').focus();
												return false;
											}
											if ($('#file').val() == null
													|| $('#file').val() == '') {
												alert('Please Choose .xls/.xlsx file to upload');
												$('#file').focus();
												return false;
											}
											document.uploadForm.submit();
										});

						$('#comboTemplateGrp')
								.on(
										'change',
										function() {
											var client = $('#comboClient')
													.val();
											var tmpGrp = $(this).val();
											var clientId = client + '-'
													+ tmpGrp;
											//alert(clientId);
											$
													.ajax({
														type : 'GET',
														url : '${pageContext.request.contextPath }/getTemplate/'
																+ clientId,
														//data : {clientId: clientId,
														//tmpGrp: tmpGrp},
														success : function(
																result) {
															//alert(result);
															var result = result.d.results;
															var s = '<option value="0">--Select Template --</option>';
															//alert(result.length);
															for (var i = 0; i < result.length; i++) {
																//alert(result[i].find('TEMPLATE').text());
																s += '<option value="' + result[i].TEMPLATE + '">'
																		+ result[i].TEMPLATE
																		+ '</option>';
															}
															$('#comboTemplate')
																	.html(s);
														},
														error : function(result) {
															alert('comboTemplate failed');
														}
													});
										});
						$('#comboClient')
								.on(
										'change',
										function() {
											var clientId = $(this).val();
											$
													.ajax({
														type : 'GET',
														url : '${pageContext.request.contextPath }/getTemplateGrp/'
																+ clientId,
														success : function(
																result) {
															//alert(result);
															var result = result.d.results;
															var s = '<option value="0">--Select Template Group --</option>';
															//alert(result.length);
															for (var i = 0; i < result.length; i++) {
																//alert(result[i].find('TEMPLATE').text());
																s += '<option value="' + result[i].TEMPLATEGRP + '">'
																		+ result[i].TEMPLATEGRP
																		+ '</option>';
															}

															$(
																	'#comboTemplateGrp')
																	.html(s);
														},
														error : function(result) {
															alert('comboTemplateGrp failed');
														}
													});
										});
					});
</script>
</head>
<body>
	<form:form method="post" enctype="multipart/form-data"
		name="uploadForm"
		action="${pageContext.request.contextPath}/uploadExcelFile">
		<h1>CAPT Data Migration Tool</h1>
		<P>Tool for all your Data Migration Needs...</P>

		<br>

		<table>
			<tr>
				<td width="10%">Client:</td>
				<td width="30%"><select id="comboClient" name="comboClient">
						<option value="0">--Select Client--</option>
						<c:forEach items="${clients}" var="client" varStatus="tagStatus">
							<option value="<c:out value="${client.key}"/>"><c:out
									value="${client.key}" /></option>
						</c:forEach>
						<!-- <option value="1">client Code - Client Description</option> -->
				</select></td>
				<td width="10%"></td>
				<td width="10%">Template Group:</td>
				<td width="30%"><select id="comboTemplateGrp"
					name="comboTemplateGrp" style="width: 200px"></select></td>
				<td width="10%"></td>
			</tr>
			<tr>
				<td width="10%">Template:</td>
				<td width="30%"><select id="comboTemplate" name="comboTemplate"
					style="width: 200px"></select></td>
				<td width="10%"></td>
				<td width="10%">Company:</td>
				<td width="30%"><select id="comboCompany" name="comboCompany"
					style="width: 200px">
						<option value="0">--Select Company --</option>
						<option value="08">Indonesia</option>
						<option value="01">Korea</option>
						<option value="07">Malaysia</option>
						<option value="05">Philippines</option>
						<option value="03">SCV</option>
						<option value="06">SPT</option>
						<option value="04">SVI</option>
						<option value="02">Taiwan</option>
							<option value="09">Thailand</option>
						<option value="99">TestRun</option>
				</select></td>
				<td width="10%"></td>
			</tr>
			<tr>
				<td width="10%">Header Index:</td>
				<td width="30%"><input type="text" id="headerIndex"
					name="headerIndex" maxlength="2" value="5" /></td>
				<td width="10%"></td>
				<td width="10%">Value Index:</td>
				<td width="30%"><input type="text" id="valueIndex"
					name="valueIndex" maxlength="2" value="7" /></td>
				<td width="10%"></td>
			</tr>
			<tr>
				<td width="10%">Test Run:</td>
				<td width="30%">
				<select id="comboTest" name="comboTest"
					style="width: 200px">
						<option value="0">--Test Run --</option>
						<option value="Yes">Yes</option>
						<option value="No">No</option>
					</select>
					
					</td>
				<td width="10%"></td>
				<td width="10%"></td>
				<td width="30%"></td>
				<td width="10%"></td>
			</tr>
			<tr>
				<td colspan="6" align="center">Upload: <input type="file"
					name="file" id="file" accept=".xls,.xlsx" /> <input type="button"
					id="upload" name="upload" value="Upload file" /></td>

			</tr>
			<tr>
				<td  colspan="6">
				<input type="text" value="${sessionScope.data}"  type="hidden"/>
				</td>
				<td width="30%">
				<select id="Log" name="Log"
					style="width: 200px">
						<option value="Yes">Yes</option>
						<option value="No">No</option>
					</select>
					
					</td>
			</tr>
			<tr>
				<td  colspan="6"  >
				<iframe id="logFrame"  width="500" height="500" name="logFrame" src="${pageContext.request.contextPath}/showLogs"></iframe>
				<%-- <jsp:include page="../views/logs.jsp" flush="true"/> --%>
				</td>
			</tr>
		</table>

	</form:form>
</body>
</html>
