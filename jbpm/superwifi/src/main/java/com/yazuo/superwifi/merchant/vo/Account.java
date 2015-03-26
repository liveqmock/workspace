/*
 * 文件名：Account.java
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

public class Account
{
private String user_name;
private String user_password;
private List<Shop> shop;
public String getUser_name()
{
    return user_name;
}
public void setUser_name(String user_name)
{
    this.user_name = user_name;
}
public String getUser_password()
{
    return user_password;
}
public void setUser_password(String user_password)
{
    this.user_password = user_password;
}
public List<Shop> getShop()
{
    return shop;
}
public void setShop(List<Shop> shop)
{
    this.shop = shop;
}

}
