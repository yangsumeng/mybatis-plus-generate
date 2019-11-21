package com.baomidou.mybatisplus.generator.config.po;

public class FieldCell {
    public FieldCell(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public FieldCell(String value, String name,String tag) {
        this.value = value;
        this.name = name;
        this.tag = tag;
    }
    private String value;
    private String name;
    private String tag;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
