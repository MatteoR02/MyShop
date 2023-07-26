package Business;

import java.util.ArrayList;

public class ExecuteResult<T> {
    public enum ResultStatement { OK, NOT_OK, OK_WITH_WARNINGS, WRONG_ACCESS, UNDEFINED , DATA_DELETED}

    private ResultStatement result;
    private String message;
    private ArrayList<T> object;

    public ExecuteResult() {
        this.object = new ArrayList<T>();
        this.message = "";
        this.result = null;
    }

    public void execute(String sessionType){
        SessionManager.getSession().put(sessionType,object);
    }


    public T getObjectFromArray(int i){
        return this.object.get(i);
    }

    public boolean isEmpty(){
        if(object != null ) return false;
        else return true;
    }

    public void resetObjectOnly(){
        object.clear();
    }

    public void removeFromArray(int i){
        this.object.remove(i);
    }

    public T getSingleObject(){
        return object.get(0);
    }

    public void setSingleObject(T obj){
        if(object.isEmpty()) object.add(obj);
    }

    public ArrayList<T> getObject() {
        return object;
    }

    public void setObject(ArrayList<T> object) {
        this.object = object;
    }

    public ResultStatement getResult() {
        return result;
    }

    public void setResult(ResultStatement result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void reset(){
        //hashMap.clear();
        object.clear();
        result = null;
        message = "";
    }
}
