package com.achers.mvpdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.achers.mvpdemo.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Achers on 2017/11/27.
 * 芝麻信用圆环View
 */



public class NewCreditSesameView extends View {

    // 圆环的信用等级文本
    public String[] sesameStr = new String[] {
            "350", "较差",
            "550", "中等",
            "600", "良好",
            "650", "优秀",
            "700", "极好",
            "950"
    };
    // 默认宽高值
    private int defaultSize;
    // 距离圆环的值
    private int arcDistance;
    // view宽度
    private int width;
    // view高度
    private int height;
    // 默认Padding值
    private final static int deaultPadding =20;
    //  圆环起始角度
    private final static float mStartAngle=165f;
    // 圆环结束角度
    private final static float mEndAngle=210f;
    //外层圆环画笔
    private Paint mMiddleArcPaint;
    //内层圆环画笔
    private Paint mInnerArcPaint;
    //大刻度画笔
    private Paint mCalibrationPaint;

    //小刻度画笔
    private Paint mSmallCalibrationPaint;
    //信用等级文本画笔
    private Paint mTextPaint;
    //小刻度文本画笔
    private Paint mCalibrationTextPaint;
    //进度圆环画笔
    private Paint mArcProgressPaint;

    //半径
    private int radius;

    //外层矩形
    private RectF mMiddleRect;

    //内层矩形
    private RectF mInnerRect;

    //进度矩形
    private RectF mMiddleProgressRect;

    // 最小数字
    private int mMinNum = 0;

    // 最大数字
    private int mMaxNum = 950;

    // 当前进度
    private float mCurrentAngle = 0f;

    //总进度
    private float mTotalAngle = 210f;

    //信用等级
    private String sesameLevel = "";

    //评估时间
    private String evaluationTime = "";

    //小圆点
    private Bitmap bitmap;

    //当前点的实际位置
    private float[] pos;

    //当前点的tangent值
    private float[] tan;

    //矩阵
    private Matrix matrix;

    //小圆点画笔
    private Paint mBitmapPaint;





    public NewCreditSesameView(Context context) {
        this(context,null);
    }

