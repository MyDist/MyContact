package cn.lny.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cn.lny.uitl.SharedPreferencesUtil;
import cn.lny.R;

public class LogInActivity extends Activity {
	private EditText et_pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		init();
	}

	private void init() {
		if (SharedPreferencesUtil.getBoolean(this)) {
			et_pwd = (EditText) findViewById(R.id.et_pwd);
		} else {
			startActivity(new Intent(this, HomeTabHostAcitivity.class));
			finish();
		}
	}

	public void click(View v) {
		String pwd = et_pwd.getText().toString().trim();
		if (SharedPreferencesUtil.getPwd(this).equals(pwd)) {
			startActivity(new Intent(this, HomeTabHostAcitivity.class));
			finish();
		} else {
			Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
			et_pwd.setText("");
		}
	}

}
