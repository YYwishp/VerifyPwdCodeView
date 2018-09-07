package com.gyx.verifypwdcodeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.lnyp.pswkeyboard.widget.VirtualKeyboardView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
	/**
	 * 数字键盘显示动画
	 */
	public Animation enterAnim;
	public Animation exitAnim;
	private VerifyPwdCodeView password;
	private VirtualKeyboardView virtualKeyboardView;
	private String passWord;
	private ArrayList<Map<String, String>> valueList;//键盘对应的数据
	private Button btGetpwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		initView();
	}

	private void initView() {

		password = (VerifyPwdCodeView) findViewById(R.id.password);


		btGetpwd = (Button) findViewById(R.id.bt_getpwd);
		btGetpwd.setOnClickListener(this);



		virtualKeyboardView = (VirtualKeyboardView) findViewById(R.id.virtualKeyboardView);

		valueList = virtualKeyboardView.getValueList();

		//是否是阿拉伯输入格式
		password.setArCountry(false);
		initAnim();
		//设置不调用系统键盘
		if (android.os.Build.VERSION.SDK_INT <= 10) {
			password.setInputType(InputType.TYPE_NULL);
		} else {
			this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			try {
				Class<EditText> cls = EditText.class;
				Method setShowSoftInputOnFocus;
				setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
				setShowSoftInputOnFocus.setAccessible(true);
				setShowSoftInputOnFocus.invoke(password, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		virtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				virtualKeyboardView.startAnimation(exitAnim);
				virtualKeyboardView.setVisibility(View.GONE);
			}
		});
		GridView gridView = virtualKeyboardView.getGridView();
		gridView.setOnItemClickListener(onItemClickListener);
		password.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				virtualKeyboardView.setFocusable(true);
				virtualKeyboardView.setFocusableInTouchMode(true);
				virtualKeyboardView.startAnimation(enterAnim);
				virtualKeyboardView.setVisibility(View.VISIBLE);
			}
		});
	}

	private long endTime = 0;
	private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
			//博主手机不好，经常点一次却触发两次`onKey`事件，就设置了一个防止多点击，间隔100毫秒。
			long startTime = System.currentTimeMillis();
			String amount = password.getTextToStirng();
			if (position < 11 && position != 9) {
				if (6 > amount.length()) {
					password.setText(amount + valueList.get(position).get("name"));
				}
			} else {
				if (position == 11) {      //点击退格键
					if (amount.length() > 0) {
						amount = amount.substring(0, amount.length() - 1);
						password.setText(amount);
					}
				}
			}
			//验证码
			passWord = password.getTextToStirng();
			if (6 == passWord.length()&& startTime - endTime > 100) {
				virtualKeyboardView.startAnimation(exitAnim);
				virtualKeyboardView.setVisibility(View.GONE);
				endTime = startTime;
			}
		}
	};

	private void initAnim() {
		enterAnim = AnimationUtils.loadAnimation(this, com.lnyp.pswkeyboard.R.anim.push_bottom_in);
		exitAnim = AnimationUtils.loadAnimation(this, com.lnyp.pswkeyboard.R.anim.push_bottom_out);
	}

	@Override
	public void onClick(View v) {

		if (6 == passWord.length()) {
			Toast.makeText(this, "验证码："+passWord, Toast.LENGTH_SHORT).show();
		}


	}
}
