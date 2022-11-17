# `httpz`

> `httpz` is an `IDE` `http` request client  `plugin`, similar to `cURL `
> or  `Httpie`, supports `yml ` language programming, more flexible and convenient
> to use. It uses a custom extension (`.httpz`) file description, supports syntax
> highlighting and one-click request, and supports one-click jumping to
> the `.httpz` file by modifying the request annotation in the Spring environment.

## 1.`Logo/Icon`

```http
https://www.iconfont.cn/search/index?searchType=icon&q=http
```

- `normal`
  - ![httpz](./src/main/resources/icons/httpz.png)
- `dark`
  - ![httpz_dark](./src/main/resources/icons/httpz_dark.png)

## 2.`Dependencies`

- `Okhttp3`
- `Fastjson2`

## 3.`Design`

### 3.1.`Option`

#### 3.1.1.`traditional`

- "--header, -H"
  - `-H "Content-Type:application/json"`
  - `--header "Accept: */*"`
- "--data, -d"
  - `-d "{\"hello\":\"world\"}"`
- "--query, -q"
  - `--query name=photowey --query age=18`
  - `-q name=photowey -q age=18`
- "--user-agent, -A"
  - `--user-agent httpz/1.0.0`
  - `-A httpz/1.0.0`

#### 3.1.2.`built-in`

- "`--profile, -p`"
- "`--config, -c`"
  - "`--file, -f`"

### 3.2.`Expect:`

#### 3.2.1.`File.httpz`

> `xxx.httpz`

```shell
// Compatible with cURL base cmd

// ---------------------------------------------------------------- > console

// 1.POST
HTTPZ POST -H "Content-Type: application/json" -H "Accept: */*" -d "{\"hello\":\"world\"}" https://httpbin.org/post

// 2.GET
// HTTPZ GET -H "Accept: */*" https://www.baidu.com

// 3.HTTPZ built-in
HTTPZ -p httpbinpost -c conf/httpz.yml

// ---------------------------------------------------------------- > file

// 4.> httpbinpost.json
HTTPZ -p httpbinpost -c conf/httpz.yml > httpbinpost.json

// 5.>> httpbinpost.json
HTTPZ -p httpbinpost -c conf/httpz.yml > httpbinpost.json
```

#### 3.2.2.`Yml`

> `httpz.yml`

```yml
---
httpbinpost:
  url: https://httpbin.org/post
  method: POST
  parameters:
    name: hello
  headers:
    content-type: "application/json"
    accept: "*/*"
  body:
    hello: world
    addr:
      country: cn
      province: cq
httpbinget:
  url: https://httpbin.org/get
  method: GET
  parameters:
    hello: world
  headers:
    content-type: "application/json"
    accept: "*/*"
```

### 3.3.`Recommend`

#### 3.3.1.`Dir`

```
|...
├─conf
│   httpz.yml
├─scripts
│  └─httpz
│      ├─cmd
│      │  httpbin.httpz
│      └─data
│         httpbinpost.json
|...
```

### 3.4.`Output`

#### 3.4.1.`format`

> When `httpz` receives a response from the server it will try to format the
> datagram, if necessary.

#### 3.4.2.`output`

`E.g.:`

```json
----------------------------------------------------------------
HTTPZ POST -H "Content-Type: application/json" -H "Accept: */*" -d "{\"hello\":\"world\"}" https://httpbin.org/post
-------
Output:
-------
{
	"args":{
		
	},
	"headers":{
		"Accept":"*/*",
		"Content-Length":"17",
		"Content-Type":"application/json",
		"Host":"httpbin.org",
		"User-Agent":"httpz/1.0.0",
		"X-Amzn-Trace-Id":"Root=xxx"
	},
	"data":"{\"hello\":\"world\"}",
	"form":{
		
	},
	"origin":"x.x.x.x",
	"files":{
		
	},
	"json":{
		"hello":"world"
	},
	"url":"https://httpbin.org/post"
}
```

