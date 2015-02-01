package com.qzp.model.extend;

public class MusicHelper {

	public static String getMusicHelper() { 
	StringBuffer buffer = new StringBuffer();  
    buffer.append("[爱你]歌曲点播操作指南：").append("\n\n");  
    buffer.append("1. 歌曲+歌曲名").append("\n"); 
    buffer.append("如： 歌曲小苹果").append("\n\n");
    buffer.append("2. 歌曲+歌曲名+@歌手").append("\n");  
    buffer.append("如： 歌曲存在@汪峰").append("\n");  
    
    return buffer.toString();  
  }
}
