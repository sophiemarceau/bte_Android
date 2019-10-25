package com.hyphenate.easeui.widget;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.easeui.R;
import com.hyphenate.util.EMLog;

public abstract class EaseChatPrimaryMenuBase extends RelativeLayout{
    protected EaseChatPrimaryMenuListener listener;
    protected Activity activity;
    protected InputMethodManager inputManager;

    public EaseChatPrimaryMenuBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public EaseChatPrimaryMenuBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EaseChatPrimaryMenuBase(Context context) {
        super(context);
        init(context);
    }
    
    private void init(Context context){
        this.activity = (Activity) context;
        inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    
    /**
     * set primary menu listener
     * @param listener
     */
    public void setChatPrimaryMenuListener(EaseChatPrimaryMenuListener listener){
        this.listener = listener;
    }
    
    /**
     * emoji icon input event
     * @param emojiContent
     */
    public abstract void onEmojiconInputEvent(CharSequence emojiContent);

    /**
     * emoji icon delete event
     */
    public abstract void onEmojiconDeleteEvent();
    
    /**
     * hide extend menu
     */
    public abstract void onExtendMenuContainerHide();
    
    
    /**
     * insert text
     * @param text
     */
    public abstract void onTextInsert(CharSequence text);
    
    public abstract EditText getEditText();
    
    /**
     * hide keyboard
     */
    public void hideKeyboard() {
        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    
    
    public interface EaseChatPrimaryMenuListener{
        /**
         * when send button clicked
         * @param content
         */
        void onSendBtnClicked(String content);
        
        /**
         * when speak button is touched
         * @return
         */
        boolean onPressToSpeakBtnTouch(View v, MotionEvent event);
        
        /**
         * toggle on/off voice button
         */
        void onToggleVoiceBtnClicked();
        
        /**
         * toggle on/off extend menu
         */
        void onToggleExtendClicked();
        
        /**
         * toggle on/off emoji icon
         */
        void onToggleEmojiconClicked();
        
        /**
         * on text input is clicked
         */
        void onEditTextClicked();
        
    }

    /**
     * primary menu
     *
     */
    public static class EaseChatPrimaryMenu extends EaseChatPrimaryMenuBase implements OnClickListener {
        private EditText editText;
        private View buttonSetModeKeyboard;
        private RelativeLayout edittext_layout;
        private View buttonSetModeVoice;
        private View buttonSend;
        private View buttonPressToSpeak;
        private ImageView faceNormal;
        private ImageView faceChecked;
        private Button buttonMore;
        private boolean ctrlPress = false;

        public EaseChatPrimaryMenu(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init(context, attrs);
        }

        public EaseChatPrimaryMenu(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public EaseChatPrimaryMenu(Context context) {
            super(context);
            init(context, null);
        }

        private void init(final Context context, AttributeSet attrs) {
            Context context1 = context;
            LayoutInflater.from(context).inflate(R.layout.ease_widget_chat_primary_menu, this);
            editText = (EditText) findViewById(R.id.et_sendmessage);
            buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
            edittext_layout = (RelativeLayout) findViewById(R.id.edittext_layout);
            buttonSetModeVoice = findViewById(R.id.btn_set_mode_voice);
            buttonSend = findViewById(R.id.btn_send);
            buttonPressToSpeak = findViewById(R.id.btn_press_to_speak);
            faceNormal = (ImageView) findViewById(R.id.iv_face_normal);
            faceChecked = (ImageView) findViewById(R.id.iv_face_checked);
            RelativeLayout faceLayout = (RelativeLayout) findViewById(R.id.rl_face);
            buttonMore = (Button) findViewById(R.id.btn_more);
            edittext_layout.setBackgroundResource(R.drawable.ease_input_bar_bg_normal);

            buttonSend.setOnClickListener(this);
            buttonSetModeKeyboard.setOnClickListener(this);
            buttonSetModeVoice.setOnClickListener(this);
            buttonMore.setOnClickListener(this);
            faceLayout.setOnClickListener(this);
            editText.setOnClickListener(this);
            editText.requestFocus();

            editText.setOnFocusChangeListener(new OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        edittext_layout.setBackgroundResource(R.drawable.ease_input_bar_bg_active);
                    } else {
                        edittext_layout.setBackgroundResource(R.drawable.ease_input_bar_bg_normal);
                    }

                }
            });
            // listen the text change
            editText.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!TextUtils.isEmpty(s)) {
                        buttonMore.setVisibility(View.GONE);
                        buttonSend.setVisibility(View.VISIBLE);
                    } else {
                        buttonMore.setVisibility(View.VISIBLE);
                        buttonSend.setVisibility(View.GONE);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            editText.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    EMLog.d("key", "keyCode:" + keyCode + " action:" + event.getAction());

                    // test on Mac virtual machine: ctrl map to KEYCODE_UNKNOWN
                    if (keyCode == KeyEvent.KEYCODE_UNKNOWN) {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            ctrlPress = true;
                        } else if (event.getAction() == KeyEvent.ACTION_UP) {
                            ctrlPress = false;
                        }
                    }
                    return false;
                }
            });

            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    EMLog.d("key", "keyCode:" + event.getKeyCode() + " action" + event.getAction() + " ctrl:" + ctrlPress);
                    if (actionId == EditorInfo.IME_ACTION_SEND ||
                            (event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
                             event.getAction() == KeyEvent.ACTION_DOWN &&
                             ctrlPress == true)) {
                        String s = editText.getText().toString();
                        editText.setText("");
                        listener.onSendBtnClicked(s);
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            });


            buttonPressToSpeak.setOnTouchListener(new OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(listener != null){
                        return listener.onPressToSpeakBtnTouch(v, event);
                    }
                    return false;
                }
            });
        }

        /**
         * set recorder view when speak icon is touched
         * @param voiceRecorderView
         */
        public void setPressToSpeakRecorderView(EaseVoiceRecorderView voiceRecorderView){
            EaseVoiceRecorderView voiceRecorderView1 = voiceRecorderView;
        }

        /**
         * append emoji icon to editText
         * @param emojiContent
         */
        public void onEmojiconInputEvent(CharSequence emojiContent){
            editText.append(emojiContent);
        }

        /**
         * delete emojicon
         */
        public void onEmojiconDeleteEvent(){
            if (!TextUtils.isEmpty(editText.getText())) {
                KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                editText.dispatchKeyEvent(event);
            }
        }

        /**
         * on clicked event
         * @param view
         */
        @Override
        public void onClick(View view){
            int id = view.getId();
            if (id == R.id.btn_send) {
                if(listener != null){
                    String s = editText.getText().toString();
                    editText.setText("");
                    listener.onSendBtnClicked(s);
                }
            } else if (id == R.id.btn_set_mode_voice) {
                setModeVoice();
                showNormalFaceImage();
                if(listener != null)
                    listener.onToggleVoiceBtnClicked();
            } else if (id == R.id.btn_set_mode_keyboard) {
                setModeKeyboard();
                showNormalFaceImage();
                if(listener != null)
                    listener.onToggleVoiceBtnClicked();
            } else if (id == R.id.btn_more) {
                buttonSetModeVoice.setVisibility(View.VISIBLE);
                buttonSetModeKeyboard.setVisibility(View.GONE);
                edittext_layout.setVisibility(View.VISIBLE);
                buttonPressToSpeak.setVisibility(View.GONE);
                showNormalFaceImage();
                if(listener != null)
                    listener.onToggleExtendClicked();
            } else if (id == R.id.et_sendmessage) {
                edittext_layout.setBackgroundResource(R.drawable.ease_input_bar_bg_active);
                faceNormal.setVisibility(View.VISIBLE);
                faceChecked.setVisibility(View.INVISIBLE);
                if(listener != null)
                    listener.onEditTextClicked();
            } else if (id == R.id.rl_face) {
                toggleFaceImage();
                if(listener != null){
                    listener.onToggleEmojiconClicked();
                }
            } else {
            }
        }


        /**
         * show voice icon when speak bar is touched
         *
         */
        protected void setModeVoice() {
            hideKeyboard();
            edittext_layout.setVisibility(View.GONE);
            buttonSetModeVoice.setVisibility(View.GONE);
            buttonSetModeKeyboard.setVisibility(View.VISIBLE);
            buttonSend.setVisibility(View.GONE);
            buttonMore.setVisibility(View.VISIBLE);
            buttonPressToSpeak.setVisibility(View.VISIBLE);
            faceNormal.setVisibility(View.VISIBLE);
            faceChecked.setVisibility(View.INVISIBLE);

        }

        /**
         * show keyboard
         */
        protected void setModeKeyboard() {
            edittext_layout.setVisibility(View.VISIBLE);
            buttonSetModeKeyboard.setVisibility(View.GONE);
            buttonSetModeVoice.setVisibility(View.VISIBLE);
            // mEditTextContent.setVisibility(View.VISIBLE);
            editText.requestFocus();
            // buttonSend.setVisibility(View.VISIBLE);
            buttonPressToSpeak.setVisibility(View.GONE);
            if (TextUtils.isEmpty(editText.getText())) {
                buttonMore.setVisibility(View.VISIBLE);
                buttonSend.setVisibility(View.GONE);
            } else {
                buttonMore.setVisibility(View.GONE);
                buttonSend.setVisibility(View.VISIBLE);
            }

        }


        protected void toggleFaceImage(){
            if(faceNormal.getVisibility() == View.VISIBLE){
                showSelectedFaceImage();
            }else{
                showNormalFaceImage();
            }
        }

        private void showNormalFaceImage(){
            faceNormal.setVisibility(View.VISIBLE);
            faceChecked.setVisibility(View.INVISIBLE);
        }

        private void showSelectedFaceImage(){
            faceNormal.setVisibility(View.INVISIBLE);
            faceChecked.setVisibility(View.VISIBLE);
        }


        @Override
        public void onExtendMenuContainerHide() {
            showNormalFaceImage();
        }

        @Override
        public void onTextInsert(CharSequence text) {
           int start = editText.getSelectionStart();
           Editable editable = editText.getEditableText();
           editable.insert(start, text);
           setModeKeyboard();
        }

        @Override
        public EditText getEditText() {
            return editText;
        }

    }
}
