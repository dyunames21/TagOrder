# QR태그를 통해 접속하는 비대면 주문 및 메뉴 관리 서비스 통합 웹 어플리케이션

Project nickname : TagOrder

Project execution period : 2022.03 ~ 2022.10

Front URL : [link](https://github.com/dyunames21/TagOrder_Front.git)


---

## Description

기존 매장을 이용할 때 매장 운영형 점포들의 주문은 대부분 대면을 통한 구두 주문이나 키오스크로 이루어진다. 
<br/>해당 방식으로 주문할 때 매장 이용자들은 

   + 긴 대기 줄의 불편함, 이로 인한 매장 복잡도 상승
   + 스타벅스 같은 자사 앱 사용 시에는 브랜드마다 각각의 앱을 설치해야 하는 불편함
 
 매장 관리자의 경우에도

  + 대면 구두 주문을 통한 주문 실수 및 종이 주문서 등의 추가적 비용
  + 운영 시 발생하는 매장 내 혼잡으로 인한 고객 불만도 증가

이러한 문제점을 해결하기 위해

 + 매장 내 QR 코드를 사용하여 앱 설치 없는 웹 어플리케이션을 통한 간단한 주문과 결제 서비스 제공
 + 디지털화된 주문, 메뉴 제어 웹서비스 제공

위와 같은 서비스를 제공함으로써 해당 문제들을 해결하고자 한다.


 ![image](https://user-images.githubusercontent.com/50948044/226090666-607432ab-3322-4986-9981-215dd9d82dbc.png)
 
 
 
 ## Environment
 <br/>
 <br/>
 
![image](https://user-images.githubusercontent.com/50948044/226091719-b454aaf0-7c7f-402a-874b-1bdf7e227468.png)


 **사용기술**
 
 주요 프레임워크 / 라이브러리
 
 백앤드
 + Java 17
 + SpringBoot 2.7.3
 + JPA
  
 Build Tool
 + Gardle
 
 DataBase
 + AWS RDS:MySQL
 + EC2
 
 프론트엔드
 + JavaScript
 + Vue 
 + HTML/Css
 

## 구조 및 설계

**1.패키지 구조**

**2.DB 설계**
![image](https://user-images.githubusercontent.com/50948044/226093988-f2110615-35e6-4ca5-ae19-d19382d9863a.png)


## 실행 화면

<details>
    <summary>사용자 화면</summary>
    로그인 화면</br>
    <img src="https://user-images.githubusercontent.com/50948044/226091804-b2ddd129-0149-417f-b35f-d1bee3830136.png", width="500px"></br>
    주문 진행</br>
    <img src="https://user-images.githubusercontent.com/50948044/226091829-c1038776-f71f-4532-9822-a86e538f92a3.png", width="600px"></br>
    주문 결제진행</br>
    <img src="https://user-images.githubusercontent.com/50948044/226091861-cef1d53d-1990-4ad0-8008-2601f60eacb2.png", width="600px"></br>
    주문 완료시 문자 발송</br>
    <img src="https://user-images.githubusercontent.com/50948044/226093437-2cdcb6fd-f4b8-40f6-b23d-bf4f4800dc62.png", width="600px"></br>
   
</details>

<details>
    <summary>관리자 화면</summary>
    현재 주문 리스트</br>
    <img src="https://user-images.githubusercontent.com/50948044/226093347-2fe104ec-03a3-476e-bd9f-26f8e1c7d571.png", width="500px"></br>
    시작을 누르면 Polling을 통해 주문 감지, 파악 </br>
    구매자가 정해 놓은 이름, 주문 메뉴 파악</br>
    주문 진행 후 완료하기 누르면  구매자에게 문자 전달, 주문 완료</br>
    </br>
    메뉴 실시간 CRUD</br>
    <img src="https://user-images.githubusercontent.com/50948044/226093387-399ff5eb-1b11-469c-b349-36433c9680a5.png", width="600px"></br>
    
</details>

## 성과
 + AWS를 통한 서비스를 진행하면서, AWS 서비스에 대한 이해도 증가.
 + 웹에서의 비동기 통신 로직에 대해 깊게 생각해보는 계기가 되었으며, 실시간 비동기 환경을 구현해 보는 계기가 되었음.
 + 풀스택 개발을 경험하면서 프론트와 백엔드 관계에 대해 실질적으로 다뤄보는 계기가 되었음. 이후 웹소켓 관련 기술과 PWA 환경에 대해 좀 더 관심을 가지게 되었고 현재는 관련 기술을 공부하고있음.
 
 + 해당 서비스를 구현하면서 가장 목표로 한것은 시현시에 실제 상용되는 시스템으로 보이고 싶었다. 
그래서 결제시스템과 주문완료 뒤 구매자에게 어떤 방법으로 알릴것인가가 전체적인 서비스 구현에서의 가장 큰 문제점이었으며, 웹 환경에서는(특히 IOS)에서는 보안을 위해 웹으로 알림을 주는것이 어렵기 때문에 PWA에 대한 방안도 생각해 보았으나, 시간적인 문제로 간단한 문자전송 API를 사용하여 주문시에 입력한 전화번호로 전송되게끔 문제를 해결하였는데, 그렇게 만족스럽지는 않았다.

+ 실제 상용되는 서비스를 생각했을때 고려할 또 다른 부분이 있었는데
테스트 때 처럼 세네명이 아닌, 더 많은 다수의 사용자의 오더가 왔을때의 대처에 대해서 비동기적(axios)으로 문제를 해결 하였지만, 실제 대규모의 서비스를 접해보고 싶다는 생각이 들었다.
