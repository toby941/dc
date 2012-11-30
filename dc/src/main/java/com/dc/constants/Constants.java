package com.dc.constants;

public class Constants {

    /**
     * LogInfo
     */
    // 登陆
    public static String REQUEST_LOGIN = "Login";
    // 注销
    public static String REQUEST_Logout = "Logout";
    // 用户切换
    public static String REQUEST_SwitchUser = "SwitchUser";

    /**
     * TableInfo
     */
    // 获取餐桌信息
    public static String REQUEST_GetTables = "GetTables";
    // 开台
    public static String REQUEST_OpenTable = "OpenTable";
    // 销台
    public static String REQUEST_CloseTable = "CloseTable";
    /**
     * MenuInfo
     */
    // 获取完整菜单 获取分页菜单 请求参数 TabId为-1代表请求所有菜单
    public static String REQUEST_GetMenuList = "GetMenuList";
    // 获取已点菜单
    public static String REQUEST_GetOrderList = "GetOrderList";
    // 点菜下单
    public static String REQUEST_OrderMenu = "OrderMenu";
    /**
     * Services
     */
    // 催菜
    public static String REQUEST_UrgeCate = "UrgeCate";
    // 缓菜
    public static String REQUEST_DelayCate = "DelayCate";
    // 结帐
    public static String REQUEST_Checkout = "Checkout";
    /**
     * Download
     */
    // 获取完整更新列表
    public static String REQUEST_GetSyncFileList = "GetSyncFileList";

    public static String LOG_NAME = "dc";
}
