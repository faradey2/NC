import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckerImpl implements Checker{

    @Override
    public Pattern getPLSQLNamesPattern() {
        return Pattern.compile("[a-zA-Z][a-zA-Z_$0-9]{0,29}");
    }

    @Override
    public Pattern getHrefURLPattern() {
        return Pattern.compile("(?i)((&lt)|(<))\\s*a\\s*href\\s*=\\s*(([^\\s\"]*)|(\"[^\"]*?\"))\\s*\\/?((&gt)|(>))");
    }

    @Override
    public Pattern getEMailPattern() {
        String accountPattern = "[a-zA-Z0-9][0-9a-zA-Z_.-]{0,20}[a-zA-Z0-9]";
        String domenPattern = "([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9].)";
        String domenFirstLevelPattern = "((ru)|(net)|(com)|(org))";
        return Pattern.compile(accountPattern+"@("+ domenPattern+")+"+domenFirstLevelPattern);
    }

    @Override
    public boolean checkAccordance(String inputString, Pattern pattern) throws IllegalArgumentException {
        if((inputString==null ^ pattern==null))throw new IllegalArgumentException();
        else if(inputString==null && pattern==null) return true;
        return pattern.matcher(inputString).matches();
    }

    @Override
    public List<String> fetchAllTemplates(StringBuffer inputString, Pattern pattern) throws IllegalArgumentException {
        if(inputString==null || pattern==null)throw new IllegalArgumentException();
        List<String> list = new ArrayList<String>();
        Matcher m = pattern.matcher(inputString);
        while(m.find())
            list.add(m.group());
        return list;
    }
}