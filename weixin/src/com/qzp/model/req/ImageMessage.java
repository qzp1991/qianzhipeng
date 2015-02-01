package com.qzp.model.req;

	/**
	 * 图片消息
	 * 
	 * @author 
	 * @date 2014-07-10
	 */
	public class ImageMessage extends BaseMessage {
		// 图片链接
		private String PicUrl;
	
		public String getPicUrl() {
			return PicUrl;
		}
	
		public void setPicUrl(String picUrl) {
			PicUrl = picUrl;
		}
}
