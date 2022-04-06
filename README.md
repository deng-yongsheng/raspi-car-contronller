# 智能小车控制终端

### 协议规定

+ 运输层协议：UDP 协议1392端口

  `sum(map(ord,'dengyongsheng'))`

### 格式

+ 请求格式

  ```json
  {
      version: 1.0,    // 版本号，数字类型
    	seq: 1000,       // 序列号，便于确认
      instruction: "", // 请求的指令
      content: {       // 指令的补充信息
          
      }
  }
  ```

+ 响应格式

  ```json
  {
      version: 1.0,    // 版本号，数字类型
    	seq: 1000,       // 请求的序列号，知道是对哪一个请求所做的响应
      msg: "",         // 响应的消息字符串摘要
      content: {       // 响应的补充信息
          
      }
  }
  ```

### 指令

#### 判断是否有设备存在

```json
// 请求
{
    instruction:"is_any_alive"
}
// 响应
{
    msg: "IP地址"
}
```

