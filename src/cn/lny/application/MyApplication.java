package cn.lny.application;

import java.util.List;

import android.content.Intent;
import cn.lny.bean.ContactBean;
import cn.lny.service.T9Service;

public class MyApplication extends android.app.Application {

	
	private List<ContactBean> contactBeanList;
	
	public List<ContactBean> getContactBeanList() {
		return contactBeanList;
	}
	public void setContactBeanList(List<ContactBean> contactBeanList) {
		this.contactBeanList = contactBeanList;
	}

	public void onCreate() {
		
		System.out.println("项目启动");
		
		Intent startService = new Intent(MyApplication.this, T9Service.class);
		startService(startService);
	}
}
