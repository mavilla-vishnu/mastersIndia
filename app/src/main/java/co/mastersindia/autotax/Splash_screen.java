package co.mastersindia.autotax;

/**
 * Created by pandu on 12/27/2014.
 */
        import android.animation.Animator;
        import android.animation.AnimatorListenerAdapter;
        import android.animation.AnimatorSet;
        import android.animation.ObjectAnimator;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MotionEvent;
        import android.widget.ImageView;
        import android.widget.Toast;

public class Splash_screen extends Activity {
    private Thread mSplashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final Splash_screen sPlashScreen = this;


        ImageView myView=(ImageView)findViewById(R.id.sp_img);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(myView, "alpha", .3f, 1f);
        fadeIn.setDuration(3000);
        final AnimatorSet mAnimationSet = new AnimatorSet();
        mAnimationSet.play(fadeIn);

        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mSplashThread.start();
            }
        });
        mAnimationSet.start();

        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){

                        wait(3000);
                    }
                }
                catch(InterruptedException ex){
                }

                finish();

            Intent intent = new Intent();
            intent.setClass(sPlashScreen, LoginActivity.class);
            startActivity(intent);
            finish();

            }
        };


    }


    @Override

    public boolean onTouchEvent(MotionEvent evt)
    {
        if(evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(mSplashThread){
                mSplashThread.notifyAll();
            }
        }
        return true;
    }

}



