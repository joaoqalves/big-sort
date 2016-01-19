package net.joaoqalves.utils;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {

    // Just an utility
    public static void close(Closeable c) {
        if(c != null) {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
