package demo.com.pictureeffectutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

import demo.com.pictureeffectutils.utils.MosaicProcessor;


/**
 * Created by ljp on 2018/3/22
 */
public class MosaicProcessorActivity extends Activity {


    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosaic_processor);
        img = (ImageView) findViewById(R.id.img);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.picture);
        Rect rect = new Rect(0, bitmap.getHeight()/2,bitmap.getWidth() , bitmap.getHeight()/2);
        Bitmap bitmap2 =  MosaicProcessor.makeMosaic(bitmap, rect,bitmap.getWidth()/17);
        img.setImageBitmap(bitmap2);

        //  getDensity() ;
    }


    /**
     * 屏幕的宽
     * @return
     */
    public  int getScreenWidth() {
        //WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        WindowManager wm = getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int width = wm.getDefaultDisplay().getWidth();

        Log.e("TAG", "屏幕的宽："+outMetrics.widthPixels+"*************"+width);
        return outMetrics.widthPixels;
    }
    /**
     * 屏幕的高
     * @return
     */
    public  int getScreenHeight() {
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        // WindowManager wm = getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int height = wm.getDefaultDisplay().getHeight();
        Log.e("TAG", "屏幕的高："+outMetrics.heightPixels+"************"+height);
        return outMetrics.heightPixels;
    }
    /**
     * 像素密度
     * @return
     */
    public int getDensity() {
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        // WindowManager wm = getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        float density = outMetrics.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        final float scale = getResources().getDisplayMetrics().density; //得到像素密度
        Log.e("TAG", "像素密度："+density+"**********"+scale);
        return outMetrics.heightPixels;
    }

    /**
     * 得到控件的宽和高
     */
    private void setRoadLineHigh() {
        ViewTreeObserver treeObserver = img.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                img.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int imgWidth = img.getWidth();
                Log.e("TAG", "控件要求的宽（match_parent）："+imgWidth);
            }
        });
    }

}
