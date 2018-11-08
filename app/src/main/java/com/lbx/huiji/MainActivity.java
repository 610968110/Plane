package com.lbx.huiji;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lbx.huiji.view.GameView;

/**
 * @author lbx
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 游戏窗口
     */
    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGameView = (GameView) findViewById(R.id.gv_main);
        //设置游戏监听
        mGameView.setOnGameListener(new GameView.OnGameListener() {
            @Override
            public void onStart() {
                //游戏开始
                mGameView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onGameOver() {
                //游戏失败
                mGameView.setVisibility(View.GONE);
            }
        });
    }

    public void again(View view) {
        //重来一次
        mGameView.again();
    }
}
