/*
 * 文件名：Shop.java
 * 版权：Copyright by www.yazuo.com
 * 描述：
 * 修改人：ququ
 * 修改时间：2014年12月24日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yazuo.superwifi.merchant.vo;

import java.util.List;

public class Shop
{
    private String name;
    private String province;
    private String city;
    private String district;
    private String brand;
    private String address;
    private String category;
    private List<String> mac;
    private Integer id;
    
    
    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getProvince()
    {
        return province;
    }
    public void setProvince(String province)
    {
        this.province = province;
    }
    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public String getDistrict()
    {
        return district;
    }
    public void setDistrict(String district)
    {
        this.district = district;
    }
    public String getBrand()
    {
        return brand;
    }
    public void setBrand(String brand)
    {
        this.brand = brand;
    }
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public String getCategory()
    {
        return category;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }
    public List<String> getMac()
    {
        return mac;
    }
    public void setMac(List<String> mac)
    {
        this.mac = mac;
    }
    
}
