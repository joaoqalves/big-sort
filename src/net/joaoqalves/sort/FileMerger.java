package net.joaoqalves.sort;

import net.joaoqalves.sort.strategies.KWayMergeStrategy;
import net.joaoqalves.sort.strategies.MergeStrategy;
import net.joaoqalves.utils.IOUtils;

import java.io.*;
import java.util.List;

public class FileMerger {


    public static void merge(final List<File> files, final String filename) {
        merge(files, filename, new KWayMergeStrategy());
    }

    public static void merge(final List<File> files, final String filename,
                             final MergeStrategy<BufferedReader, String> mergeStrategy) {
        File f =  new File(filename);
        OutputStream out = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(f);
            bw = new BufferedWriter(new OutputStreamWriter(out));

            SortedFileBuffer sf = new SortedFileBuffer(files);

            while(sf.reload()) {
                BufferedReader pickedBuffer = mergeStrategy.select(sf.getCache());
                bw.write(sf.getCache().get(pickedBuffer));
                bw.newLine();
                sf.getCache().remove(pickedBuffer);
            }
        } catch (FileNotFoundException fnfex) {
            System.err.println("File not found: " + f.getAbsolutePath());
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } finally {
            IOUtils.close(bw);
            IOUtils.close(out);
        }

    }

}
