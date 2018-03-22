package demo.com.pictureeffectutils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import demo.com.pictureeffectutils.utils.FastBlur;


/**
 * Created by ljp on 2018/3/22
 */
public class FastBlurActivity extends Activity {

    private final String DOWNSCALE_FILTER = "downscale_filter";
    private ImageView image;
    private TextView text;
    private LinearLayout ll;
    private Bitmap mutableBitmap ;
    private View tou;
    private FrameLayout fly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_blur);
        image = (ImageView)findViewById(R.id.picture);
        text = (TextView) findViewById(R.id.text);
        ll = (LinearLayout)findViewById(R.id.ll);
        fly = (FrameLayout) findViewById(R.id.fly);
        //Drawable tou = getDrawable(R.drawable.picture);
        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture);
        //  drawRectangles(bitmap);
        applyBlur();
    }

    /**
     * 在applyBlur()函数中我注册了onPreDrawListener()。因为在applyBlur()方法调用的时候界面还没有开始布局，
     * 所以我需要实现这个监听器,否则不能进行模糊处理。需要等到布局文件全都经过measured、laid out、displayed的时候，才能进行操作。
     */
    private void applyBlur() {
        //当一个视图树将要绘制时，所要调用的回调函数的接口类
        image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                image.getViewTreeObserver().removeOnPreDrawListener(this);
                image.buildDrawingCache();
                Bitmap bmp = image.getDrawingCache();//得到绘制缓存
                blur(bmp, ll);//ll是一个view，这个view大小，也就是这个view后面图片需要毛玻璃效果的大小
                return true;
            }
        });
    }

    @SuppressLint("NewApi")
    private void blur(Bitmap bkg, View viewBlur) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 1;
        float radius = 100;
        Bitmap overlay = Bitmap.createBitmap((int) (viewBlur.getMeasuredWidth()/scaleFactor),
                (int) (viewBlur.getMeasuredHeight()/scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-viewBlur.getLeft()/scaleFactor, -viewBlur.getTop()/scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);
        overlay = FastBlur.doBlur(overlay, (int)radius, true);
        viewBlur.setBackground(new BitmapDrawable(getResources(), overlay));
    }


    private void drawRectangles(Bitmap imageBitmap) {
        mutableBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#1A65C7"));
        //paint.setStyle(Paint.Style.STROKE);//不填充
        paint.setStrokeWidth(10);  //线的宽度
        canvas.drawRect(10,200,500,1000, paint);
        image.setImageBitmap(mutableBitmap);//img: 定义在xml布局中的ImagView控件
    }

}
