package com.baomidou.mybatisplus.generator.config.po;

public class StaticCell {
    public StaticCell(String value, String name) {
        this.value = value;
        this.name = name;
    }

    private String value;
    private String name;

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

    @Override
    public String toString() {
        return "StaticCell{" +
                "value='" + value + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
