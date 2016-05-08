package cn.lny.view;

import java.util.List;

import cn.lny.bean.ContactInfo;
import xu.ye.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HomeSettintActivity extends Activity implements OnClickListener {
	   Button mBackupContactButton;
	   Button mRecoverContactButton;

	    //标记消息的来源
	    public final int BACKUP_WHAT = 0;
	    public final int RECOVER_WHAT = 1;

	    //标记成功还是失败
	    public final int SUCCESS_FLAG = 1;
	    public final int FAIL_FLAG = 0;
	   //用于进行备份和还原操作的ContactHandler内部类
	    ContactInfo.ContactHandler handler = ContactInfo.ContactHandler.getInstance();
	    Handler mBackupAndRecoverProcessHandler = new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	            super.handleMessage(msg);
	            if (msg.what == BACKUP_WHAT){
	                //add your action
	            	  mBackupContactButton.setEnabled(true);
	                if (msg.arg1 == SUCCESS_FLAG){
//	                    mBackupContactButton.setProgress(100);
	                }else {
//	                    mBackupContactButton.setProgress(-1);
	                }
	            }else if (msg.what == RECOVER_WHAT){
	                //add your action
	            	mRecoverContactButton.setEnabled(true);
	                if (msg.arg1 == SUCCESS_FLAG){
//	                    mRecoverContactButton.setProgress(100);
	                }else {
//	                    mRecoverContactButton.setProgress(-1);
	                }
	            }
	            Toast.makeText(HomeSettintActivity.this, msg.obj.toString(),Toast.LENGTH_SHORT).show();
	        }
	    };
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_setting_page);
		 mBackupContactButton = (Button) findViewById(R.id.backup_contact_button);
	        mRecoverContactButton = (Button) findViewById(R.id.recover_contact_button);
	        initEvent();
		
	}
	   private void initEvent() {
	        mRecoverContactButton.setOnClickListener(this);
	        mBackupContactButton.setOnClickListener(this);
	    }
	@Override
	public void onClick(View v) {
	       switch (v.getId()){
           case R.id.backup_contact_button:
               backup_contact();
               break;
           case R.id.recover_contact_button:
               recover_contact();
               break;
           default:
               break;
       }		
	}
	
	 public void backup_contact(){
	        //让按钮进入工作状态
	        mBackupContactButton.setEnabled(false);
	   
	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	                //新建一条Handler处理的消息
	                Message message = new Message();
	                try{
	                    // 进行备份联系人信息动作
	                    handler.backupContacts(HomeSettintActivity.this, handler.getContactInfo(HomeSettintActivity.this));
	                    //如果顺利，则将消息的参数设置为成功
	                    message.obj = "backup success";
	                    message.arg1 = SUCCESS_FLAG;
	                }catch (Exception e){
	                    //如果出现异常，则将消息的参数设置为失败
	                    message.obj = "backup fail";
	                    message.arg1 = FAIL_FLAG;
	                    e.printStackTrace();
	                }finally {
	                    //最后设置消息来源并发送
	                    message.what = BACKUP_WHAT;
	                    mBackupAndRecoverProcessHandler.sendMessage(message);
	                  
	                }
	            }
	        }).start();
	    }

	    //与backup基本相同，不再注释
	    public void recover_contact(){
	        mRecoverContactButton.setEnabled(false);
	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	                Message message = new Message();
	                try {
	                    // 获取要恢复的联系人信息
	                    List<ContactInfo> infoList = handler.restoreContacts();
	                    for (ContactInfo contactInfo : infoList) {
	                        // 恢复联系人
	                        handler.addContacts(HomeSettintActivity.this, contactInfo);
	                    }
	                    message.obj = "recover success";
	                    message.arg1 = SUCCESS_FLAG;
	                } catch (Exception e) {
	                    message.obj = "recover fail";
	                    message.arg1 = FAIL_FLAG;
	                    e.printStackTrace();
	                }finally {
	                    message.what = RECOVER_WHAT;
	                    mBackupAndRecoverProcessHandler.sendMessage(message);
	                }
	            }
	        }).start();
	    }
}
