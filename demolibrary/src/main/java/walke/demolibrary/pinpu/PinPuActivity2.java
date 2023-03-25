package walke.demolibrary.pinpu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import walke.demolibrary.R;

/**
 * @author Walker - 2023/3/22 11:22 AM
 * @email 1126648815@qq.ocm
 * @desc : 音频频谱2
 */
public class PinPuActivity2 extends AppCompatActivity {

    private MediaPlayer mPlayer;    // ���岥��������MediaPlayer
    private Visualizer mVisualizer0; // ����ϵͳ��Ƶ��,0-����Ҷ����Ƶ�ף�1-��������Ƶ��
    //private Visualizer mVisualizer1;
    private MyVisualizerView mVisualizerView;
    final int CRate = Visualizer.getMaxCaptureRate() / 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pinpu2);

        //������Ƶ�� - STREAM_MUSIC�����ֻطż�ý������
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // ����MediaPlayer����,�������Ƶ
        // ��Ƶ·��Ϊ  res/raw/beautiful.mp3
        mPlayer = MediaPlayer.create(this, R.raw.ski);

        // ����MyVisualizerView�����������ʾ����ͼ
        mVisualizerView = new MyVisualizerView(this);
        mVisualizerView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,       //ԭ����MATCH_PARENT,,FILL_PARENT
                ViewGroup.LayoutParams.FILL_PARENT));

        // ��MyVisualizerView�����ӵ�layout������
        LinearLayout pinpulayout = (LinearLayout) findViewById(R.id.pinpu2_layout1);//Ƶ����ʾ����
        pinpulayout.addView(mVisualizerView);

        //���������α�ʾ�����Ҳ�����
        //mVisualizerView.setVisualizer(mVisualizer0); //���п���

        setupVisualizer1();  // ��ʼ��ʾ����      //��ѡ���Ӳ���Ƶ�ס��ÿ�������
        mPlayer.start();    // ��ʼ��������
    }

    /******** ��ʼ��Ƶ�� *************/
    private void setupVisualizer0()  //���òɼ�����Ҷ���ݵ�Ƶ��
    {
        // ��MediaPlayer��AudioSessionId����Visualizer
        // �൱������Visualizer������ʾ��MediaPlayer����Ƶ����
        mVisualizer0 = new Visualizer(mPlayer.getAudioSessionId());
        //mVisualizer1 = new Visualizer(mPlayer.getAudioSessionId());
        //������Ҫת�����������ݳ��ȣ�רҵ��˵����ǲ������ò���ֵһ��Ϊ2��ָ��������64,128,256,512,1024��
        //mVisualizer0.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        mVisualizer0.setCaptureSize(1024);

        // ΪmVisualizer0���ü�����
        /* Visualizer.setDataCaptureListener(OnDataCaptureListener listener, int rate, boolean waveform, boolean fft
         *
         *      listener������������������ڲ���ʵ�ָýӿڣ��ýӿ���Ҫʵ����������
                rate�� ��ʾ���������ڣ�������ò���һ�Σ���ϵǰ�ľ��Ǹ���ò���128������
                iswave���ǲ����ź�
                isfft����FFT�źţ���ʾ�ǻ�ȡ�����źŻ���Ƶ���ź�
         */

        //ȡ����Ҷ���ݵĻ�������������ʮ�ֿ϶��ϸ��Ҫ�������˳����ţ���������������������ţ�����Ҷ������ȡ�����ģ�����û����ʾ����ʹ��һ����������û���κβ���
        //����Ŀǰ�뵽�İ취ֻ�з�����Visualizer��ȡ�����ˡ����ʧ���ˣ����������ǻ��������Ǹ�����Ϊ׼�ģ��������ݴ洢��ָ��λ�ã��ᱻ���ǡ�
        //�뵽�İ취���ڴ����л�ʱ������setupVisualizer
        mVisualizer0.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    //����ص�Ӧ�òɼ����ǿ��ٸ���Ҷ�任�йص�����
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] bytes, int samplingRate) {
                        //mVisualizerView.updateVisualizer0(bytes); //�������������ȡ���ݣ�������ʾ�ᡰ������
                    }

                    //����ص�Ӧ�òɼ����ǲ�������
                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] fft, int samplingRate) {
                        // ��waveform�������ݸ���mVisualizerView���
                        mVisualizerView.updateVisualizer0(fft); //ԭ���ǲɼ��������ݵ�,���ò�������updateVisualizer���ô������ݣ����¼���
                    }
                }, CRate, false, true);

	         /*
             //ȡ�������ݵĻ�������������ʮ�ֿ϶��ϸ��Ҫ�������˳����ţ���������������������ţ�����������ȡ�����ģ�����û����ʾ����ʹ��һ����������û���κβ���
	         //����Ŀǰ�뵽�İ취ֻ�з�����Visualizer��ȡ������
	         mVisualizer0.setDataCaptureListener(
	 	            new Visualizer.OnDataCaptureListener()
	 	            {
	 	                //����ص�Ӧ�òɼ����ǿ��ٸ���Ҷ�任�йص�����
	 	                @Override
	 	                public void onFftDataCapture(Visualizer visualizer,
	 	                    byte[] fft, int samplingRate)
	 	                {
	 	                	 //mVisualizerView.updateVisualizer0(fft);
	 	                }
	 	                 //����ص�Ӧ�òɼ����ǲ�������
	 	                @Override
	 	                public void onWaveFormDataCapture(Visualizer visualizer,
	 	                    byte[] waveform, int samplingRate)
	 	                {
	 	                    // ��waveform�������ݸ���mVisualizerView���
	 	                    mVisualizerView.updateVisualizer0(waveform); //ԭ���ǲɼ��������ݵ�,���ò�������updateVisualizer���ô������ݣ����¼���
	 	                }
	 	            }, Visualizer.getMaxCaptureRate() / 2, true, false);
	 	           */

        mVisualizer0.setEnabled(true);
        //mVisualizer1.setEnabled(true);
    }

    private void setupVisualizer1()  //���òɼ��������ݵ�Ƶ��
    {
        // ����MyVisualizerView�����������ʾ����ͼ
        //mVisualizerView = new MyVisualizerView(this);
        //mVisualizerView.setLayoutParams(new ViewGroup.LayoutParams(
        //    ViewGroup.LayoutParams.FILL_PARENT,       //ԭ����MATCH_PARENT
        //    (int) (120f * getResources().getDisplayMetrics().density)));

        // ��MyVisualizerView�����ӵ�layout������
        //LinearLayout pinpulayout=(LinearLayout)findViewById(R.id.layout1);//Ƶ����ʾ����
        //pinpulayout.addView(mVisualizerView);

        mVisualizer0 = new Visualizer(mPlayer.getAudioSessionId());
        mVisualizer0.setCaptureSize(1024);

        mVisualizer0.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    //����ص�Ӧ�òɼ����ǿ��ٸ���Ҷ�任�йص�����
                    @Override
                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] fft, int samplingRate) {
                        //mVisualizerView.updateVisualizer0(fft);
                    }

                    //����ص�Ӧ�òɼ����ǲ�������
                    @Override
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] waveform, int samplingRate) {
                        // ��waveform�������ݸ���mVisualizerView���
                        mVisualizerView.updateVisualizer0(waveform); //ԭ���ǲɼ��������ݵ�,���ò�������updateVisualizer���ô������ݣ����¼���
                    }
                }, CRate, true, false);

        mVisualizer0.setEnabled(true);
    }

    /*** ����Visualizer���������ݶ�̬���Ʋ���Ч�����ֱ�Ϊ��
     * ��״���Ρ���״���Ρ����߲��� */
    class MyVisualizerView extends View {

        private static final int DN_W = 470; //view����뵥����Ƶ��ռ�� - ����480 ��΢��
        private static final int DN_H = 180; //view�߶��뵥����Ƶ��ռ��
        private static final int DN_SL = 15; //������Ƶ����
        private static final int DN_SW = 5;  //������Ƶ��߶�

        private int hgap = 0;
        private int vgap = 0;
        private int vgap2 = 0;
        private int levelStep = 0;
        private float strokeWidth = 0;
        private float strokeLength = 0;

        protected final static int MAX_LEVEL = 16; //����������Ƶ�� - ������  ԭ15
        protected final static int CYLINDER_NUM = 25;//�������� - ������     ԭ25
        //protected Visualizer mVisualizer02 = null;//Ƶ����
        //protected Paint mPaint = null;//����
        //  ��MAX_LEVEL2ֻȡż�������������������������Ӱ����Ҫ<20�����򲻶Թ�ʽ��ʵ������ٶ�ʵ����Ч�Ķ���9����
        protected final static int MAX_LEVEL2 = 18;//23; //С���鲨��ͼ����Ƶ�� - ������

        protected byte[] mData = new byte[CYLINDER_NUM];//������ ����
        boolean mDataEn = true;

        // bytes���鱣���˲��γ������ֵ
        private byte[] bytes;
        private float[] points;
        private Paint MyPaint = new Paint();
        private Rect rect = new Rect();
        private byte type = 0;

        //private boolean mFirst = true;
        //private int mSpectrumNum = 48;

        public MyVisualizerView(Context context) {
            super(context);
            bytes = null;
            // ���û��ʵ�����
            MyPaint.setStrokeWidth(1f);///ԭ��Ϊ1f//////////////////////////////////
            MyPaint.setAntiAlias(true);//�����
            MyPaint.setColor(Color.BLUE);//������ɫ
            MyPaint.setStyle(Paint.Style.FILL);
        }

        //ִ�� Layout ����
        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);

            float w, h, xr, yr;

            w = right - left;
            h = bottom - top;
            xr = w / (float) DN_W;
            yr = h / (float) DN_H;

            strokeWidth = DN_SW * yr;
            strokeLength = DN_SL * xr;
            hgap = (int) ((w - strokeLength * CYLINDER_NUM) / (CYLINDER_NUM + 1));  //���Ӽ���
            vgap = (int) (h / (MAX_LEVEL + 8));     //��������ÿ��Ƶ�׿���
            vgap2 = (int) (h / (MAX_LEVEL2 + 4));   //��������ÿ��Ƶ�׿���

            MyPaint.setStrokeWidth(strokeWidth); //����Ƶ�׿���
        }

        //������ͬ��Ƶ�ײ�����ʾ��Ƶ�׹��ɲ�һ��Ӧ�þ�����Ϊ�������Ҷ�任�ɼ����ݷ������������������ǲ�һ���ģ����������о������ԣ����Ͼ������֣����Ҳ������õ�~~
        //���ò������ݵĻ�����Ͳ��ô���ʲô���ø���Ҷ�Ļ���Ҫ������ת������
        public void updateVisualizer0(byte[] fft) {
            if (type > 1)  //��������Ƶ��
            {
                bytes = fft;  //ԭ���ò������ݣ����Ҳ���Ҫ���ݴ���ֱ��ȡ�������
                // ֪ͨ������ػ��Լ���
                invalidate();
            } else if (type == 0 || type == 1)  //����ʽ����ҶƵ�׺Ͳ���Ƶ��
            {
                if (type == 0) levelStep = 128 / MAX_LEVEL2;
                else levelStep = 128 / MAX_LEVEL;

                byte[] model = new byte[fft.length / 2 + 1];
                if (mDataEn) {
                    model[0] = (byte) Math.abs(fft[1]);
                    int j = 1;
                    for (int i = 2; i < fft.length; ) {
                        model[j] = (byte) Math.hypot(fft[i], fft[i + 1]);
                        i += 2;
                        j++;
                    }
                } else {
                    for (int i = 0; i < CYLINDER_NUM; i++) {
                        model[i] = 0;
                    }
                }
                for (int i = 0; i < CYLINDER_NUM; i++) {
                    final byte a = (byte) (Math.abs(model[CYLINDER_NUM - i]) / levelStep);

                    final byte b = mData[i];
                    if (a > b) {
                        mData[i] = a;
                    } else {
                        if (b > 0) {
                            mData[i]--;
                        }
                    }
                }
                invalidate();
            }
            invalidate(); //ˢ�½���
        }


      /* public void updateVisualizer1(byte[] fft)
         {
             if(type>0)
         	{
 	            bytes = fft;  //ԭ���ò������ݣ����Ҳ���Ҫ���ݴ���ֱ��ȡ�������
 	            // ֪ͨ������ػ��Լ���
 	            invalidate();
         	}
         }  */

        //����Ƶ�׿�͵�Ӱ
        protected void drawCylinder0(Canvas canvas, float x, byte value) {
            if (value <= 0) value = 1;//������һ��Ƶ�׿�
            for (int i = 0; i < value; i++) {                  //ÿ������������value��������
                float y = (getHeight() - i * vgap - vgap);//����y������
                //float y = (getHeight() - i * vgap - vgap) - 40;//ԭ����������������ʾ������y������

                //����Ƶ�׿�
                MyPaint.setStrokeWidth(strokeWidth); //����Ƶ�׿���
                MyPaint.setColor(Color.BLUE);            //������ɫ
                canvas.drawLine(x, y, (x + strokeLength), y, MyPaint);//����Ƶ�׿�

                //������������Ӱ
                //if (i <= 6 && value > 0) {
                //    MyPaint.setColor(Color.BLUE);        //������ɫ
                //    MyPaint.setAlpha(100 - (100 / 6 * i));//��Ӱ��ɫ
                //    canvas.drawLine(x, -y + 210, (x + strokeLength), -y + 210, MyPaint);//����Ƶ�׿�
                //}
            }
        }

        //����Ƶ�׿�͵�Ӱ 2//ע���޸ĵĵط������������������ͻ��Ƴ�ʼ�ĸ���(y��)
        protected void drawCylinder1(Canvas canvas, float x, byte value) {
            if (value <= MAX_LEVEL2 / 2 + 1)
                value = MAX_LEVEL2 / 2 + 1;   //������һ��Ƶ�׿�(<=8����������Ӱ)��ʵ������ʱΪ8~9�����ӣ���8���ȶ�����9��Ϊ��Ӱ���ȶ���������value=9������Ӱ��
            for (int i = 0; i < value - MAX_LEVEL2 / 2; i++) {        //ÿ�����������Ƹ�������
                float y = (getHeight() - i * vgap2 - vgap2);   //����y������ //
                //float y = (getHeight() - i * vgap - vgap) - 40;//ԭ����������������ʾ������y������

                //����Ƶ�׿�
                MyPaint.setStrokeWidth(strokeWidth); //����Ƶ�׿���
                MyPaint.setColor(Color.BLUE);            //������ɫ
                canvas.drawLine(x, y, (x + strokeLength), y, MyPaint);//����Ƶ�׿�

                //������������Ӱ
                //if (i <= 6 && value > 0) {
                //    MyPaint.setColor(Color.BLUE);        //������ɫ
                //    MyPaint.setAlpha(100 - (100 / 6 * i));//��Ӱ��ɫ
                //    canvas.drawLine(x, -y + 210, (x + strokeLength), -y + 210, MyPaint);//����Ƶ�׿�
                //}
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //����Ҷ��ʾʱ��������return;��������Ҷ��Ƶ�׾���ʾ�����ˣ���
            if (type > 1 && bytes == null) {  //��������Ǻ��������Ӱ�캦�ҵģ�ֱ��ȥ�����ˣ��ߺߺߣ�����ȥ�������п��ܳ������������~
                return;
            }
            // ���ư�ɫ����
            //canvas.drawColor(Color.WHITE);
            // ʹ��rect�����¼������Ŀ�Ⱥ͸߶�
            rect.set(0, 0, getWidth(), getHeight());
            switch (type) {
                //-------���Ƹ���ҶƵ��ͼ-------
                case 1:
                    for (int i = 0; i < CYLINDER_NUM; i++) { //����25��������
                        drawCylinder0(canvas, strokeWidth / 2 + hgap + i * (hgap + strokeLength), mData[i]);
                    }
                    break;
                // -------���ƿ�״����ͼ-------(���������ǲ�������ͼ�����Ǹ���Ҷ����)
                case 0:
                    for (int i = 0; i < CYLINDER_NUM; i++) {
                        drawCylinder1(canvas, strokeWidth / 2 + hgap + i * (hgap + strokeLength), mData[i]);
                    }
                    break;
                // -------������״����ͼ-------
                case 3:
                    for (int i = 0; i < bytes.length - 1; i++) {
                        float left1 = getWidth() * i / (bytes.length - 1);
                        // ���ݲ���ֵ����þ��εĸ߶�
                        float top1 = (rect.height() - (byte) (bytes[i + 1] + 128)
                                * rect.height() / 128) - 5;     //����Ҳ��ʾ�ײ����Լ��Ĺ��ģ�ԭ����û��-5
                        float right1 = left1 + 1;
                        float bottom1 = rect.height();
                        MyPaint.setStrokeWidth(1f);
                        MyPaint.setColor(Color.RED);
                        canvas.drawRect(left1, top1, right1, bottom1, MyPaint);
                    }
                    break;
                // -------������״�Ĳ���ͼ��ÿ��18�����������һ�����Σ�-------
                case 4:
                    for (int i = 0; i < bytes.length - 1; i += 18) {
                        float left1 = rect.width() * i / (bytes.length - 1);
                        // ���ݲ���ֵ����þ��εĸ߶�
                        float top1 = (rect.height() - (byte) (bytes[i + 1] + 128)
                                * rect.height() / 128) - 5;          //����Ҳ��ʾ�ײ����Լ��Ĺ��ģ�ԭ����û��-5
                        float right1 = left1 + (strokeWidth * 1 / 2);  //ԭ����left1+6����������Ӧ��С�ģ�����һ����������Ӧ
                        float bottom1 = rect.height();
                        MyPaint.setStrokeWidth(1f);
                        MyPaint.setColor(Color.GREEN);
                        canvas.drawRect(left1, top1, right1, bottom1, MyPaint);
                    }
                    break;
                // -------�������߲���ͼ-------
                case 2:
                    // ���point���黹δ��ʼ��
                    if (points == null || points.length < bytes.length * 4) {
                        points = new float[bytes.length * 4];
                    }
                    for (int i = 0; i < bytes.length - 1; i++) {
                        // �����i�����x����
                        points[i * 4] = rect.width() * i / (bytes.length - 1);
                        // ����bytes[i]��ֵ�����ε��ֵ�������i�����y����
                        points[i * 4 + 1] = (rect.height() / 2) //�м�λ�ã���ʼ��
                                + ((byte) (bytes[i] + 128)) * 128
                                / (rect.height());    //������(rect.height() / 2)��������Сlayout�Ļ����¿��̫����
                        // �����i+1�����x����                                     //ʵ��rect.height()*3/2���ԣ�*1Ҳ��
                        points[i * 4 + 2] = rect.width() * (i + 1)
                                / (bytes.length - 1);
                        // ����bytes[i+1]��ֵ�����ε��ֵ�������i+1�����y����
                        points[i * 4 + 3] = (rect.height() / 2)
                                + ((byte) (bytes[i + 1] + 128)) * 128
                                / (rect.height());    //������(rect.height() / 2) ��������Сlayout�Ļ����¿��̫����
                    }                                 //ʵ��rect.height()*3/2���ԣ�*1Ҳ��
                    // ���Ʋ�������
                    MyPaint.setStrokeWidth(1.5f);
                    MyPaint.setColor(Color.YELLOW);
                    canvas.drawLines(points, MyPaint);
                    break;
                default:
                    break;
            }
        }

 /*
        public void setVisualizer(Visualizer visualizer) {
            if (visualizer != null)
            {
                if (!visualizer.getEnabled())
                    visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[0]);
                levelStep = 128 / MAX_LEVEL;
                visualizer.setDataCaptureListener((OnDataCaptureListener) this, Visualizer.getMaxCaptureRate() / 2, false, true);
             }
           else {
                if (mVisualizer02 != null)
                {
                    mVisualizer02.setEnabled(false);
                    mVisualizer02.release();
                }
            }
            mVisualizer02 = visualizer;
        }
   */

        // ���û����������ʱ���л���������
        @Override
        public boolean onTouchEvent(MotionEvent me) {
            if (me.getAction() != MotionEvent.ACTION_DOWN)
                return false;
            type++;
            if (type >= 5) {
                type = 0;
            }

            mVisualizer0.setEnabled(false);  //��������У�����Ȼ�����л��ٶȻ᲻�ϱ�죡������ԭ������ܾ��ˣ�Ѿ�ģ�
            mVisualizer0.release();  //����Ҫ��release���������ã��������
            mVisualizer0 = null;
            //����type��ֵ����setupVisualizer//Ѿ�����������ڳɹ��ˣ���ȫ������ô���ǣ�����
            if (type != 1) {
                setupVisualizer1();   //���ò���Ƶ��
            } else if (type == 1) {
                setupVisualizer0(); //���ø���ҶƵ��
            }

            return true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing() && mPlayer != null) {
            // �ͷ����ж���
            mVisualizer0.release();
            // mVisualizer1.release();
            mPlayer.release();
            mPlayer = null;
        }
    }

}