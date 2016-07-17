import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurriculumVitaeImpl implements CurriculumVitae{
    String text;

    List<Replacement> replNotebook = new LinkedList<Replacement>();

    public static final String NAME_PATTERN = "[A-Z][a-z]*[a-z\\.]";
    public static final String FULL_NAME_PATTERN = '('+NAME_PATTERN + ")( ("+NAME_PATTERN + "))? ("+NAME_PATTERN + ")";
    public static final String HIDE_PHONE_PATTERN = "(\\(?([X][X]{2})\\)?[-. ]*)?([X][X]{2})[-. ]*(X{2})[-. ]*(X{2})(\\s*ext\\.?\\s*([X]+))?";

    public CurriculumVitaeImpl(){
    }

    public CurriculumVitaeImpl(String text){
        setText(text);
    }

    @Override
    public void setText(String text) {
        this.text = text;
        replNotebook.clear();
    }

    @Override
    public String getText() throws IllegalStateException {
        if(this.text == null) throw new IllegalStateException();
        return this.text;
    }

    @Override
    public List<Phone> getPhones() throws IllegalStateException {
        Pattern p = Pattern.compile(PHONE_PATTERN);
        Matcher m = p.matcher(getText());
        List<Phone> result = new ArrayList<>() ;
        Phone phone;
        while(m.find()) {
            int aCode = -1;
            int ext = -1;
            if(m.group(2)!=null) aCode = Integer.parseInt(m.group(2));
            if(m.group(7)!=null) ext = Integer.parseInt(m.group(7));
            phone = new Phone(m.group(),aCode,ext);
            result.add(phone);

        }
        return result;
    }

    @Override
    public String getFullName() throws NoSuchElementException, IllegalStateException {
        Pattern p = Pattern.compile(FULL_NAME_PATTERN);
        Matcher m = p.matcher(getText());
        boolean flag = false;
        if (!m.find()) throw new NoSuchElementException();
        if (m.end() != getText().length() &&
                !getText().substring(m.end(), m.end() + 1).matches("\\s"))
            flag = true;
        if(m.start() != 0 &&
                !getText().substring(m.start()-1, m.start()).matches("\\s"))
            flag = true;
        while (flag) {
            flag = false;
            if (!m.find(m.start()+1)) throw new NoSuchElementException();
            if (    m.end() != getText().length() &&
                    !getText().substring(m.end(), m.end() + 1).matches("\\s"))
                flag = true;
            if(m.start() != 0 &&
                    !getText().substring(m.start()-1, m.start()).matches("\\s"))
                flag = true;
        }
        return m.group();
    }

    @Override
    public String getFirstName() throws NoSuchElementException, IllegalStateException {
        Pattern p = Pattern.compile(NAME_PATTERN);
        Matcher m = p.matcher(getFullName());
        if(!m.find()) throw new NoSuchElementException();
        return m.group();
    }

    @Override
    public String getMiddleName() throws NoSuchElementException, IllegalStateException {
        Pattern p = Pattern.compile(NAME_PATTERN);
        Matcher m = p.matcher(getFullName());
        if(!m.find()) throw new NoSuchElementException();
        if(!m.find()) throw new NoSuchElementException();
        String res = m.group();
        if(!m.find()) return null;
        return res;
    }

    @Override
    public String getLastName() throws NoSuchElementException, IllegalStateException {
        Pattern p = Pattern.compile(NAME_PATTERN);
        Matcher m = p.matcher(getFullName());
        String res = null;
        while(m.find())
            res = m.group();
        return res;
    }

    @Override
    public void updateLastName(String newLastName) throws NoSuchElementException, IllegalStateException {
        String lastName = getLastName();
        this.text = getText().replaceAll(lastName, newLastName);
    }

    @Override
    public void updatePhone(Phone oldPhone, Phone newPhone) throws IllegalArgumentException, IllegalStateException {
        if(!getText().contains(oldPhone.getNumber())) throw new IllegalArgumentException();
        this.text = getText().replaceAll(oldPhone.getNumber(), newPhone.getNumber());
    }

    @Override
    public void hide(String piece) throws IllegalArgumentException, IllegalStateException {
        if(!getText().contains(piece))  throw new IllegalArgumentException();

        StringBuilder str = new StringBuilder(getText());
        String cens = piece.replaceAll("[^ .@]","X");
        int len = piece.length();
        int i = str.indexOf(piece);

        while(i > -1){
            str.replace(i,i+len,cens);
            replNotebook.add(new Replacement(piece,cens,i));

            i = str.indexOf(piece,i+1);
        }

        this.text = str.toString();
    }

    @Override
    public void hidePhone(String phone) throws IllegalArgumentException, IllegalStateException {
        Pattern p = Pattern.compile(PHONE_PATTERN);
        Matcher m = p.matcher(phone);
        //if(!getText().contains(phone)) throw new IllegalArgumentException();
        if(!m.find()) throw new IllegalArgumentException();
        int aCode = -1;
        int ext = -1;
        if(m.group(2)!=null) aCode = Integer.parseInt(m.group(2));
        if(m.group(7)!=null) ext = Integer.parseInt(m.group(7));
        Phone _phone = new Phone(m.group().replaceAll("[^0-9]",""),aCode,ext);

        boolean flag  = false;
        StringBuilder str = new StringBuilder(getText());
        int i = -1;

        m = p.matcher(getText());

        while(m.find()) {
            aCode = -1;
            ext = -1;
            if (m.group(2) != null) aCode = Integer.parseInt(m.group(2));
            if (m.group(7) != null) ext = Integer.parseInt(m.group(7));
            Phone otherPhone = new Phone(m.group().replaceAll("[^0-9]", ""), aCode, ext);
            if (_phone.equals(otherPhone)) {
                flag = true;

                String cens = m.group().replaceAll("[0-9]", "X");
                i = str.indexOf(m.group(), i + 1);
                str.replace(i, i + m.group().length(), cens);
                replNotebook.add(new Replacement(m.group(), cens, i));
            }
        }

        if(!flag)    throw new IllegalArgumentException();

        this.text = str.toString();
    }

    @Override
    public int unhideAll() throws IllegalStateException {
        StringBuilder str = new StringBuilder(getText());

        for(Replacement r: replNotebook){
            str.replace(r.position, r.position+r.onSmth.length(), r.ofSmth);
        }

        int i = replNotebook.size();
        setText(str.toString());

        return i;
    /*
        int res = 0;
        Pattern p = Pattern.compile(HIDE_PHONE_PATTERN);
        Matcher m = p.matcher(getText());

        while(m.find()){
            for(int i=0;i<replNotebook.size();i++){
                if(replNotebook.get(i).onSmth.equals(m.group())){
                    t = t.replaceFirst(HIDE_PHONE_PATTERN, replNotebook.get(i).ofSmth);
                    res++;
                    break;
                }
            }
        }
        setText(t);

        return res;
        */
    }

    public class Replacement implements Comparable{
        public String ofSmth;
        public String onSmth;
        public int position;
        public Replacement(String ofSmth,String onSmth,int position){
            this.ofSmth = ofSmth;
            this.onSmth = onSmth;
            this.position = position;
        }
        public Replacement(){
            this("","",-1);
        }


        @Override
        public int compareTo(Object o) {
            Replacement r = (Replacement)o;
            if(r.onSmth.length()> this.onSmth.length())
                return 1;
            if(r.onSmth.length()< this.onSmth.length())
                return -1;
            return 0;
        }
    }
}