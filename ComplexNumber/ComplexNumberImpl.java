import java.util.Arrays;

public class ComplexNumberImpl implements ComplexNumber{

    private double Re;
    private double Im;

    public ComplexNumberImpl(){
        Re = 0.0;
        Im = 0.0;
    }

    public ComplexNumberImpl(double a, double b){
        Re = a;
        Im = b;
    }

    public ComplexNumberImpl(String s){
        this.set(s);
    }

    @Override
    public double getRe() {
        return Re;
    }

    @Override
    public double getIm() {
        return Im;
    }

    @Override
    public boolean isReal() {
        if(Im == 0.0) return true;
        else          return false;
    }

    @Override
    public void set(double re, double im) {
        Re = re;
        Im = im;
    }

    @Override
    public void set(String value) throws NumberFormatException {

        double _re = 0.0;
        double _im = 0.0;
        String str = "";
        // если в начале отсутствует знак - добавляем '+'
        if(value.charAt(0)!='-' && value.charAt(0)!='+')
            str +='+';

        // если перед i не стоит числа, то добавляем 1
        for(int i=0;i<value.length();i++) {
            if (value.charAt(i) == 'i' && (i == 0 || value.charAt(i - 1) == '+' || value.charAt(i - 1) == '-'))
                str += '1';
            str += value.charAt(i);
        }

        // считаем количество знаков +/-
        int signCount = 0;
        for(int i = 0; i < str.length(); i++)
            if(str.charAt(i)=='-'||str.charAt(i)=='+')
                signCount++;


        if(signCount > 2) return;
        else if(signCount < 2){
            if(str.charAt(str.length()-1)=='i')
            {
                try{
                    _im = Double.parseDouble(str.substring(0,str.length()-1));
                }
                catch (Exception e) {
                    throw new NumberFormatException();
                }
            }
            else{
                try{
                    _re = Double.parseDouble(str);
                }
                catch (Exception e) {
                    throw new NumberFormatException();
                }
            }
        }
        else {
            if(str.charAt(str.length()-1) != 'i') throw new NumberFormatException();
            String str1 = "" + str.charAt(0);
            for (int i = 1; i < str.length(); i++)
                if (str.charAt(i) == '-' || str.charAt(i) == '+') break;
                else str1 += str.charAt(i);
            str = str.substring(str1.length(), str.length() - 1);
            try{
                _re = Double.parseDouble(str1);
                _im = Double.parseDouble(str);
            }
            catch(Exception e){
                throw new NumberFormatException();
            }
        }

        Re = _re;
        Im = _im;
    }

    @Override
    public ComplexNumber copy() {
        return new ComplexNumberImpl(Re,Im);
    }

    @Override
    public ComplexNumber clone() throws CloneNotSupportedException {
        return new ComplexNumberImpl(Re,Im);
    }

    @Override
    public String toString(){
        String result = "";
        if(Re != 0.0)    result += Double.toString(Re);
        if(Im != 0.0){
            if(Re != 0.0 && Im > 0) result += "+";
            result += Double.toString(Im) + "i";
        }
        if(result.length() == 0)
            result = "0";
        return  result;
    }

    public boolean equals(Object other){
        ComplexNumber oth ;
        try{
            oth = (ComplexNumber)other;
            if(oth.getIm() == Im && oth.getRe() == Re) return true;
            else return false;
        }
        catch(Exception e) {
            return false;
        }
    }

    @Override
    public int compareTo(ComplexNumber other) {
        double a = Re*Re + Im*Im;
        double b = other.getRe()*other.getRe() + other.getIm()*other.getIm();
        if(a < b) return -1;
        else if(a > b)  return 1;
        else return 0;
    }

    @Override
    public void sort(ComplexNumber[] array) {
        Arrays.sort(array);
    }

    @Override
    public ComplexNumber negate() {
        Re *= -1;
        Im *= -1;
        return this;
    }

    @Override
    public ComplexNumber add(ComplexNumber arg2) {
        Re += arg2.getRe();
        Im += arg2.getIm();
        return this;
    }

    @Override
    public ComplexNumber multiply(ComplexNumber arg2) {
        double a = Re * arg2.getRe() - Im * arg2.getIm();
        double b = Re * arg2.getIm() + Im * arg2.getRe();
        Re = a;
        Im = b;
        return this;
    }
}
