尽可能封装rsocket请求、响应，提高代码可阅读性：

- bear token
  
  登录成功后，将token存入localStorage

- 接口约定
  
  route以`anonymous.*`开头，默认是用户未登录状态可以访问的接口。否则，从localStorage获取token，构建oauth请求

- 使用`async, await, Promise`等异步编程的API封装
