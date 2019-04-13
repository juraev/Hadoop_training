package HADOOP_IO;

import org.apache.hadoop.io.GenericWritable;
import org.apache.hadoop.io.Writable;

/**
 * Created by user on 9/27/18.
 */
public class SampleWriteble extends GenericWritable {


    protected Class<? extends Writable>[] getTypes() {
        return new Class[0];
    }
}
