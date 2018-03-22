package demo.com.pictureeffectutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by ljp on 2018/3/22
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private Button btn_blur,btn_mosaic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_blur = findViewById(R.id.btn_blur);
        btn_mosaic = findViewById(R.id.btn_mosaic);
        btn_blur.setOnClickListener(this);
        btn_mosaic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_blur:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FastBlurActivity.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.btn_mosaic:
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, MosaicProcessorActivity.class);
                MainActivity.this.startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
