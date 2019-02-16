package com.jcwx.entity.pub;

import java.util.Date;

import com.jcwx.utils.DateUtils;

/**
 * @author gaoshuai
 *	2017年10月31日
 *	首页--热点关注
 */
public class RdgzIndexEntity {
		private String id;//热点id
		private String title;//热点标题
		private Date createTime;//创建时间
		private String lbType;//热点类别
		private String urlContent;//图片url(缩略图)
		private String content;//内容
		
		private String createTimes;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
			if(null != createTime){
				setCreateTimes(DateUtils.formateDate(createTime, "yyyy-MM-dd"));
			}
		}
		public String getLbType() {
			return lbType;
		}
		public void setLbType(String lbType) {
			this.lbType = lbType;
		}
		public String getUrlContent() {
			return urlContent;
		}
		public void setUrlContent(String urlContent) {
			this.urlContent = urlContent;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getCreateTimes() {
			return createTimes;
		}
		public void setCreateTimes(String createTimes) {
			this.createTimes = createTimes;
		}
}
