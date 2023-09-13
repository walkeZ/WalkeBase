package com.example.cdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

/**
 * @author Walker - 2023/8/12 17:30 PM
 * @email 1126648815@qq.ocm
 * @desc : 新建一个使用C语言的Demo
 * 参考：https://www.51c51.com/danpianji/xinxi/84/786987.html
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.main_tvTips);

        byte[] originalBytes = {2, 1};
        byte[] bs = HelloLzo.testCompress(originalBytes);
//        byte[] bs = originalBytes;
        tv.setText(Arrays.toString(bs));

    }

    public void testLZO(View view) {
        // 数据样例在：doc/数据样例_data_len1075.txt
        String originalHex = "0000000000000000000c0a0b0c080000000000080e0f0009090000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000a08000000000000000000000000000000" +
                "0000000000000000000000000000090c0b090a0d090b08080809000008000000000000000000000000000000000a0d0f0e0d0f160f150e0e0d0d00080a080000000000000000000000000000090e1113100f131b121b1212110f090a0b0a0000000000000000000000000000000c0c0e0d0d12160f170f0d0d0d000809080000" +
                "000000000000000000000000000a0b0c0d0c0f110c110c090a0c00000a000000000000000000000000000000080c0e10110f10110b130d0d0e0e00080b000000000000000000000000000000000b0f11161411120c150d10120f080a0b000000000000000000000000000000000b1212161310110d130c0f100e090b0e0a0000" +
                "00000000000000000000000000090e1010110e0e0a0e0b13130e0a090b0900000000000000000000000000000000090e121413120a0f0b1514110e110b000000000000000000000000000000080b0f0f161616150b110c12131611140d0900000000000000000000000000000a1312111612100f090d0a10171c0f100f0b0900" +
                "000000000000000000000008000e11111b1311110d0e0c0e151d1412100c0000000000000000000000000a09000c1d1013120f100d140c0f141614100d09000008000000000000000000080000001111140f0f1410170e111615120f100800000b0b080000000000080d0d0000000b1216111317101910141816110d0d000000" +
                "000b0800000000000c0f0a000000090f14131616111910161b17120e0a0000000000000000000000101000000000080f14101516101a12161b160d0d08000000000d0f0f000000102a1200000000000c121016160f160f1013150e0a00000000000b12150000001722120000000000091016161611150a0d0f150f0900000000" +
                "000812120000001216140000000000000e0e11140d120a0c10120a0000000000000014160800001412120000000000000e0b0f100b0f0f1013120a0a0000000000001114080000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000" +
                "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000a11100f0f0b0d0b140f130b0c0900000000000000000000000000000000000913131919190f120d171a160d101000000000000000000000000000000000000d161b1c211e12110d1419130b1010000000" +
                "00000000000000000900000000000f12151c1f1f13100e1317120c0a090000000000000000000000000000000000080e18131f23120f101417140d0f1100000000000000000000000000000000000c10171e21230f0f121418140e0e1000000000000000000000000000000000000c131e1d2123160f121319140f110f000000" +
                "00000000000000000900000000000e131f1a2122140d131417150e140d0800000000000000000000001200000008150f15161a150a090f1212170e0f120b0000000000000000000000080000000010121a16120f08090914141614151900000000000000000000000000000000001a1a1519100c0008090b141511131a000000" +
                "00000000000000000000000000000b0d0a09000000000000000c0d0f0d00000000000000000000000000000000000c17120e0800000000000a101318190000000000000000000000000000000000091b1c08000000000000000a0f161d000000000000000000000000000000000008131a000000000000000000091419080000" +
                "000000000000000000000000000000150a0000000000000000000015120000000000000000000000000000000000080c00000000000000000000000b0f000000000000000000000000000000000d14090000000000000000000000000b000000000000000000000000000000001011000000000000000000000000000a090000" +
                "0000000000000000000000082833170d000000000000000000000000111d0f0000000000000000000000000a283021120000000000000000000000000e1f1c0000000000000000000000091c2c2e200900000000000000000000000011231b0c000000000000000000000e212e3221090000000000000000000000000a0d210c" +
                "00000000000000000000002d383d23000000000000000000000000000e1a110a00000000000000000000001b363911000000000000000000000000000a191b1e0000000000000000000000163a3500000000000000000000000000000025351600000000000000000000001130180000000000000000000000000000001c4611" +
                "000000000000000000000012270d0000000000000000000000000000000e3e0c0000000000000000000000120f0000000000000000000000000000000000120900000000000000000000000d0900000000000000000000000000000000080e0d0000000000000000000000090900000000000000000000000000000000080b00" +
                "00000000000000000000000a3612000000000000000000000000000000165513000000000000000000000d2d2e0900000000000000000000000000000008200000000000000000000000001710000000000000000000000000000000000c0f000000000000000000000000235d0c0000000000000000000000000000000a12000000000000";

        // 1075
        String compressHex = "0200000000007000020c0a0b0c0860010400080e0f0009098801202b11000ab40b3648010b090c0b090a0d090b0808080900002fac000c0a0d0f0e0d0f160f150e0e0d0d00082e30010f090e1113100f131b121b1212110f090a0b0a2d9c01080c0c0e0d"
                + "0d12160f170f0d7d07092e80010c0a0b0c0d0c0f110c110c090a0c00002ef8000e080c0e10110f10110b130d0d0e0e00080b2d78010d000b0f11161411120c150d10120f080a307c000c1212161310110d130c0f100e090b0e2e80010d090e1010110e0e"
                + "0a0e0b13130e0a090b2ffc050b090e121413120a0f0b1514110e112e7c010e080b0f0f161616150b110c12131611140d2dfc000e0a1312111612100f090d0a10171c0f100f2c8001000108000e11111b1311110d0e0c0e151d1412100c2a80030f0a0900"
                + "0c1d1013120f100d140c0f141614107d0b08278800640100021111140f0f1410170e111615120f100800000b0b7c0200030000080d0d0000000b121611131710191014181611850200de030c0f70210a090f14131616111910161b17122b7a041010600d"
                + "0b080f14101516101a12161b160d0d840b060d0f0f000000102a127c0309000c121016160f160f101315d807050b12150000001722bc0309091016161611150a0d0f150fb623081267031216149c0708000e0e11140d120a0c10127c116c020314160800"
                + "001494040b0000000e0b0f100b0f0f1013120ac20411149c10680520410c000a0a11100f0f0b0d0b140f130b0ca0182ad0010a0913131919190f120d171a160da8262a78000b000d161b1c211e12110d1419130b2b7c00ac090b0f12151c1f1f13100e13"
                + "17120c0aac022a00010c080e18131f23120f101417140d0f112a68008c010b0c10171e21230f0f121418140e0e88369c02f0000c0c131e1d2123160f121319140f110ffb02000000ac0d0c0e131f1a2122140d131417150e140d2a600574340d08150f15"
                + "161a150a090f1212170e0f122a000c8c050c10121a16120f080909141416141519297c01a8010c1a1a1519100c0008090b141511131ab0022916000b0d27d3030c0d0f6556002b0000010c17120e800d02000a101318307f01091b1cb8030300000a0f16"
                + "1d2bec0071010829cf01091419ec056803ad0015d04a750115681e6001280d00082a450e0b2afc048f040d14099c00d0002a78048902102cc40627ac0390030200082833172bca03111d2b8401010a2830212b4b020e1f1ca407940002091c2c2e202bff"
                + "0111231b29bc02020e212e32212b7f000a0d21297c0002002d383d238409f3000e1a1129380401001b36392dff02191b1eec0503000000163a352934006b012535167800cf00113018c401fa001c462a420112272bf403717d3e2a7d02122b34048d0812"
                + "2a3d030d2a30009a03080e2a450109307c002b86060a362b7405661a55139c0893000d2d2e2e020108208403d100172fbd0d0c2bc602235d2a340309000000000a12000000000000110000";

        byte[] originalBytes = DataUtil.hexStringToBytes(originalHex);
        byte[] compressBytes = DataUtil.hexStringToBytes(compressHex);
        Log.i("testLZO ", " origin（原数据） " + originalBytes.length + ", " + Arrays.toString(originalBytes));
        Log.i("testLZO ", " miniLzo（嵌入式的）compress " + compressBytes.length + ", " + Arrays.toString(compressBytes));
        try {
            byte[] compress1 = compress(originalBytes);
            byte[] uncompress1 = uncompress(compress1);
            byte[] uncompress = uncompress(compressBytes);
            // 验证
            Log.i("testLZO ", " 原数据对比 " + DataUtil.bytesToHexString(originalBytes).equals(DataUtil.bytesToHexString(uncompress)) + ", 压缩数据长度(byte) " + compress1.length);
            Log.i("testLZO ", " 压缩数据对比 " + DataUtil.bytesToHexString(compressBytes).equals(DataUtil.bytesToHexString(compress1)));
            // 分行对比
            String compress1Hex = DataUtil.bytesToHexString(compress1);
            StringBuffer buffer = new StringBuffer();
            buffer.append("\n 01行  ");
            int hang = 1;
            for (int j = 0; j < compress1Hex.length(); j++) {
                buffer.append(compress1Hex.charAt(j));
                if ((j + 1) % 200 == 0) {
                    hang++;
                    if (hang < 10) {
                        buffer.append("\n 0" + hang + "行  ");
                    } else {
                        buffer.append("\n " + hang + "行  ");
                    }

                } else if ((j + 1) % 2 == 0) {
                    buffer.append("  ");
                }
            }
            Log.i("testLZO ", " testLZO 压缩后Hex " + compress1Hex);
            Log.i("testLZO ", " 压缩后Hex格式化 " + buffer);
        } catch (Exception e) {
            Log.i("testLZO ", " testLZO IOException " + e.getMessage());
        }
    }

    /**
     * 压缩
     *
     * @param originalBytes 原数据。
     * @return 压缩后的数据
     */
    private byte[] compress(byte[] originalBytes) {
        // TODO: 2023/8/22 miniLzo 压缩
        // miniLzo C 库官网（源码） http://www.oberhumer.com/opensource/lzo/#minilzo
        byte[] compress = MiniLzo.compress(originalBytes);
        Log.i("testLZO ","compress: " + Arrays.toString(compress));
        return compress;
    }

    /**
     * 解压
     *
     * @param compress 压缩后的数据
     * @return 原数据
     */
    private byte[] uncompress(byte[] compress) {
        // TODO: 2023/8/22 miniLzo 解压
        byte[] uncompress = MiniLzo.uncompress(compress);
        Log.i("testLZO ","uncompress: " + Arrays.toString(uncompress));
        return uncompress;
    }

    public void jump(View view) {
        startActivity(new Intent(this, HelloJniActivity.class));
    }
}