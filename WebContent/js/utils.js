
function go(path){
	location=path;
}
function goconf(msj,path){
	if(confirm("ATENCION!\n"+msj))location=path;
}
r=0;
function adddetvenrow(){
	r++;
	$('<tr id="row'+r+'"><td class="sel"></td>'+
	  '<td><input name="cant" type="number" class="validate[required,custom[onlyNumberSp],min[1]" id="cant'+r+'" placeholder="unidades" autocomplete="off" onblur="sum('+r+')" value="1"><br><span id="info'+r+'"></span></td>'+
	  '<td><input type="text" id="cos'+r+'" name="cos" autocomplete="off" required readonly onblur="sum('+r+')"></td>'+
	  '<td><input type="number" class="validate[required,custom[number],min[0.0]]" id="gan'+r+'" name="gan" placeholder="incremento %" autocomplete="off" required onblur="modpunit('+r+')"></td>'+
	  '<td><input type="number" class="validate[required,custom[number],min[0.0]]" id="punit'+r+'" name="punit" placeholder="precio de venta" autocomplete="off" required onblur="sum('+r+')"></td>'+
	  '<td><span id="tot'+r+'" class="sub">0.00</span></td>'+
	  '<td class="center"><button type="button" class="btn btn-sm btn-danger" onclick="deldetrow('+r+')"><strong>-</strong></button></td></tr>').appendTo('#example');
	$sel=$('#codp0').clone();
	$sel.attr('id','codp'+r);
	$sel.attr('onchange','mvvalmv('+r+',true)');
	$("#row"+r+" .sel").html($sel);
	$("#cant"+r).focus();
	$('#example input').css('color','#000');
	$('#example select').css('color','#000');
}
function adddetcomrow(){
	r++;
	temp='<tr id="row'+r+'"><td class="sel"></td><td><input type="text" id="cod'+r+'"  class="validate[required,minSize[3],maxSize[15]]" name="cod" placeholder="codigo" autocomplete="off" required></td>'+
					'<td> <input name="caj" type="number" class="validate[required,custom[onlyNumberSp],min[1]" id="caj'+r+'" placeholder="cajas o bultos" autocomplete="off" onblur="modcan('+r+')" value="1"></td>'+
                   '<td><input name="uca" type="number" class="validate[required,custom[onlyNumberSp],min[1]" id="uca'+r+'" placeholder="unid. x caja" autocomplete="off" onblur="modcan('+r+')" value="1"></td>'+
                   '<td class="puc"><input name="cant" type="number" class="validate[required,custom[onlyNumberSp],min[1]" id="cant'+r+'" placeholder="unidades" autocomplete="off" onblur="sum('+r+')" value="1"><br><span id="info'+r+'"></span></td>'+
					'<td><input type="number" id="punit'+r+'"  class="validate[required,custom[number],min[0.0]]" name="punit" placeholder="precio por unidad" autocomplete="off" required onblur="sum('+r+')"></td>'+
                   '<td><span id="tot'+r+'" class="sub">0.00&nbsp;&nbsp;&nbsp;</span></td><td class="center"><button type="button" class="btn btn-sm btn-danger" onclick="deldetrow('+r+')"><strong>-</strong></button></td></tr>';
	$(temp).appendTo('#example');
	$sel=$('#codp0').clone();
	$sel.attr('id','codp'+r);
	$sel.attr('onchange','mvvalm('+r+')');
	$("#row"+r+" .sel").html($sel);
	$("#codp"+r).focus();
	$('#example input').css('color','#000');
	$('#example select').css('color','#000');
}
function deldetrow(r){
	$("#row"+r).remove();
	sumall();
}
function mvvalm(row,valcan){
	tasac=$('#tcm').val();
	if(isNaN(tasac))tasac=1;
	$opt=$('#codp'+row).val();
	cant=$('#codp'+row+' #'+$opt).attr('class');
	$obj=$('#cant'+row);
	$obj.val(1);	
	if(valcan)$obj.attr('class','validate[required,custom[onlyNumberSp],min[1],max['+cant+']]');
	if(parseInt($opt)!=-1)$('#info'+row).html(cant+' disponibles!');
	else $('#info'+row).html('');
	val=parseFloat($('#'+$opt).attr("tpre"))/tasac;
	$('#punit'+row).val(val.toFixed(2));
	sum(row);
}
function mvvalmv(row,valcan){
	tasac=$('#tcm').val();
	if(isNaN(tasac))tasac=1;
	$opt=$('#codp'+row).val();
	cant=$('#codp'+row+' #'+$opt).attr('class');
	gan=$('#codp'+row+' #'+$opt).attr('gan');
	$obj=$('#cant'+row);
	$obj.val(1);	
	if(valcan)$obj.attr('class','validate[required,custom[onlyNumberSp],min[1],max['+cant+']]');
	if(parseInt($opt)!=-1)$('#info'+row).html(cant+' disponibles!');
	else $('#info'+row).html('');
	val=parseFloat($('#'+$opt).attr("tpre"))/tasac;
	$('#cos'+row).val(val.toFixed(2));
	$('#gan'+row).val(gan);
	f=val+gan/100*val;
	$('#punit'+row).val(f.toFixed(2));
	sum(row);
}
function sumall(){
	for(i=0;i<=r;i++){
		$row=$('#row'+i);
		if($row){
			sum(i);
		}
	}
}
function modpre(){
	tasac=parseFloat($('#tcm').val());
	if(isNaN(tasac))tasac=1;
	for(i=0;i<=r;i++){
		$row=$('#row'+i);
		if($row){
			$obj=$('#punit'+i);
			actual=parseFloat($obj.val());
			monto=actual*tasac;
			$obj.val(isNaN(monto)?"":monto.toFixed(2));
		}
	}
	sumall();
}
function sum(row){
	cant=$('#cant'+row).val();
	punit=$('#punit'+row).val();
	if(!isNaN(cant)&&!isNaN(punit)){
		monto=(cant*punit);
		$('#tot'+row).html(monto.toFixed(2));
		suma=0;
		$(".sub").each(
				function(idx, el) {
					suma+=parseFloat($(el).html());
				}
				);
		$('#total').html(suma.toFixed(2));}
}
function modpunit(row){
	gan=$('#gan'+row).val();
	val=$('#cos'+row).val();
	tasac=$('#tcm').val();
	if(isNaN(tasac))tasac=1;
	f=parseFloat(val)+(gan/100*val);
	f=f*tasac;
	$('#punit'+row).val(f.toFixed(2));
	sum(row);
}
function modcan(row){
	caj=$('#caj'+row).val();
	uca=$('#uca'+row).val();
	if(!isNaN(caj)&&!isNaN(uca)){
		$("#cant"+row).val(caj*uca);
	}
	else $("#cant"+row).val("");
	sum(row);
}