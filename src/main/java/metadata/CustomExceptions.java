package metadata;

public  class CustomExceptions {
    public static class InvalidInputException extends Exception{
        public InvalidInputException(String s){
            super(s);
        }
    }
}
