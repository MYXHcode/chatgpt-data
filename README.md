# chatgpt-data | DDD 工程分层架构

## 测试脚本

### 1. 验证码

```shell
curl -X POST \
 http://localhost:8090/api/v1/auth/gen/code \
-H 'Content-Type: application/x-www-form-urlencoded' \
-d 'openid=o0G6z6h-nHpZFUZVrcPJayOdN884'
```

```shell
$headers = @{
    'Content-Type' = 'application/x-www-form-urlencoded'
}

$body = 'openid=o0G6z6h-nHpZFUZVrcPJayOdN884'

Invoke-WebRequest -Method POST -Uri 'http://localhost:8090/api/v1/auth/gen/code' -Headers $headers -Body $body
```
- 也可以通过启动本地 natapp 内网穿透，对接公众号进行获取验证码

### 2. 登录 - 获取 Token

```shell
curl -X POST \
http://localhost:8090/api/v1/auth/login \
-H 'Content-Type: application/x-www-form-urlencoded' \
-d 'code=0293'
```

```shell
$headers = @{
'Content-Type' = 'application/x-www-form-urlencoded'
}

$body = 'code=0293'

Invoke-WebRequest -Method POST -Uri 'http://localhost:8090/api/v1/auth/login' -Headers $headers -Body $body
```

- 登录后可以获取 Token

### 3. 功能 - 流式问题

```shell
curl -X POST \
http://localhost:8090/api/v1/chatgpt/chat/completions \
-H 'Content-Type: application/json;charset=utf-8' \
-H 'Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvMEc2ejZoLW5IcFpGVVpWcmNQSmF5T2ROODg0Iiwib3BlbklkIjoibzBHNno2aC1uSHBaRlVaVnJjUEpheU9kTjg4NCIsImV4cCI6MTcwODM0ODYwOSwiaWF0IjoxNzA3NzQzODA5LCJqdGkiOiI3NjM5ZGQxOC1lNzI3LTRmZTYtODU4ZC02NDAyMTY5ZjFmYWIifQ.jzmO_aYmnTCkzeottEXNknUItUL6Qv2mD9LOaccuaPo' \
-d '{
"messages": [
{
"content": "1+1",
"role": "user"
}
],
"model": "gpt-3.5-turbo"
}'
```

```shell
$headers = @{
    'Content-Type' = 'application/json;charset=utf-8';
    'Authorization' = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvMEc2ejZoLW5IcFpGVVpWcmNQSmF5T2ROODg0Iiwib3BlbklkIjoibzBHNno2aC1uSHBaRlVaVnJjUEpheU9kTjg4NCIsImV4cCI6MTcwODM0ODYwOSwiaWF0IjoxNzA3NzQzODA5LCJqdGkiOiI3NjM5ZGQxOC1lNzI3LTRmZTYtODU4ZC02NDAyMTY5ZjFmYWIifQ.jzmO_aYmnTCkzeottEXNknUItUL6Qv2mD9LOaccuaPo'
}

$body = @'
{
  "messages": [
    {
      "content": "1+1",
      "role": "user"
    }
  ],
  "model": "gpt-3.5-turbo"
}
'@

# Convert the body to JSON
$jsonBody = ConvertTo-Json $body

# Send the POST request
Invoke-WebRequest -Method POST -Uri 'http://localhost:8090/api/v1/chatgpt/chat/completions' -Headers $headers -Body $jsonBody -ContentType 'application/json;charset=utf-8'
```
- Token 是通过登录从控制台复制的，注意可别复制错了。

### 4. 查询商品列表

```shell
curl -X GET \
-H "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvMEc2ejZoLW5IcFpGVVpWcmNQSmF5T2ROODg0Iiwib3BlbklkIjoibzBHNno2aC1uSHBaRlVaVnJjUEpheU9kTjg4NCIsImV4cCI6MTcwODM0ODYwOSwiaWF0IjoxNzA3NzQzODA5LCJqdGkiOiI3NjM5ZGQxOC1lNzI3LTRmZTYtODU4ZC02NDAyMTY5ZjFmYWIifQ.jzmO_aYmnTCkzeottEXNknUItUL6Qv2mD9LOaccuaPo" \
-H "Content-Type: application/x-www-form-urlencoded" \
http://localhost:8090/api/v1/sale/query_product_list
```

```shell
$headers = @{
    'Authorization' = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvMEc2ejZoLW5IcFpGVVpWcmNQSmF5T2ROODg0Iiwib3BlbklkIjoibzBHNno2aC1uSHBaRlVaVnJjUEpheU9kTjg4NCIsImV4cCI6MTcwODM0ODYwOSwiaWF0IjoxNzA3NzQzODA5LCJqdGkiOiI3NjM5ZGQxOC1lNzI3LTRmZTYtODU4ZC02NDAyMTY5ZjFmYWIifQ.jzmO_aYmnTCkzeottEXNknUItUL6Qv2mD9LOaccuaPo'
    'Content-Type' = 'application/x-www-form-urlencoded'
}

Invoke-WebRequest -Method Get -Uri 'http://localhost:8090/api/v1/sale/query_product_list' -Headers $headers
```

### 5. 用户下单商品

```shell
curl -X POST \
-H "Authorization: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvMEc2ejZoLW5IcFpGVVpWcmNQSmF5T2ROODg0Iiwib3BlbklkIjoibzBHNno2aC1uSHBaRlVaVnJjUEpheU9kTjg4NCIsImV4cCI6MTcwODM0ODYwOSwiaWF0IjoxNzA3NzQzODA5LCJqdGkiOiI3NjM5ZGQxOC1lNzI3LTRmZTYtODU4ZC02NDAyMTY5ZjFmYWIifQ.jzmO_aYmnTCkzeottEXNknUItUL6Qv2mD9LOaccuaPo" \
-H "Content-Type: application/x-www-form-urlencoded" \
-d "productId=1001" \
http://localhost:8090/api/v1/sale/create_pay_order
```

```shell
$headers = @{
    'Authorization' = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJvMEc2ejZoLW5IcFpGVVpWcmNQSmF5T2ROODg0Iiwib3BlbklkIjoibzBHNno2aC1uSHBaRlVaVnJjUEpheU9kTjg4NCIsImV4cCI6MTcwODM0ODYwOSwiaWF0IjoxNzA3NzQzODA5LCJqdGkiOiI3NjM5ZGQxOC1lNzI3LTRmZTYtODU4ZC02NDAyMTY5ZjFmYWIifQ.jzmO_aYmnTCkzeottEXNknUItUL6Qv2mD9LOaccuaPo'
    'Content-Type' = 'application/x-www-form-urlencoded'
}

$body = @{
    'productId' = '1001'
}

Invoke-WebRequest -Method Post -Uri 'http://localhost:8090/api/v1/sale/create_pay_order' -Headers $headers -Body $body -ContentType 'application/x-www-form-urlencoded'
```