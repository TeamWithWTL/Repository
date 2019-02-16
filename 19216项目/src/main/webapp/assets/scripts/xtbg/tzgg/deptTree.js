/**
 * 转发管理-部门人员
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
				$("#bmry").load(basePath + "/xtbg/tzgg/treeIndex.do",{'ajaxCmd': 'table', 'pageNumber': pageNumber, 'name': name,'deptId':deptId}, function(){
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
			/**
			 * 派发
			 * @param pid
			 * @param cid
			 */
function payOut(pid,tid,pname){
				
				var msgIndex = top.layer.msg('派发中...', {
					icon : 16,
					shade : [ 0.5, '#999999' ]
				});
				$.ajax({
					type : "POST",
					url : basePath + "/xtbg/tzgg/payOut.do",
					data : {'pname':pname,
						'pid':pid,
						'tid':tid
						
						},
					error : function(data) {
						top.layer.close(msgIndex);
						top.layer.msg('派发成功', {shade : [ 0.5, '#999999' ]});
					},
					success : function(data) {
						top.layer.close(msgIndex);
						if (data == "success") {
							top.layer.msg('派发成功', {
								icon : 6,
								time : 2000,
								shade : [ 0.5, '#999999' ]
							}, function() {
								initData(1);
								var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
								parent.layer.close(index);  // 关闭layer
							});
						}else if (data == "succ") {
							top.layer.msg('已经派发，无需处理', {
								icon : 6,
								time : 2000,
								shade : [ 0.5, '#999999' ]
							}, function() {
								initData(1);
								var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
								parent.layer.close(index);  // 关闭layer
							});
						} else {
							top.layer.msg('派发失败，请联系管理员', {shade : [ 0.5, '#999999' ]});
							var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
							parent.layer.close(index);  // 关闭layer
						}
					}
				});
				
				/*var ids = '';	// 要转发的id
				// 获取被选中的ID
				var chkItems = $('input[name="icheck"]');
				$.each(chkItems, function(index, chkItem){
					if($(chkItem).is(":checked")){
						ids += $(chkItem).val() + ';' ;
					}
				});
				var id = ids.split(";");
				if(id == ''){
					top.layer.confirm("请选择要转发的记录",{
						 icon:0,
						 title:"提示"
					});
					return false;
				}
				if(id.length > 2){
					top.layer.confirm("只能选择一条记录",{
						 icon:0,
						 title:"提示"
					});
					return false;
				}
				alert(id);*/
			}