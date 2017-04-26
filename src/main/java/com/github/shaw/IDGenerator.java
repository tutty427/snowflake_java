package com.github.shaw;

/**
 * Created by Shaw on 2017/4/26.
 */
public class IDGenerator {

    private final static int APP_SHIFT = 10;
    private final static int SEQ_SHIFT = 12;

    private final static int MAX_APP_ID = 1 << APP_SHIFT;
    private final static int MAX_SEQ_NUM = 2 << SEQ_SHIFT;

    private int appId;
    private long referenceTime;
    private short sequence;

    public IDGenerator(int appId){

        if(appId < 0 || appId > MAX_APP_ID){
            throw new IllegalArgumentException(String.format("appId is invalidate! must between %s and %s",0,MAX_APP_ID));
        }

        this.appId = appId;
    }


    public long getUniqueId(){
        long currentTime = System.currentTimeMillis();
        long currentSeq;
        synchronized(this) {
            if(currentTime < referenceTime){
                throw new IllegalArgumentException("Time is back");
            }else if(currentTime > referenceTime){
                this.sequence = 0;
            }else{
               if(this.sequence < MAX_SEQ_NUM){
                   this.sequence++;
               }else{
                   throw new IllegalArgumentException("sequence is full");
               }
            }
            currentSeq = sequence;
            referenceTime = currentTime;
        }
        return currentTime << APP_SHIFT | appId << MAX_SEQ_NUM |currentSeq;
    }

}
