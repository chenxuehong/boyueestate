package com.huihe.usercenter.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huihe.usercenter.R;
import com.kotlin.base.utils.DensityUtils;

public class EditInputView extends FrameLayout {

    private EditText etText;
    private ImageView ivClear;
    private ImageView ivSee;

    public EditInputView(@NonNull Context context) {
        this(context,null);
    }

    public EditInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EditInputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       int mTipTextSize = (int) DensityUtils.sp2px(context, 10);
        View.inflate(context, R.layout.layout_edit_inputview,this);
        etText = findViewById(R.id.layout_edit_inputview_et_text);
        ivClear = findViewById(R.id.layout_edit_inputview_iv_clear);
        ivSee = findViewById(R.id.layout_edit_inputview_iv_see);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditInputView);
        String hint = typedArray.getString(R.styleable.EditInputView_hint);
        String inputType = typedArray.getString(R.styleable.EditInputView_inputType);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.EditInputView_textSize, mTipTextSize);
        etText.setHint(hint);
        etText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        ivSee.setOnClickListener(new OnClickListener() {
            int iconid = R.drawable.hide_pw;
            @Override
            public void onClick(View v) {
                iconid = iconid == R.drawable.see ? R.drawable.hide_pw : R.drawable.see;
                ivSee.setImageResource(iconid);
                if (iconid == R.drawable.see) {
                    etText.setInputType(InputType.TYPE_CLASS_NUMBER);
                } else {
                    etText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                etText.setSelection(etText.getText().length());
            }
        });
        ivClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etText.setText("");
                ivClear.setVisibility(View.GONE);
            }
        });
        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                etText.setVisibility(s.toString().length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
