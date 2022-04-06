package cn.dengyongsheng.smartcar_control;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * create by libo
 * create on 2020/7/30
 * description 手机方向键手柄view
 */
public class GameRockerView extends View {
    private Paint outerCirclePaint;
    private Paint innerCirclePaint;
    /**
     * 内圆中心x坐标
     */
    private double innerCenterX;
    /**
     * 内圆中心y坐标
     */
    private double innerCenterY;
    /**
     * view中心点x坐标
     */
    private float viewCenterX;
    /**
     * view中心点y左边
     */
    private float viewCenterY;
    /**
     * view宽高大小，设定宽高相等
     */
    private int size;
    /**
     * 外圆半径
     */
    private int outerCircleRadius;
    /**
     * 内圆半径
     */
    private int innerCircleRadius;
    /**
     * 摇杆的方向，弧度，正前方为0度，左侧为负数，右侧为正数
     */
    public float direction = 0;
    /**
     * 摇杆的力度，最大值为1
     */
    public float strength = 0;

    public GameRockerView(Context context) {
        super(context);
        init();
    }

    public GameRockerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(getResources().getColor(R.color.green));
        outerCirclePaint.setAntiAlias(true);
        outerCirclePaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.INNER));

        innerCirclePaint = new Paint();
        innerCirclePaint.setAlpha(130);
        innerCirclePaint.setColor(getResources().getColor(R.color.red));
        innerCirclePaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        size = getMeasuredWidth();
        setMeasuredDimension(size, size);

        innerCenterX = (int) (size / 2);
        innerCenterY = (int) (size / 2);
        viewCenterX = (int) (size / 2);
        viewCenterY = (int) (size / 2);
        outerCircleRadius = size / 2;
        innerCircleRadius = size / 5;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(viewCenterX, viewCenterY, outerCircleRadius, outerCirclePaint);

        canvas.drawCircle((float) innerCenterX, (float) innerCenterY, innerCircleRadius, innerCirclePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                handleEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                restorePosition();
                break;
        }

        return true;
    }

    /**
     * 处理手势事件
     */
    private void handleEvent(MotionEvent event) {
        double distance = Math.sqrt(Math.pow(event.getX() - viewCenterX, 2) + Math.pow(event.getY() - viewCenterY, 2)); //触摸点与view中心距离
        if (distance < outerCircleRadius - innerCircleRadius) {
            //在自由域之内，触摸点实时作为内圆圆心
            innerCenterX = event.getX();
            innerCenterY = event.getY();
            // 计算力度
            this.strength = (float) distance / (outerCircleRadius - innerCircleRadius);
            invalidate();
        } else {
            //在自由域之外，内圆圆心在触摸点与外圆圆心的线段上
            updateInnerCircelCenter(event);
            // 力度为最大值
            this.strength = 1;
        }

        // 计算方向
        this.direction = (float) Math.atan2((event.getX() - viewCenterX), -(event.getY() - viewCenterY));
    }

    /**
     * 在自由域外更新内圆中心坐标
     */
    private void updateInnerCircelCenter(MotionEvent event) {
        //当前触摸点到圆心的距离
        double distance = Math.sqrt(Math.pow(event.getX() - viewCenterX, 2) + Math.pow(event.getY() - viewCenterY, 2));
        int innerDistance = outerCircleRadius - innerCircleRadius;  //内圆圆心到中心点距离
        //相似三角形的性质，两个相似三角形各边比例相等得到等式
        innerCenterX = (event.getX() - viewCenterX) * innerDistance / distance + viewCenterX;
        innerCenterY = (event.getY() - viewCenterY) * innerDistance / distance + viewCenterY;

        invalidate();
    }

    /**
     * 恢复内圆到view中心位置
     */
    private void restorePosition() {
        innerCenterX = viewCenterX;
        innerCenterY = viewCenterY;
        // 力度归零
        this.strength = 0;
        // 方向归零
        this.direction = 0;
        invalidate();
    }

}
