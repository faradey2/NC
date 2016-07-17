import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounterImpl implements WordCounter{

    String text;
    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Map<String, Long> getWordCounts() throws IllegalStateException {
        if(text == null) throw new IllegalStateException();
        Map<String,Long> result = new TreeMap<String,Long>();
        String t = getText().replaceAll("((<)|(&lt)).*?((>)|(&gt))","");
        String[] split = t.split("\\s+");
        for(String s: split){
            if(s.length()<1)continue;
            String str = s.toLowerCase().replaceAll("[\\(\\)\\.,:;-]","");
            Long val = result.get(str);
            if(val == null)
                result.put(str,new Long(1));
            else    result.put(str,new Long(val + 1));
        }
        return result;
    }

    @Override
    public List<Map.Entry<String, Long>> getWordCountsSorted() throws IllegalStateException {
        return sortWordCounts(getWordCounts());
    }

    @Override
    public List<Map.Entry<String, Long>> sortWordCounts(Map<String, Long> orig) {
        List<Map.Entry<String, Long>> result = new ArrayList<Map.Entry<String, Long>>(orig.entrySet());
        class comp implements Comparator<Map.Entry<String, Long>>{

            @Override
            public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
                if(o1.getValue()<o2.getValue())
                    return 1;
                else if (o1.getValue()>o2.getValue())
                    return -1;
                if(o1.getKey().compareToIgnoreCase(o2.getKey())<0)
                    return -1;
                else if (o1.getKey().compareToIgnoreCase(o2.getKey())>0)
                    return 1;
                return 0;
            }
        }

        Collections.sort(result ,new comp());
        return result;
    }
}