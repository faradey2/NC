import java.lang.reflect.Array;

public class ArrayVectorImpl implements ArrayVector{

    double[] array;
    public ArrayVectorImpl(){
        array = new double[1];
    }

    @Override
    public void set(double... elements) {
        array = elements;
    }

    @Override
    public double[] get() {
        return array;
    }

    @Override
    public ArrayVector clone() {
        ArrayVectorImpl v = new ArrayVectorImpl();
        v.set(array.clone());
        return v;
    }

    @Override
    public int getSize() {
        return array.length;
    }

    @Override
    public void set(int index, double value) {
        if(index < 0 ) return;
        if(index >= array.length){
            double[] newArr = new double[index + 1];
            for(int i=0;i<array.length;i++)
                newArr[i] = array[i];
            newArr[index] = value;
            array = newArr;
        }
        else    array[index] = value;
    }

    @Override
    public double get(int index) throws ArrayIndexOutOfBoundsException {
        if(index < 0 || index >= array.length) throw new ArrayIndexOutOfBoundsException();
        else return array[index];
    }

    @Override
    public double getMax() {
        if(array.length == 0) return 0;

        double max = array[0];
        for(int i = 1; i < array.length; i++)
            if(array[i] > max)
                max = array[i];

        return max;
    }

    @Override
    public double getMin() {
        if(array.length == 0) return 0;

        double min = array[0];
        for(int i = 1; i < array.length; i++)
            if(array[i] < min)
                min = array[i];

        return min;
    }

    @Override
    public void sortAscending() {
        // BubbleSort
        if(array.length < 1) return;
        int indexMax = 0;
        for(int i = 0 ; i < array.length; i++){
            indexMax = 0;
            for(int j = 0; j < array.length - i; j++)
                if(array[j] > array[indexMax]) indexMax = j;

            double item = array[array.length - 1 - i];
            array[array.length - 1 - i] = array[indexMax];
            array[indexMax] = item;
        }
    }

    @Override
    public void mult(double factor) {
        for(int i = 0 ; i < array.length; i++)
            array[i] *= factor;
    }

    @Override
    public ArrayVector sum(ArrayVector anotherVector) {
        int len = (anotherVector.getSize() > this.getSize())? this.getSize() : anotherVector.getSize();

        for(int i = 0; i < len; i++)
            array[i] += anotherVector.get(i);

        return this;
    }

    @Override
    public double scalarMult(ArrayVector anotherVector) {
        double result = 0.0;
        int len = (anotherVector.getSize() > this.getSize())? this.getSize() : anotherVector.getSize();

        for(int i = 0; i < len; i++)
            result += (anotherVector.get(i) * array[i]);

        return result;
    }

    @Override
    public double getNorm() {
        return Math.sqrt(this.scalarMult(this));
    }
}
