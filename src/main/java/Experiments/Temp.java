package Experiments;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;

import java.io.InputStream;
import java.net.URL;

import static com.sun.org.apache.xerces.internal.util.FeatureState.is;

/**
 * Created by user on 9/22/18.
 */
public class Temp {

    static {
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
    }


    public static void main(String[] args) throws Exception{
        InputStream in = null;


        try{
            in = new URL(args[0]).openStream();
            IOUtils.copyBytes(in, System.out, 4096, false);
        } finally {
            IOUtils.closeStream(in);
        }
    }
}
