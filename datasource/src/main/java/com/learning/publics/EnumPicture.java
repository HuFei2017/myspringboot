package com.learning.publics;

public enum EnumPicture {

    ONE("/user/hdfs/catalog/ico_02.png",1),
    TWO("/user/hdfs/catalog/ico_04.png",2),
    THREE("/user/hdfs/catalog/ico_05.png",3),
    FOUR("/user/hdfs/catalog/ico_06.png",4),
    FIVE("/user/hdfs/catalog/ico_08.png",5),
    SIX("/user/hdfs/catalog/ico_10.png",6),
    SEVEN("/user/hdfs/catalog/ico_12.png",7),
    EIGHT("/user/hdfs/catalog/ico_14.png",8),
    NINE("/user/hdfs/catalog/ico_16.png",9);

    private String name ;
    private int index ;

    private EnumPicture(String name , int index ){
        this.name = name ;
        this.index = index ;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }



}
