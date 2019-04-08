package hello.utilities;

public  class KeyGenerator {

    private  String pattern;

    public KeyGenerator() {
        this.pattern = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789" ;
    }

    public String generate(int length) {
    StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < length; i++)
            stringBuilder.append(pattern.charAt((int) (pattern.length() * Math.random())));
    return stringBuilder.toString();
    }

}
