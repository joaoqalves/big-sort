package net.joaoqalves.sort;

import net.joaoqalves.utils.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileSplitter {

    public static File sortAndWrite(List<String> tmplist) {
        // Sort phase. We could implement our own algorithm (quicksort, etc)
        // Too boring. Java8 makes it almost as good as Scala code :)
        tmplist = tmplist.parallelStream()
                .sorted(String::compareTo)
                .collect(Collectors.toList());

        // Write phase.
        OutputStream out = null;
        BufferedWriter bw = null;
        File f = null;
        try {
            f = File.createTempFile("sort", "tmp");
            f.deleteOnExit(); // We're lazy, so we won't to implement a cleanup function.

            out = new FileOutputStream(f);
            bw = new BufferedWriter(new OutputStreamWriter(out));
            for (String s : tmplist) {
                bw.write(s);
                bw.newLine();
            }
        } catch (FileNotFoundException fnfex) {
            System.err.println("File not found: " + f.getAbsolutePath());
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } finally {
            IOUtils.close(bw);
            IOUtils.close(out);
        }
        return f;
    }

    public static List<File> split(final File file, final long blockSize) {

        InputStream in = null;
        BufferedReader br = null;
        List<File> tmpFiles = new ArrayList<>();
        try {
            in = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(in));
            List<String> tmplist = new ArrayList<>();
            String line = "";
            try {
                while (line != null) {
                    long currentBlockSize = 0;
                    while ((currentBlockSize < blockSize) && ((line = br.readLine()) != null)) {
                        tmplist.add(line);
                        currentBlockSize += line.length();
                    }

                }
                tmpFiles.add(sortAndWrite(tmplist));
                tmplist.clear();
            } catch (EOFException eof) {
                if (tmplist.size() > 0) {
                    tmpFiles.add(sortAndWrite(tmplist));
                    tmplist.clear();
                }
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
        } catch (FileNotFoundException fnfex) {
            System.err.println("File not found: " + file.getAbsolutePath());
        } finally {
            IOUtils.close(br);
            IOUtils.close(in);
        }

        return tmpFiles;
    }

}
