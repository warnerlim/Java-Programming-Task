package uob.oop;

public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        this.doubElements = _elements;
        //TODO Task 3.1 - 0.5 marks
    }

    public double getElementatIndex(int _index) {
        //TODO Task 3.2 - 2 marks
        if (_index >= 0 && _index < getVectorSize()) {
            return getAllElements()[_index];
        }
            else {
            return -1;
        }
    }

    public void setElementatIndex(double _value, int _index) {
        if (_index >= 0 && _index < getVectorSize()) {
            getAllElements()[_index] = _value;
        }
            else {
                int length = (getVectorSize() - 1);
                getAllElements()[length] = _value;
        }
        //TODO Task 3.3 - 2 marks

    }

    public double[] getAllElements() {
        //TODO Task 3.4 - 0.5 marks
        return doubElements; //you need to modify the return value
    }

    public int getVectorSize() {
        //TODO Task 3.5 - 0.5 marks
        return doubElements.length; //you need to modify the return value
    }

    public Vector reSize(int _size) {
        if (_size <= 0) {
            return new Vector(getAllElements());
        }

        double[] tempARR = new double[_size];
        for (int i = 0; i < Math.min(getVectorSize(), _size); i++) {
            tempARR[i] = getElementatIndex(i); // Use getElementatIndex to access elements
        }
        for (int i = getVectorSize(); i < _size; i++) {
            tempARR[i] = -1;
        }
        return new Vector(tempARR);
    }

    public Vector add(Vector _v) {
        int newSize = Math.max(getVectorSize(), _v.getVectorSize());
        Vector create = new Vector(getAllElements());

        if (create.getVectorSize() < _v.getVectorSize()){
            create.reSize(newSize);
        }
        else if (create.getVectorSize() > _v.getVectorSize()){
            _v.reSize(newSize);
        }

        double[] add_array = new double[newSize];
        for (int i = 0; i < newSize; i++) {
            double element1 = create.getElementatIndex(i);
            double element2 = _v.getElementatIndex(i);

            add_array[i] = element1 + element2;
        }

        return new Vector(add_array);
    }

    public Vector subtraction(Vector _v) {
        int newSize = Math.max(getVectorSize(), _v.getVectorSize());
        Vector create = new Vector(getAllElements());

        if (create.getVectorSize() < _v.getVectorSize()){
            create.reSize(newSize);
        }
        else if (create.getVectorSize() > _v.getVectorSize()){
            _v.reSize(newSize);
        }

        double[] subtract_array = new double[newSize];
        for (int i = 0; i < newSize; i++) {
            double element1 = create.getElementatIndex(i);
            double element2 = _v.getElementatIndex(i);

            subtract_array[i] = element1 - element2;
        }

        return new Vector(subtract_array);
    }

    public double dotProduct(Vector _v) {
        int newSize = Math.max(getVectorSize(), _v.getVectorSize());
        Vector create = new Vector(getAllElements());

        if (create.getVectorSize() < _v.getVectorSize()){
            create.reSize(newSize);
        }
        else if (create.getVectorSize() > _v.getVectorSize()){
            _v.reSize(newSize);
        }

        double dot_product_values = 0;

        for (int i = 0; i < newSize; i++) {
            double element1 = create.getElementatIndex(i);
            double element2 = _v.getElementatIndex(i);

            dot_product_values += element1 * element2;
        }

        return dot_product_values;
    }


    //Resize both and also use dot product method
    public double cosineSimilarity(Vector _v) {
        int newSize = Math.max(getVectorSize(), _v.getVectorSize());
        Vector create = new Vector(getAllElements());

        if (create.getVectorSize() < _v.getVectorSize()){
            create.reSize(newSize);
        }
        else if (create.getVectorSize() > _v.getVectorSize()){
            _v.reSize(newSize);
        }

        double dot_product_values = create.dotProduct(_v);

        double A = 0.0;
        double B = 0.0;

        for (int i = 0; i < newSize; i++) {
            double element1 = create.getElementatIndex(i);
            double element2 = _v.getElementatIndex(i);

            A += Math.pow(element1, 2);
            B += Math.pow(element2, 2);
        }

        return dot_product_values / (Math.sqrt(A) * Math.sqrt(B));
    }

    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;

        if (this.getVectorSize() != v.getVectorSize())
            return false;

        for (int i = 0; i < this.getVectorSize(); i++) {
            if (this.getElementatIndex(i) != v.getElementatIndex(i)) {
                boolEquals = false;
                break;
            }
        }
        return boolEquals;
    }

    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}
