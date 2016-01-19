package net.joaoqalves.sort;

import java.io.File;
import java.util.List;

public class ExternalSort {

    // 10 MB. Could be configurable or based on the available memory of the system
    private static final long BLOCK_SIZE = 10 * 1024 * 1024;

    private static long getNumberOfBlocks(final File file) {
        return (file.length() / BLOCK_SIZE) + 1;
    }

    public static void sort(final String filename, final String output) {
        File f = new File(filename);

        try {
            List<File> files = FileSplitter.split(f, ExternalSort.getNumberOfBlocks(f));
            FileMerger.merge(files, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
