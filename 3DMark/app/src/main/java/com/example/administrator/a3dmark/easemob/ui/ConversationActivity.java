package com.example.administrator.a3dmark.easemob.ui;

import android.os.Bundle;

import com.example.administrator.a3dmark.Activity.BaseActivity;
import com.example.administrator.a3dmark.R;

/**
 * chat activity，EaseChatFragment was used
 *
 */
public class ConversationActivity extends BaseActivity {
//    private ConversationListFragment conversationListFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_conversation);
//        conversationListFragment = new ConversationListFragment();
//        conversationListFragment.setArguments(getIntent().getExtras());
//        getSupportFragmentManager().beginTransaction().add(R.id.frame, conversationListFragment).commit();
        
    }
}
