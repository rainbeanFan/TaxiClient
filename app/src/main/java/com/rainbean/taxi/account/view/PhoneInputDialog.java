package com.rainbean.taxi.account.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;



import com.rainbean.mytaxi.R;
import com.rainbean.taxi.main.view.MainActivity;
import com.rainbean.taxi.common.util.FormatUtil;

public class PhoneInputDialog extends Dialog {

    private View mRoot;
    private EditText mPhone;
    private Button mButton;
    private MainActivity mainActivity;
    public PhoneInputDialog(MainActivity mainActivity) {
        this(mainActivity, R.style.Dialog);
        this.mainActivity = mainActivity;
    }
    public PhoneInputDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot  = inflater.inflate(R.layout.dialog_phone_input, null);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        mRoot.setLayoutParams(params);

        setContentView(mRoot);
        initListener();
    }

    private void initListener() {

        mButton = (Button) findViewById(R.id.btn_next);
        mButton.setEnabled(false);
        mPhone = (EditText) findViewById(R.id.phone);
        //  手机号输入框组册监听检查手机号输入是否合法
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                check();
            }
        });
        // 按钮注册监听
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone =  mPhone.getText().toString();
                SmsCodeDialog dialog = new SmsCodeDialog(mainActivity, phone);
                dialog.show();
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        PhoneInputDialog.this.dismiss();
                    }
                });



            }
        });


        // 关闭按钮注册监听事件

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneInputDialog.this.dismiss();
            }
        });
    }

    private void check(){
        String phone =  mPhone.getText().toString();
        boolean legal = FormatUtil.checkMobile(phone);
        mButton.setEnabled(legal);

    }


    @Override
    public void show() {
        super.show();

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();

        Display display = mainActivity.getWindowManager().getDefaultDisplay();

        layoutParams.width= (int) (display.getWidth()*0.8);
        layoutParams.height= LinearLayout.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(layoutParams);

    }
}
