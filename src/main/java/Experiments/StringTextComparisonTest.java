package Experiments;

import org.apache.hadoop.io.Text;

import java.io.UnsupportedEncodingException;

/**
 * Created by user on 9/27/18.
 */

public class StringTextComparisonTest {
    public void string() throws UnsupportedEncodingException {
        String s = "\u0041\u00DF\u6771\uD801\uDC00";
        assert s.length() == 5;
        assert s.getBytes("UTF-8").length == 10;
        assert s.indexOf("\u0041") ==  0;
        assert s.indexOf("\u00DF") ==  1;
        assert s.indexOf("\u6771") ==  2;
        assert s.indexOf("\uD801\uDC00") ==  3;
        assert s.charAt(0) ==  '\u0041';
        assert s.charAt(1) ==  '\u00DF';
        assert s.charAt(2) ==  '\u6771';
        assert s.charAt(3) ==  '\uD801';
        assert s.charAt(4) ==  '\uDC00';
        assert s.codePointAt(0) ==  0x0041;
        assert s.codePointAt(1) ==  0x00DF;
        assert s.codePointAt(2) ==  0x6771;
        assert s.codePointAt(3) ==  0x10400;
    }

    public void text() {
        Text t = new Text("\u0041\u00DF\u6771\uD801\uDC00");
        assert t.getLength() ==  10;
        assert t.find("\u0041") ==  0;
        assert t.find("\u00DF") ==  1;
        assert t.find("\u6771") ==  3;
        assert t.find("\uD801\uDC00") ==  6;
        assert t.charAt(0) ==  0x0041;
        assert t.charAt(1) ==  0x00DF;
        assert t.charAt(3) ==  0x6771;
        assert t.charAt(6) ==  0x10400;

    }


    public static void main(String[] args) {
        StringTextComparisonTest test = new StringTextComparisonTest();
        try {
            test.string();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        test.text();
    }
}