    public NewCreditSesameView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NewCreditSesameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }

    public int dp2px(int values){
        float density =getResources().getDisplayMetrics().density;
        return (int) (values*density+0.5f);


    }

    /**
     *参数初始化
     */
    private void init() {

        defaultSize=dp2px(250);
        arcDistance=dp2px(14);
        //外层圆环画笔
        mMiddleArcPaint =new Paint(Paint.ANTI_ALIAS_FLAG);//设置抗锯齿
        mMiddleArcPaint.setStrokeWidth(8);//设置笔的宽度
        mMiddleArcPaint.setColor(Color.WHITE);//设置颜色
//        Paint.Style.STROKE 只绘制图形轮廓（描边）
//        Paint.Style.FILL 只绘制图形内容
//        Paint.Style.FILL_AND_STROKE 既绘制轮廓也绘制内容
        mMiddleArcPaint.setStyle(Paint.Style.STROKE);
        mMiddleArcPaint.setAlpha(80);

        //内层圆环画笔
        mInnerArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerArcPaint.setStrokeWidth(30);
        mInnerArcPaint.setColor(Color.WHITE);
        mInnerArcPaint.setAlpha(80);
        mInnerArcPaint.setStyle(Paint.Style.STROKE);

        //正中间字体画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        //圆环大刻度画笔
        mCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCalibrationPaint.setStrokeWidth(4);
        mCalibrationPaint.setStyle(Paint.Style.STROKE);
        mCalibrationPaint.setColor(Color.WHITE);
        mCalibrationPaint.setAlpha(120);

        //圆环小刻度画笔
        mSmallCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallCalibrationPaint.setStrokeWidth(1);
        mSmallCalibrationPaint.setStyle(Paint.Style.STROKE);
        mSmallCalibrationPaint.setColor(Color.WHITE);
        mSmallCalibrationPaint.setAlpha(130);

        //圆环刻度文本画笔
        mCalibrationTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCalibrationTextPaint.setTextSize(30);
        mCalibrationTextPaint.setColor(Color.WHITE);

        //外层进度画笔
        mArcProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcProgressPaint.setStrokeWidth(8);
        mArcProgressPaint.setColor(Color.WHITE);
        mArcProgressPaint.setStyle(Paint.Style.STROKE);
        mArcProgressPaint.setStrokeCap(Paint.Cap.ROUND);

        mBitmapPaint = new Paint();
        mBitmapPaint.setStyle(Paint.Style.FILL);
        mBitmapPaint.setAntiAlias(true);

        //初始化小圆点图片
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_circle);
        pos = new float[2];
        tan = new float[2];
        matrix = new Matrix();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveMesaure(widthMeasureSpec,defaultSize),
                resolveMesaure(heightMeasureSpec,defaultSize));
    }
    /**
     * 根据传入的值进行测量
     * 其实就是返回三种模式的值
     */
    private int resolveMesaure(int measureSpec, int defaultSize) {
        int result=0;
        int specSize =MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)){
            /**
             * MeasureSpec.UNSPECIFIED是未指定尺寸，
             * 这种情况不多，一般都是父控件是AdapterView，
             * 通过measure方法传入的模式。
             */
            case MeasureSpec.UNSPECIFIED:

                result= defaultSize;
                break;
            /**
             * MeasureSpec.AT_MOST是最大尺寸，
             * 当控件的layout_width或layout_height指定为WRAP_CONTENT时，
             * 控件大小一般随着控件的子空间或内容进行变化，
             * 此时控件尺寸只要不超过父控件允许的最大尺寸即可。
             * 因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
             */
            case MeasureSpec.AT_MOST:
                //设置warp_content时的默认值
                result=Math.min(specSize,defaultSize);
                break;
            /**
             * MeasureSpec.EXACTLY是精确尺寸，
             * 当我们将控件的layout_width或layout_height指定为具体数值时如andorid:layout_width="50dip"，
             * 或者为FILL_PARENT是，都是控件大小已经确定的情况，
             * 都是精确尺寸。
             */
            case MeasureSpec.EXACTLY:
                //设置math_parent 和设置了固定宽高值
                break;

        }

        return result;

    }

    /**
     * 这个方法在界面的尺寸更改的时候会被调用，
     * 一般是在屏幕旋转的时候会被调用，
     * 有两个新w/h和旧w/h会被传入，
     * 这里我用来实现初始化绘图的时候边框
     * （RecF类型，具体见Canvas绘图中的Oval和矩形绘制类）等。
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
        radius = width / 2;
        //外层矩形
        mMiddleRect =new RectF(deaultPadding,deaultPadding,
                width-deaultPadding,
                height-deaultPadding);

        //内层矩形
        mInnerRect =new RectF(deaultPadding+arcDistance,
                deaultPadding+arcDistance,
                width-deaultPadding-arcDistance,
                height-deaultPadding-arcDistance);
        //进度矩形
        mMiddleProgressRect = new RectF(
                deaultPadding, deaultPadding,
                width - deaultPadding, height - deaultPadding);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawMiddleArc(canvas);
        drawInnerArc(canvas);
        drawSmallCalibration(canvas);
        drawRingProgress(canvas);
        drawCenterText(canvas);
        drawCalibrationAndText(canvas);
    }




    /**
     * 绘制外层圆环
     */
    private void drawMiddleArc(Canvas canvas) {
        canvas.drawArc(mMiddleRect,mStartAngle,mEndAngle,false,mMiddleArcPaint);
    }
    /**
     * 绘制内层圆环
     */
    private void drawInnerArc(Canvas canvas) {

        canvas.drawArc(mInnerRect, mStartAngle, mEndAngle, false, mInnerArcPaint);
    }

    /**
     * 绘制内层小刻度
     */
    private void drawSmallCalibration(Canvas canvas) {

        //反三角函数Math.atan()求出来的不是°数，所以要转化。
        canvas.save();//保存画布  转一次保存一次上一次
        /**
         *  rotate()这个函数要注意，参数为正则顺时针，否则逆时针。
         * 这个参数要求必须是度数，如旋转90°，就填90，不能填pi/2.
         * 可以用Math.toDegrees() 和 Math.toRadians()互相转化 ° 和 弧度。
         */
        //旋转画布
        canvas.rotate(-105,radius,radius);
        //计算刻度线的起点结束点
        int startDst = (int) (deaultPadding+arcDistance - mInnerArcPaint.getStrokeWidth() / 2-1 );
        int endDst= (int) (startDst+mInnerArcPaint.getStrokeWidth());
        //每旋转6度绘制一个小刻度
        for (int i=0;i<=35;i++){
            canvas.drawLine(radius,startDst,radius,endDst,mSmallCalibrationPaint);
            canvas.rotate(6,radius,radius);
        }
        canvas.restore();
    }
    /**
     * 绘制外层圆环进度和小圆点
     */
    private void drawRingProgress(Canvas canvas) {
        Path path =new Path();
        /**
         * path.addArc方法用于绘制圆弧，这个圆弧取自RectF矩形的内接椭圆上的一部分，圆弧长度由后两个参数决定

         startAngle：起始位置的角度值

         sweepAngle：旋转的角度值
         */
        path.addArc(mMiddleProgressRect,mStartAngle,mCurrentAngle);
        /**
         * PathMeasure (Path path, boolean forceClosed)
         * forceClosed参数
         *简单的说，就是Path最终是否需要闭合，如果为True的话，则不管关联的Path是否是闭合的，都会被闭合。
         * forceClosed参数对绑定的Path不会产生任何影响，例如一个折线段的Path，本身是没有闭合的，forceClosed设置为True的时候，PathMeasure计算的Path是闭合的，但Path本身绘制出来是不会闭合的。
         forceClosed参数对PathMeasure的测量结果有影响，还是例如前面说的一个折线段的Path，本身没有闭合，forceClosed设置为True，PathMeasure的计算就会包含最后一段闭合的路径，与原来的Path不同。
         参考资料 http://www.jianshu.com/p/3efa5341abcc
         */
        PathMeasure pathMeasure =new PathMeasure(path,false);
        pathMeasure.getPosTan(pathMeasure.getLength()*1,pos,tan);

        matrix.reset();
        matrix.postTranslate(pos[0]-bitmap.getWidth()/2,pos[1]-bitmap.getHeight()/2);
        canvas.drawPath(path,mArcProgressPaint);
        //起始角度不为0时候才进行绘制小圆点
        if (mCurrentAngle == 0) {
            return;
        }
        canvas.drawBitmap(bitmap,matrix,mBitmapPaint);
        mBitmapPaint.setColor(Color.WHITE);
        canvas.drawCircle(pos[0],pos[1],8,mBitmapPaint);


    }
    /**
     * 绘制中间文本
     */
    private void drawCenterText(Canvas canvas) {
        //绘制Logo
        mTextPaint.setTextSize(40);
        canvas.drawText("BETA", radius, radius - 130, mTextPaint);

        //绘制信用分数
        mTextPaint.setTextSize(180);
        mTextPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText(String.valueOf(mMinNum), radius, radius + 70, mTextPaint);

        //绘制信用级别
        mTextPaint.setTextSize(80);
        canvas.drawText(sesameLevel, radius, radius + 160, mTextPaint);

        //绘制评估时间
        mTextPaint.setTextSize(30);
        canvas.drawText(evaluationTime, radius, radius + 205, mTextPaint);
    }
    /**
     * 绘制刻度 大刻度
     */
    private void drawCalibrationAndText(Canvas canvas) {
        //旋转画布进行绘制对应的刻度
        canvas.save();
        canvas.rotate(-105, radius, radius);
        //计算刻度线的起点结束点
        int startDst = (int) (deaultPadding + arcDistance - mInnerArcPaint.getStrokeWidth() / 2 - 1);
        int endDst = (int) (startDst + mInnerArcPaint.getStrokeWidth());

        //刻度旋转的角度
        int rotateAngle = 210 / 10;
        for (int i = 1; i < 12; i++) {
            if (i % 2 != 0) {
                canvas.drawLine(radius, startDst, radius, endDst, mCalibrationPaint);
            }
            // 测量文本的长度
            float textLen = mCalibrationTextPaint.measureText(sesameStr[i - 1]);
            canvas.drawText(sesameStr[i - 1], radius - textLen / 2, endDst + 40, mCalibrationTextPaint);
            canvas.rotate(rotateAngle, radius, radius);
        }
    }

    public void setSesameValues(int values) {

        if (values <= 350) {
            mMaxNum = values;
            mTotalAngle = 0f;
            sesameLevel = "信用较差";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 550) {
            mMaxNum = values;
            mTotalAngle = (values - 350) * 80 / 400f + 2;
            sesameLevel = "信用较差";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 700) {
            mMaxNum = values;
            if (values > 550 && values <= 600) {
                sesameLevel = "信用中等";
                mTotalAngle = (values - 550) * 120 / 150f + 43;
            } else if (values > 600 && values <= 650) {
                sesameLevel = "信用良好";
                mTotalAngle = (values - 550) * 120 / 150f + 45;
            } else {
                sesameLevel = "信用优秀";
                mTotalAngle = (values - 550) * 120 / 150f + 48;
            }
            evaluationTime = "评估时间:" + getCurrentTime();
        } else if (values <= 950) {
            mMaxNum = values;
            mTotalAngle = (values - 700) * 40 / 250f + 170;
            sesameLevel = "信用极好";
            evaluationTime = "评估时间:" + getCurrentTime();
        } else {
            mTotalAngle = 240f;
        }

        startAnim();
    }

    /**
     * 属性动画
     */
    private void startAnim() {
        ValueAnimator mAngleAnim =ValueAnimator.ofFloat(mCurrentAngle,mTotalAngle);
        mAngleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAngleAnim.setDuration(3000);
        mAngleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               mCurrentAngle = (float) animation.getAnimatedValue();
                postInvalidate();

            }
        });

        mAngleAnim.start();

        // mMinNum = 350;
        ValueAnimator mNumAnim = ValueAnimator.ofInt(mMinNum, mMaxNum);
        mNumAnim.setDuration(3000);
        mNumAnim.setInterpolator(new LinearInterpolator());
        mNumAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mMinNum = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mNumAnim.start();
    }


    /**
     * 获取当前时间
     */
    public String getCurrentTime() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd");
        return format.format(new Date());
    }
}
