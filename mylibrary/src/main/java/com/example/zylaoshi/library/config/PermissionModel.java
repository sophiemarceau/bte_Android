package com.example.zylaoshi.library.config;

/**
 * @auther zylaoshi
 * @date 2017/12/13 15:37
 */

public class PermissionModel {
    /**
     * 权限名称
     */
    public String name;

    /**
     * 请求的权限
     */
    public String permission;

    /**
     * 解析为什么请求这个权限
     */
    public String explain;

    /**
     * 请求代码
     */
    public int requestCode;

    public PermissionModel(String name, String permission, String explain, int requestCode) {
        this.name = name;
        this.permission = permission;
        this.explain = explain;
        this.requestCode = requestCode;
    }
}
