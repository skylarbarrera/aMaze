package edu.wm.cs.cs301.skylarbarrera.gui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import edu.wm.cs.cs301.skylarbarrera.R;

/**
 * TODO: document your custom view class.
 */
public class MazePanel extends View {
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private Canvas bitCanvas;
    private Paint paint;
    private int paddingLeft ;
    private int paddingTop ;
    private int paddingRight;
    private int paddingBottom;
    private Bitmap bitmap;

    public MazePanel(Context context) {
        super(context);
        init(null, 0);

    }

    public MazePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MazePanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        bitmap = Bitmap.createBitmap(1200,1200,Bitmap.Config.ARGB_8888);




        // Update TextPaint and text measurements from attributes
        // invalidateTextPaintAndMeasurements();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();



        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        //fillRect(paddingLeft,paddingTop, 600, 200, canvas);

        Log.v("Project6","MazePanel: onDraw called.");

        canvas.drawBitmap(bitmap, 0,0,paint);
        tester(canvas);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);
    }

    public void tester(Canvas canvas){
        setColor(255,0,0);
        fillOval(paddingLeft,paddingTop,100,100,canvas);
        setColor(0,255,0);
        fillOval(paddingLeft+110,paddingTop,100,100,canvas);
        setColor(255,255,0);
        fillRect(paddingLeft,paddingTop+250,200,100,canvas);
        setColor(0,0,255);
        int[] xP = {0,0,100,200,200};
        int[] yP = {500,550,525,550,500};
        fillPolygon(xP,yP,5,canvas);
        setColor(50,100,80);
        paint.setStrokeWidth(10);
        drawLine(0,0,500,500,canvas);

    }

    /**
     * Wrapper Method to update color of graphics
     * sets the Color to the given value
     * @param rgb color representation
     */
    public void setColor(int[] rgb) {
        //Color col = new Color(rgb[0], rgb[1], rgb[2]);
        paint.setARGB(255,rgb[0],rgb[1],rgb[2]);
    }
    /**
     * Wrapper Method to update color of graphics
     * sets the Color to the given value
     * @param rgb
     */
    public void setColor(int r, int g, int b) {
        //Color col = new Color(rgb[0], rgb[1], rgb[2]);
        paint.setARGB(255,r,g,b);
    }
    /**
     * Wrapper Method to draw a filled in Rect
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void fillRect(int x, int y, int width, int height, Canvas canvas) {
        Rect rect =  new Rect(x,y,width, height);


        canvas.drawRect(rect,paint);;
    }


    /**
     * Wrapper Method to draw a Polygon
     * @param xPoints
     * @param yPoints
     * @param nPoints
     */
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints, Canvas canvas) {


        Path polyPath = new Path();
        polyPath.reset();
        polyPath.moveTo(xPoints[0],yPoints[0]);
        for (int i = 1; i< nPoints;i++){
            polyPath.lineTo(xPoints[i],yPoints[i]);

        }
        polyPath.lineTo(xPoints[0],yPoints[0]);

        canvas.drawPath(polyPath,paint);
    }

    /**
     * Wrapper Method to draw a Line
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(int x1, int y1, int x2, int y2, Canvas canvas) {


        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    /**
     * Wrapper Method to draw and fill an Oval
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void fillOval(int x, int y, int width, int height, Canvas canvas) {
        RectF oval;


        oval = new RectF(x, y,  x+width, y+height);

        canvas.drawOval(oval, paint);
    }

    public void update(){
        this.invalidate();
    }


}
