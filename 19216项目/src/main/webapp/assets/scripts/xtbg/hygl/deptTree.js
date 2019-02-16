/**
 * 会议管理 -部门人员
 */

	var basePath = $("#basePath").val();
		
			$(function(){
				initData(1);
			});
		
			//数据初始化
			function initData(pageNumber){
				var msgIndex = top.layer.msg('数据加载中', {icon:16, shade:[ 0.5, '#999999' ], time:0});
				var deptId=$("#deptId").val();
				var name = $("#name").val();
				$("#bmry").load(basePath + "/xtbg/hygl/treeIndex.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'name': name,'deptId':deptId}, function(){
					top.layer.close(msgIndex);
				});
			}
			
			
			// ZTree加载完成后回调
			function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
				var treeObj = window.frames['treeFrame'].window.deptTreeObj;
				// 展开所有节点
				treeObj.expandAll(true);
			}

			// 节点被点击时触发
			function zTreeOnClick(event, treeId, treeNode){
				$("#deptId").val(treeNode.id);
				initData(1);
			}
		/**回显当前，选择的参会人员的姓名
		 * 
		 */
			function goSave(){
				var ids='';//初始化参会人员id,包扩隐藏的id值
				var chkItems=$('input[name="icheck"]');
				$.each(chkItems,function(index,chkItem){
					if ($(chkItem).is(":checked")) {
						 ids =ids+ $(chkItem).val() + ',' ;
					}
				});
				var oIds='';//初始化已经被选择的参会id
				 var inputs=parent.layer.getChildFrame('body').find("#ul").find("li").find("input");
				    $.each(inputs,function(index,inpu){
				    	oIds +=$(inpu).val()+',';
				    })
				    var oId=oIds.split(",");//截取成数组
				 
						//回显参会人员名称
				$.ajax({
					type : "POST",
					url : basePath + "/xtbg/hygl/selectName.do",
					data : {'ids':ids},
					dataType: "json",
					success : function(data) {
						var data1=eval(data);
						var str='';
						 $.each(data1,function(index, n){
							 //判断回显的id,, 在已选的参会人员是不是已存在，判断剔除
							 if (!contains(oId, data1[index].accCode) ) {
								/* <li >
									${attr.name !} <div onclick="delFj('${attr.id !}', this)" class="ftpdel"><input type="hidden"  name="staffId" value="${attr.id !}"></div></li>*/
								str=str+"<li class='form-user-tag'>"+data1[index].name+"<span onclick=\"delStaff('"+data1[index].accCode+"',this)\" class='colsed'></span><input type='hidden'  name='staffId' value='"+data1[index].accCode+"'></li>";
							 }
						    });
						 //追加到回显列表中
						/* alert(parent.layer.getChildFrame('body').find("#ul:last-child"));
						 if ( parent.layer.getChildFrame('body').find("#ul:last-child")) {
							 parent.layer.getChildFrame('body').find("#ul").prepend(str);
						}else{*/
							 parent.layer.getChildFrame('body').find("#ul:last-child").append(str);
						/*}*/
						
					}
				});
				//parent.layer.getChildFrame('body').find("#ids").val(ids);
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				 var inputs=parent.layer.getChildFrame('body').find("#ul").find("li").find("input");
				
				parent.layer.getChildFrame('body').find("#ids").val(ids);//给父页面控件赋值
				parent.layer.close(index);  // 关闭layer
			}	
				/**
				 * 查询数组有无此值
				 * @param arr
				 * @param obj
				 * @returns {Boolean}
				 */
			function contains(arr, obj) {  
			  
			    for (var int = 0; int < arr.length; int++) {
			    	
			        if (arr[int] === obj) {  
			            return true;  
			        }  
				}
			  /*  i=arr.length;
			    while (i>=0) {  
			    	i--
			        if (arr[i] === obj) {  
			            return true;  
			        }  
			    }  */
			    return false;  
			}  		