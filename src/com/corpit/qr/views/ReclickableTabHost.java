package com.corpit.qr.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TabHost;



/**
 *****************************************************
 * <hr>
 * <dt><span class="strong">类功能简介:</span></dt>
 * <dd>Coffee</dd>
 * <dt><span class="strong">创建时间:</span></dt>
 * <dd>2015-1-30 上午9:54:12</dd>
 * <dt><span class="strong">公司:</span></dt>
 * <dd>CorpIt</dd>
 * @author aa1000777 - Email:aa1000777@qq.com
 *****************************************************
*/



public class ReclickableTabHost extends TabHost {

	private OnSameTabClickedListener listener;
	
    public ReclickableTabHost(Context context) {
        super(context);
    }

    public ReclickableTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public void setOnSameTabClickedListener(OnSameTabClickedListener l){
    	listener = l;
    }

    @Override
    public void setCurrentTab(int index) {
        if (index == getCurrentTab()) {
        	if(listener != null){
        		listener.onTabReclicked(index);
        	}       
        } 
        super.setCurrentTab(index);
    }
    
    public interface OnSameTabClickedListener 
    {
        void onTabReclicked(int index);
    }
}

