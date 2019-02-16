package com.jcwx.tags;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.beetl.core.GeneralVarTagBinding;

import com.jcwx.utils.Pagenate;

/**
 * @author MaBo 2017年5月25日 自定义标签-分页 用法：参照/WEB-INF/templates/xtgl/jsgl/index.html
 *         <#pagenate func="" pagenate="" />
 */
public class PagenateTag extends GeneralVarTagBinding {

	private Logger logger = Logger.getLogger(PagenateTag.class);

	private int currPage; // 当前页
	private int firstPage; // 首页
	private int lastPage; // 尾页
	private int prePage; // 上一页
	private int nextPage; // 下一页
	private String funcName; // 要执行的JS函数名
	private List<Integer> pageNums; // 页码

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void render() {
		funcName = getAttributeValue("func").toString();
		Pagenate pagenate = (Pagenate) getAttributeValue("page");

		StringBuilder html = new StringBuilder("");
		if (funcName != null && !funcName.equals("") && pagenate != null) {
			currPage = pagenate.getPageNum();
			firstPage = pagenate.getFirstPage();
			lastPage = pagenate.getLastPage();
			prePage = pagenate.getPrePage();
			nextPage = pagenate.getNextPage();
			pageNums = pagenate.getPageNos();

			html.append("<div class='pagination justify-content-center'>");
			if (firstPage < lastPage) {
				// 只有大于一页的时候才显示分页标签
				// 只有当前页码大于首页页码时才显示“上一页”
				if (currPage > firstPage) {
					html.append("<li class='page-item '><a class='page-link' href=\"javascript:" + funcName + "('" + prePage + "')"
							+ "\" aria-label='Previous'>上一页</a></li>");
				}

				// 组织页码
				for (Integer pn : pageNums) {
					if (pn == currPage) {
						// 当前页
						html.append("<li class='page-item active'><a class='page-link'>" + pn + "</a></li>");
					} else {
						html.append(
								"<li class='page-item'><a class='page-link' href=\"javascript:" + funcName + "('" + pn + "')" + "\">" + pn + "</a></li>");
					}
				}

				// 只有当前页码小于尾页页码时才显示“下一页”
				if (currPage < lastPage) {
					html.append("<li class='page-item'><a class='page-link' href=\"javascript:" + funcName + "('" + nextPage + "')"
							+ "\" aria-label='Next'>下一页</a></li>");
				}
			}
			html.append("</div>");
		} else {
			html.append(".");//相关参数未定义
		}
		try {
			ctx.byteWriter.writeString(html.toString());
		} catch (IOException e) {
			logger.error("自定义标签-分页出错", e);
		}
	}
}
