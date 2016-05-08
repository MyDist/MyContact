package cn.lny.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.lny.R;
import cn.lny.uitl.SharedPreferencesUtil;

public class PassWordAcitivity extends Activity implements OnClickListener {
	private EditText editText1, editText2, editText3;
	private Button button1, button2, button3;
	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password);

		init();

	}

	private void init() {

		ll = (LinearLayout) findViewById(R.id.ll);

		editText1 = (EditText) findViewById(R.id.editText1);
		editText2 = (EditText) findViewById(R.id.editText2);
		editText3 = (EditText) findViewById(R.id.editText3);

		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);

		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);

		if (SharedPreferencesUtil.getBoolean(this)) {
			editText1.setHint("请输入旧密码");
			editText2.setHint("请输入新密码");
			editText3.setVisibility(View.VISIBLE);
			ll.setVisibility(View.VISIBLE);
			button1.setVisibility(View.GONE);
			editText3.setHint("请再次输入");
		} else {
			editText1.setHint("请输入6位密码");
			editText2.setHint("再次输入");
			editText3.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:// 确定
			String pwd1 = editText1.getText().toString().trim();
			String pwd2 = editText2.getText().toString().trim();
			if (pwd1.equals(pwd2)) {
				if (pwd1.length() == 6) {
					showToast("设置成功");
					SharedPreferencesUtil.setBoolean(this, true);
					SharedPreferencesUtil.setPwd(this, pwd1);
					finish();
				} else {
					showToast("请输入6位密码");
				}
			} else {
				showToast("两次密码不一致");
				editText1.setText("");
				editText2.setText("");
				editText3.setText("");
			}
			break;
		case R.id.button2:// 确定更改
			String pwd3 = editText1.getText().toString().trim();
			String pwd4 = editText2.getText().toString().trim();
			String pwd5 = editText3.getText().toString().trim();
			if (SharedPreferencesUtil.getPwd(this).equals(pwd3)) {
				if (pwd4.equals(pwd5)) {
					showToast("更改成功");
					SharedPreferencesUtil.setPwd(this, pwd4);
					finish();
				} else {
					showToast("两次密码不一致");
					editText1.setText("");
					editText2.setText("");
					editText3.setText("");
				}
			} else {
				showToast("密码错误");
				editText1.setText("");
				editText2.setText("");
				editText3.setText("");
			}
			break;
		case R.id.button3:// 清除密码
			String pwd6 = editText1.getText().toString().trim();
			if (SharedPreferencesUtil.getPwd(this).equals(pwd6)) {
				SharedPreferencesUtil.setPwd(this, "");
				showToast("密码已清除");
				SharedPreferencesUtil.setBoolean(this, false);
				SharedPreferencesUtil.setPwd(this, "");
				finish();
			} else {
				showToast("密码不正确");
			}
			break;

		}
	}
	
	private void showToast(String message){
		Toast toast = new Toast(this);
		View view = getLayoutInflater().inflate(R.layout.toastview, null);
		TextView text = (TextView) view.findViewById(R.id.text);
		text.setText(message);
		text.setTextColor(Color.RED);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.show();
	}

}
