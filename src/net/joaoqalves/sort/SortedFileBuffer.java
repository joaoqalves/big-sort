package net.joaoqalves.sort;

import java.io.*;
import java.util.*;

public class SortedFileBuffer {

    private List<BufferedReader> buffers;
    private Map<BufferedReader, String> cache;
    private Set<BufferedReader> done;

    public SortedFileBuffer(final List<File> sortedFiles) {
        this.buffers = new ArrayList<>();
        this.done = new HashSet<>();
        this.cache = new HashMap<>();

        for(File f: sortedFiles) {
            try {
                InputStream in = new FileInputStream(f);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                this.buffers.add(br);
            } catch (FileNotFoundException fex) {
                fex.printStackTrace();
            }
        }

    }

    public Map<BufferedReader, String> getCache() {
        return this.cache;
    }

    public boolean reload() throws IOException {

        for(BufferedReader br: buffers) {
            if(!this.cache.containsKey(br) && !done.contains(br)) {
                this.cache.put(br, br.readLine());

                if(this.cache.get(br) == null) {
                    done.add(br);
                }
            }
        }

        if(done.size() == buffers.size())
            return false;

        return true;
    }

}
