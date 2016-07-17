import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class WordProcessorImpl implements WordProcessor{
    String src;

    @Override
    public String getText() {
        return src;
    }

    @Override
    public void setSource(String src) {
        if(src == null) throw new IllegalArgumentException();
        this.src = src;
    }

    @Override
    public void setSourceFile(String srcFile) throws IOException {
        if(srcFile == null) throw new IllegalArgumentException();
        File file = new File(srcFile);
        Scanner scn = new Scanner(file);
        StringBuilder text = new StringBuilder();
        while(scn.hasNextLine()){
            text.append(scn.nextLine());
            text.append('\n');
        }
        setSource(text.toString());
    }

    @Override
    public void setSource(FileInputStream fis) throws IOException {
        if(fis == null) throw new IllegalArgumentException();
        StringBuilder text = new StringBuilder();
        while (fis.available()>0)
            text.append((char)fis.read());
        setSource(text.toString());
    }

    @Override
    public Set<String> wordsStartWith(String begin) {
        if(src == null) throw new IllegalStateException();
        Set<String> set = new HashSet<String>();
        if(begin==null) begin = "";
        String[] words = getText().split("\\s+");
        for(String w: words){
            if(w.matches("(?i)"+begin+"\\S*?"))
                set.add(w.toLowerCase());
        }
        return set;
    }
}