# HIGHERX

## jwt 인증 및 리프레시 토큰
```
   - 추리 과정 
   
   auth의 로그아웃 
   로그아웃 같은 경우는 보통 jwt같은경우 백엔드 측에서 가지고 있을 이유가 꼭 없기 때문에 
   보통은 로그아웃 api를 백엔드 측에서 안만드는 경우도 많다
   
   +
   
   토큰 재발급 기능 
   위와 마찬가지로 토큰 재발급 기능은 보통 로그인을 유지 시킬 경우에 필요하다 
   보통 재발급용 토큰을 백엔드 측에서 가지고 있는경우가 있다
   
   = 
   
   따라서 단순 jwt가 아닌 리프레시 토큰까지 만들라는 이야기가 된다 
     
```

## todo 관련
```
   1. 할 일 설명 부분에 텍스트 길이에 대한 것이 기획에서 정해지지 않음 
   일단은 mysql varchar 2046로 해놓긴 함
   
   2. 할일 목록을 했는지 안했는지에 대한 체크박스를 클릭하는 api가 없다 
   /v1/todo/{id}/check를 하나 추가했다 
   
   3. delete /v1/todo -> /v1/todo/{id}
```

## 버전 관리 관련
```
   모바일 앱은 업데이트를 유저에게 시킬지 말지가 좀 중요해서 
   이전버전의 api, 현재 api 이런식으로 나누는게 중요한데 명세에 없어서 /v1 추가 
```

## 유저 관련
```
   휴대폰 번호를 스트링으로 저장함 나중에 필요에 따라 이야기 해보고 결정해야함
```


