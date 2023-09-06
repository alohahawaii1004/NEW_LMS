# YeoYu (여유 餘由)

YeoYu Project

**여유가 경쟁력 이다.**

since 2017.07.07

> *날지않는 돼지는 평범한 돼지일뿐이다. -붉은돼지 中에서-*
> 
>  YeoYu 는 Project PORCO 에서 출발했습니다.

> 
> Support2 README.md © 2023 by Yang, Ki Hwa is licensed under CC BY-ND 4.0

## VISELF

* VISF 4.0
* VISF (Vertex ID. Integration Solution Framework, AKA Paragon-Framework, AKA VISELF)
* VISELF(Vertex ID. Simple, Easy and Lightweight Framework) 는 Spring을 활용한 프레임워크 입니다.

  VISELF is based on Spring Framework.

>  VISELF는 JYSELF 에서 출발했습니다.

## 1. License

* VISELF (Vertex ID. Simple, Easy and Lightweight Framework) 는 Apache 2.0 라이선스를 따릅니다.
--------------------------
```
    Licensed under the Apache License, Version 2.0 (the "License"); 
    you may not use this file except in compliance with the License. 
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software 
    distributed under the License is distributed on an "AS IS" BASIS, 
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
    See the License for the specific language governing permissions and 
    limitations under the License.
```

* 포함된 CKEditor 4.x는 MPL 1.1 로 배포됩니다.
    + CKEditor 소스는 GitHub(https://github.com/ckeditor/ckeditor4) 에서 받으실 수 있습니다.
    
## 2. Member Info.

* 개발자
    - 양기화 차장

## 3. System Info.

* 공통
    - JDK: 1.8.x (Open JDK)
    - DBMS: MariaDB

* 개발(LOCAL)
    - OS: Windows 10 64bit
    - WAS: Tomcat 9.x

* 테스트(DEV)
    - DB Server
        * NAME:
        * DBMS:
        * DBNAME:
        * IP:
        * PORT:
        * 1D/9W:
    - WAS Server
        * NAME:
        * OS:
        * Web Server:
        * WAS:
        * IP:
        * 1D/9W:
        * war path:

* 운영(PROD)

## 4. Interface Info.

* SSO



## 5. 주의사항

1. properties.xml
    * properties.xml 파일을 개발와 운영 분리 적용해야 함
    * 운영 시 "properties.prod.xml" 파일의 내용을 "properties.xml" 에 붙여넣기 하여 빌드
    * 개발 시 "properties.dev.xml" 파일의 내용을 "properties.xml" 에 붙여넣기 하여 빌드


## 6. GUIDE

### 6.1. 일반

#### 6.1.1. 전체 디렉터리 구조

```
    +- docs                                             : documnet folder
    |
    +- lib                                              : libs folder not in maven repository
    |
    +- src
    |  +- main
    |     |
    |     +- java                                       : java source folder
    |     |
    |     +- resource                                   : resource folder
    |     |              
    |     +- webapp                                     : web context folder
    |
    +- pom.xml                                          : maven POM file
    +- LICENSE                                          : license (Apache 2.0) file
    +- README.md                                        : this document file
    +- VERSION.md                                       : application version info. file
    +- AUTHORS.md                                       : application authors info. file
```

>
> [정보]
>
> * 전체 디렉터리 구조는 Java EE Web application 구조에 기반한 Maven Project 구조를 따릅니다.
>
> * Web contenxt 구조는 HTML5 Boilerplate 구조를 따릅니다.

>
> [주의!!]
>
> url path, web directory path, package path, namespace
>
> 상세설명에서 더욱 자세하게 다루겠지만 URL 경로, 웹 디렉터리 경로와 java 팩키지 경로, SQL mapper namespace 등이 비슷하면서 차이를 가지고 있기 때문에 주의 해야 합니다.
>
>
> * URL 경로
    >   - 소문자 코볼 케이스를 사용합니다. 즉 소문자로 작성하되 단어와 단어사이에는 '-'(hyphen)을 사용합니다.
>
> * 웹 디렉터리 경로
    >   - URL 경로와 동일한 방식으로 작성합니다.
>
> * Java 팩키지 경로
    >   - 소문자 스네이크 케이스를 사용합니다. 즉 소문자로 작성하되 단어와 단어사이에는 '_'(underscore)를 사용합니다.
>
> * SQL mapper namespace
    >   - Java 패키기 경로와 유사하나 실 디렉터리 구성과 namespace 와 차이가 있습니다. (자세한것은 MyBatis 설정 참조)


#### 6.2.1. 설정

* Maven 기반 프로젝트 입니다.

    - project 에 맞게 pom.xml을 설정합니다.

* Spring 을 사용하고 있습니다.

    - web.xml, properties.xml, applicationContext.xml, dispatcher-servlet.xml 을 설정합니다.

* Log4j2 를 사용하고 있습니다.

    - log4j2.xml 을 설정합니다.

* properties.xml 설정
    - properties.모드.xml 형태로 파일들이 구성되어 있습니다.
    - properties.xml 을 기본으로 합니다.
    - 개발시에는 properties.local.xml 을 참조하여 properties.xml 을 본인의 시스템에 맞게 작성합니다.
    - properties.xml 은 절대 commit 하지 않습니다.
    - 배포시에는 미리 설정한 각각의 모드(혹은 시스템)에 따라 rename 을 이용하여 properties.xml 로 바꿔줍니다.
>
> [주의!!]
>
> 개발시에 properties.xml 은 최초 check out 이후에 절대 commit 하거나 update 하지 않습니다.

* web.xml 설정
    - Simple Security Filter 를 설정합니다.(관련 설정은 properties.xml, applicationConf.xml)
    - Spring Context Loader Listener 를 설정합니다.
    - Spring Dispatcher Servlet 를 설정합니다.

* Spring Context Loader Listener 설정
    - web.xml 에 설정되어 있어야 합니다.
    - /resources/com/vertexid/spring/conf/applicationContext.xml 을 설정합니다.
    - applicationConf.xml, transactionConf.xml, schedulerConf.xml 로 구성되어 있습니다.
    - applicationConf.xml
        * Spring context 동작을 설정합니다.
        * CLL(Context Loader Listener) 에서 읽어들일 기본 컴포넌트 들을 설정합니다.
        * properties.xml 을 읽어오도록 설정합니다.
        * 기타 추가 기능 컴포넌트를 설정합니다.
        * Simple Security Filter 관련 설정을 수행합니다.
    - transactionConf.xml
        * JDBC/JNDI 연결에대한 설정, MyBatis에 대한 설정 및 일반/배치 Transaction에 대한 설정을 수행합니다.
    - schedulerConf.xml
        * 배치작업에 대한 설정을 합니다.

>
> [주의!!]
>
> 시스템 이중화 구성시에 schedulerConf.xml 에 설정된 모든 Job 이 이중화 되기 때문에 이 부분을 해결하는 방안도 고려해서 작업해야 합니다.
>
> ex) 작업수행 시작과 동시에 토큰을 찍고 해당작업이 중복 되지 않도록 처리


* MyBatis 설정
    - /resources/com/vertexid/mybaits/conf/mybatis-config.xml 에 설정합니다.
    - 기본유형 ParamMap 에 대한 설정이 있습니다.
    - MyBatis 에서 제공하는 Query logging 대신 LogInterceptor 를 사용하고 있습니다.
        * 운영시에 로그양이 문제가 된다면 기본 Query logging 으로 전환합니다.
    - mapper 는 transactionConf.xml 설정합니다.

* Spring Dispatcher Servlet 설정
    - web.xml 에 설정되어 있어야 합니다.
    - /WEB-INF/config/spring/dispatcher-servlet.xml 에 설정합니다.
    - Controller 만 컴포넌트 스캔합니다.
    - aop 설정을 적용합니다.
    - View 를 처리하기위해 Resolver 들을 설정합니다.
        * BeanNameViewResolver: 기본 Resolver 입니다.
        * InternalResourceViewResolver: JSP 를 처리하는 Resolver 입니다.
        * JsonView: JSON 을 처리하는 Resolver 입니다.(MappingJackson2JsonView 를 상속했습니다.)
        * UrlBasedViewResolver: Apache Tiles 를 처리하는 Resolver 입니다.
    - Apache Tiles 와 연동하기 위한 설정을 합니다.

* Apache Tiles 설정
    - dispatcher-servlet.xml 에 설정되어 있어야 합니다.
    - /WEB-INF/config/tiles/tiles-default.xml 에 설정합니다.
    - Template 를 정의 하는 Layout 부분과 주소에 따른 처리를 하는 부분으로 설정할 수 있습니다.


### 6.2. Front-End

#### 6.2.1. webapp 디렉터리 구조
* 전체적인 구조는 JavaEE 의 Web 구조를 따릅니다.
* html, css, js, img 는 HTML 5 Boilerplate 기반으로 구성되어 있습니다.
* /webapp/js/module 하위와 /webapp/WEB-INF 하위를 제외하고 웹 디렉터리 경로 작명양식을 따릅니다.
* /webapp/js/module 과 /webapp/WEB-INF/jsp 의 하위는 유사한 구조를 유지해야 합니다.(module은 jsp에 부분집합)

```
    +- src
        +- main
           +- webapp                                     : web context folder
              +- css                                     : CSS folder 
              |
              +- img                                     : image folder
              |
              +- js                                      : javascript folder
              |  +- module                               : application javascript folder
              |  +- vendor                               : vendor javascript(jquery, etc) folder
              |     +- bootstrap
              |     +- jquery.js
              |     ...
              |
              +- META-INF
              |  +- context.xml                          : context descriptor file
              |  
              +- WEB-INF
              |  +- config
              |  |  +- spring
              |  |  |  +- dispatcher-servlet.xml         : spring dispatcher servlet configuration file
              |  |  +- tiles
              |  |     +- tiles-default.xml              : apache tiles configuration file
              |  +- jsp                                  : JSP folder
              |  +- tiles
              |  |  +- include                           : JSP include folder
              |  |  +- layout                            : tiles layout JSP folder
              |  +- web.xml                              : web application deployment discriptor file
              |
              +- index.html
```

#### 6.2.2. javascript

* 상세 디렉터리 구조는 아래와 같습니다.
    ```
           +- js                 : Javascript Root
              +- vendor          : vendor js library Root
              |  +- jquery
              |  +- bootstrap
              |  ...
              +- module          : application js Root
                 +- main         
                 ...
    ```
    - vendor 폴더 아래로는 JQuery 등의 밴더 라이브러리들을 배치합니다.
    - module 폴더 아래로는 솔루션 관련 js 들을 배치합니다.

* JQuery 를 사용하고 있습니다.

* module 은 ES5 기반으로 작업합니다.(고객사의 환경이 ES6를 지원하는 브라우저를 100% 쓴다고 하기전까지...)

* vendor 폴더 안에는 jayu library 가 있습니다. 자세한것은 해당 라이브러리를 참조하시기 바랍니다.

* Code Convention

    - 파일명
        * 모듈 Javascript의 경우 Camel Case 로 작명 합니다.
    - 변수명
        * 명사로 선언하며 Camel Case 로 작성합니다.
        * 전역변수로 작성하지 않도록 합니다.
    - 상수명
        * 명사로 선언하며 Upper Snake Case 로 작성합니다.
    - 객체 생성자 명
        * 생성자의 경우 Pascal Case 로 작성합니다.
    - 함수명
        * 동사로 선언하며 Camel Case 로 작성합니다.
    - 주석
        * 단일행 주석: 주요위치에 사용합니다.
        * 영역 주석: 상세 설명이 필요한 곳에 사용합니다.
        * 문서 주석: 객체나 함수 등의 문서 주석이 필요한 곳에 사용합니다.

  ```
     ex)
     // 파일명 예제 입니다.
     userMng.js 
  
     /*
      * 예제를 표시합니다.
      * 블럭주석 예시 입니다.
      */
  
     // 변수명 단일 주석입니다.
     var testData = ""; 
  
     // 상수명 단일 주석입니다.
     var DEFAULT_HEIGHT = 200;
  
     /**
      * 생성자 명 문서 주석입니다.
      */
     var Patent = function(){
       ...
     };
  
     /**
      * 생성자 명 문서 주석입니다.
      */
     function Patent(){
       ...
     }
     var patent = new Patent();
  
     /**
      * 함수명 문서 주석입니다.
      * @param parameter 설명
      * @return return 값 설명
      */
     var createGrid = function(param){
         ...
         return testData;
     };
  
     /**
      * 함수명 문서 주석입니다.
      * @param parameter 설명
      * @return return 값 설명
      */
     function createGrid(){
         ...
         return testData;
     }
  ```

>
> [권고!]
>
> ES 기본 함수나 객체에 대해서는 Overriding 하지 않기를 권장합니다.

#### 6.2.3. URL 규칙

- 마지막에 '/' 를 포함하지 않습니다.
    ```
        ex)
            BAD:    /test/
  
            GOOD:   /test
    ```
- lower COBOL case 를 사용합니다.(소문자, '-'을 사용)
    - 소문자를 사용합니다.
    - '_'(underscore) 대신에 '-'(hyphen) 을 사용합니다.
      ```
          ex)
              BAD:    /test/TestMng
                      /test/testMng
                      /test/test_mng
    
              GOOD:   /test/test-mng
      ```
- 데이터를 호출할때는 json 을 기본으로 하며 '/json'을 경로 접미어를 사용합니다.
    ```
        ex)
             BAD:   /test/test-data.json
  
             GOOD:  /test/test-data/json
    ```
- 페이지를 호출할때는 경로 접미어를 사용하지 않으나 필요에 따라 페이지 접미어('.tiles', '.popup', '.modal', '.particle')를 사용합니다.
    ```
        ex)
            BAD:    /test/test-case-popup
                    /test/test-case/popup
  
            GOOD:   /test/test-case.popup
                    /test/test-case.tiles
    ```

>
> [주의!!]
>
> **ajax url 과 view url 차이**
>
> - URL Path 는 웹에서 호출되는 주소양식을 따릅니다.
>
> - ajax로 처리되는 url: '~/json' 의 경우 '/json' 앞의 경로가 resource 가 되는데 내부적으로 resource 는 Pascal Case 로 변형되어 처리됩니다.
>
> - view url: ajax url 을 제외한 나머지 url 의 경우 접미어를 제외하고 내부적으로 Camel Case 로 변형되어 처리됩니다.


#### 6.2.4. jsp

* 상세 디렉터리 구조는 아래와 같습니다.
  ```
    +- WEB-INF
       +- config
       |  +- spring
       |  |  +- dispatcher-servlet.xml         : spring dispatcher servlet configuration file
       |  +- tiles
       |     +- tiles-default.xml              : apache tiles configuration file
       +- jsp                                  : JSP folder
       +- tiles
       |  +- include                           : JSP include folder
       |  +- layout                            : tiles layout JSP folder
       +- web.xml                              : web application deployment discriptor file
  ```

* Spring MVC 를 사용하고 있습니다.

* Apache Tiles 를 사용하고 있습니다.

* JSTL 을 사용하고 있습니다.

* JSP Tag Library 는 기본적으로 JSTL 을 사용하며 부분적으로 Apache Tiles Tag, Spring MVC Tag 정도를 부분적으로 사용합니다.

* WEB-INF/config 에는 Spring Dispatcher Servlet 설정 과 Apache Tiles 설정이 위치하고 있습니다.

* WEB-INF/tiles 아래에는 Apache Tiles 등에서 사용하는 include 와 layout 관련 JSP 를 작성합니다.

* 모듈 JSP 는 WEB-INF/jsp 아래에 작성합니다.

* JSP(Java Server Page) Conde Convention

    - JSP 내에서 코딩은 최소화 합니다.
    - 변수명
        * 명사로 선언하며 Camel Case 로 작성합니다.
        * 전역변수로 작성하지 않도록 합니다.
    - 상수명
        * 명사로 선언하며 Upper Snake Case 로 작성합니다.
    - 객체 생성자 명
        * 생성자의 경우 Pascal Case 로 작성합니다.
    - 함수명
        * 동사로 선언하며 Camel Case 로 작성합니다.
    - 주석
        * 단일행 주석: 주요위치에 사용합니다.
        * 영역 주석: 상세 설명이 필요한 곳에 사용합니다.
        * 문서 주석: 객체나 함수 등의 문서 주석이 필요한 곳에 사용합니다.
    - HTML 코딩중 id에 대한 내용은 다음을 따릅니다.
      ```
      ex) 특허>출원리스트의 [저장] 버튼일 경우
      
      PatentApplySaveBtn1
      {    1    }{  2  }{3}
  
      {1}PatentApply [업무단]
          업무에 마추어 작명(지재권,법무,AMS에 마추어 진행)
  
      {2}SaveBtn [액션단]
          * 접두 예제
              - Save
              - Create
              - Add
              - Delete
              - Search
              - Export
              - Download
              - Upload
              - Move
          *접미 예제
              - Btn (Button)
              - Rdo (Radio)
              - Chk (CheckBox)....
      {3}1 [순번]
          같은 액션이 여럿일 경우 증가;        
      ``` 
>
> [권고!]
>
> 사용자 정의 Tag Library 의 사용을 자제하길 권합니다.
>
> 사용자 정의 Tag, 사용자 정의 어노테이션은 좋은기능이지만 새로운 지옥을 만들기도 합니다.


### 6.3. Back-End

#### 6.3.1. java 디렉터리

* src/main/java 디렉터리

```
    +- src
        +- main
           +- java                                       : java source folder
              +- com
              |  +- vertexid                             : VertexID product folder(package)
              |     +- commons                           : common utility package
              |     +- mybatis                           : MyBatis extention package
              |     +- spring                            : spring extention package
              |     +- viself                            : viself package (F/W core)
              |     +- lms                               : Paragon ELMS package
              +- vertexid
                 +- ski
                    +- lms                               : Customer ELMS package
```

* Code Convention

    - 기본적으로 Java Conde Convention 규칙에 의거 작성합니다.
    - 패키지명
        * 명사 소문자를 원칙으로 하며 최대한 1단어로 작성합니다.
        * 단어가 2개 이상일 경우 소문자 스네이크 캐이스로 작성합니다.
        * 3rd party에서 제공 혹은 github 등에서 다운받은 것을 제외하고 com.vertexid 밑으로 추가하는 것을 원칙으로 합니다.
        * 해당고객사 커스터 마이징 패키지는 **vertexid.고객사약어.솔루션약어** 형태로 작성합니다.
    - 클래스명
        * Pascal case 명사로 작성합니다.
    - 변수명
        * Camel case 명사로 작성합니다.
        * 기본형 데이터타입일 경우 맹목적인 헝가리언 표기법 보다는 접두어 접미어를 적절히 사용하여 표시하길 권장합니다.
        * 단 Boolean의 경우 접두어 'is', 'can' 등의 동사 형이 아닌 'YN', 'Flag' 의 접미어를 사용하여 메서드와 구분이 되도록 권장합니다.
    - 상수명
        * Upper Snake case 명사
    - 메서드명
        * Camel case 동사

    - 기타
        * 공통 접두어: 업무에 맞추어 작명(지재권,법무,AMS 등 각 솔루션에 맞추어 진행)
        * controller : 접미 - Ctrl.java
        * service : 접미 - Svc.java
        * dto : 접미 - DTO.java
        * vo : 접미 - VO.java
    - 주소
        * 주소는 host 의 경우 하이픈을 활용한 cobol case 만 가능합니다.(RFC-1738)
        ```
           RFC 1738. hostname : alphanumerical character and possibly also containing "-" characters
      
           http://test-site.com (o)
           http://test_site.com (x)
      
        ```
        * 나머지 url 은 came case 혹은 cobol case 만 지원합니다.
        ```
            ex) URL:
                 /viself/code/codeMng/dtlCodeList/json (x) : VISF 3.0 지원하지 않음
                 /viself/code/codeMng/dtlCodeList/json (o) : VISF 3.0 권장
                 /viself/code/code-mng/dtl-code-list/json (o) : REST 일경우 권장되고 있으나 사용하지 않음
        ```


##### 6.3.1.1. CmmTilesViewCtrl

* 요청을 통한 View 페이지 접근에 대해서 기본적으로 CmmTilesViewCtrl 을 이용할 수 있습니다.

* 요청된 주소는 BaseViewVO 에 의해 case 가 자동 변환되어 처리 됩니다.

* Request 주소의 경우 /{system}/{app}/{module}/{sub}/{page:.+} 형태를 가지며 page 의 postfix 에 따라서 보여지는 것이 달라집니다.

* system, app, module, sub 의 구성은 jsp 디렉터리 구성을 따르며 page 는 jsp 명이 됩니다.

* system ~ sub의 경우 옵션이며 최소 구성의 경우 /{page:.+} 형태입니다.

* page. 뒤의 postfix(.popup, .include 등) 에 따라서 보여지는 것이 달라집니다.

    ```
        ex) URL: /viself/code/code-mng
          Tiles 호출: /viself/code/codeMng.tiles
          실제 JSP 위치: /WEB-INF/jsp/viself/code/codeMng.jsp
          관련 JS 위치: /js/module/viself/code/codeMng.js
    ```

##### 6.3.1.2. CmmJsonViewCtrl

* Ajax 를 통해서 JSON type data 를 조회할 경우 기본적으로 단건 혹은 리스트에 대해서 기본적으로 CmmJsonViewCtrl 을 이용할 수 있습니다.

* 요청된 주소는 BaseModelVO 에 의해 case 가 자동 변환되어 처리 됩니다.

* Request 주소의 경우 /{system}/{app}/{module}/{sub}/{resource}/{action}/json 형태를 가지며 action 에 따라 조회 유형이 변경됩니다.

* system, app, module, sub, resource 의 구성은 MyBatis 맵퍼 구성을 따르며 action 의 경우 맵퍼 id가 됩니다.

* system ~ sub 의 경우 옵션이며 최소 구성의 경우 /{resource}/{action}/json 형태입니다.

* **action** : *~Data* 또는 *data~* 의 경우 단건을 반환하며 *~List* 또는 *list~* 의 경우 리스트를 반환합니다.

    ```
        ex) URL: /viself/code/code-mng/dtl-code-list/json
          MyBatis: com.vertexid.viself.code.CodeMng.mssql.dtlCodeList
  
        ex) URL: /viself/code/codeMng/dtlCodeList/json
          MyBatis: com.vertexid.viself.code.CodeMng.mssql.dtlCodeList
  
    ```

>
> [정보]
>
> * com.vertexid, mssql 은 mapper 설정에서 자동으로 추가됨
>
> * 조회만을 사용하고 Transaction 처리에 대해서는 각각의 모듈단위로 별도로 구성해서 사용하길 권장합니다.


#### 6.3.2. resource

* src/main/resource 디렉터리

```
    +- src
        +- main
           +- resource                                   : resource folder
              +- com
              |  +- vertexid
              |     +- mybatis                           : MyBatis section root
              |     |  +- conf
              |     |  |  +- mybatis-config.xml          : MyBatis configuration file
              |     |  +- mapper
              |     |     +- viself
              |     |     |  +- mssql
              |     |     |     +- hr
              |     |     |        +- UserMng.xml        : MyBatis mapper xml file
              |     |     +- Common.xml                  : MyBatis common mapper xml file
              |     |     
              |     +- spring                            : Spring F/W section root
              |        +- conf
              |           +- applicationContext.xml      : spring configuraion CLL (Context Loader Listener)
              |           +- applicationConf.xml         : CLL configuration flagment - application
              |           +- transactionConf.xml         : CLL configuration flagment - transaction
              |           +- confJDBC.xml                : transcation flagment - JDBC info
              |           +- confJNDI.xml                : transcation flagment - JNDI info
              |           +- schedulreConf.xml           : CLL configuration flagment - schedule
              |           +- securityContext.xml         : spring securitn configuration
              |           
              +- log4j2.xml                              : log4j2 configuration
              +- properties.xml                          : application properties
```

##### 6.3.2.1. MyBatis mapper 작성

* com.vertexid.mybatis.mapper 아래에 구성합니다.

* SYS_NAME(or CUSTOMER_NAME or PRJ_NAME).DB_NAME.PACKAGE_NAME.CLASS_NAME 형태로 디렉터리를 구성합니다.
    ```
        ex) namespcae
  
            norman namespace: com.vertexid.viself.menu.MenuMng.mssql
  
            +- com
               +- vertexid
                  +- mybatis
                     +- mapper                              : core mapper root 
                        +- viself                           : core package name
                        |  +- mssql                         : dbms name
                        |  |  +- menu
                        |  |  |  +- MenuMng.xml             : mapper xml file
                        |  |  +- hr
                        |  +- oracle
                        |  +- mariadb
                        +- ams                              : system package name (솔루션)
                        |  +- mssql
                        |  +- oracle
                        +- autron                              : customer name
                           +- ams                              : solution name
                              +- mssql                         : dbms name
                                 +- inv                        : module name
                                    +- InvPlan.xml             : mapper xml file
    ```

* Common.xml
    - 공통으로 사용하는 mapper 입니다.(namespace com.vertexid.mybatis.mapper.Common)
    - resultMap 유형으로 'rMap'(paramMap) 이 등록되어 있습니다.(Callable Statement 에서 사용할 수 있습니다.)
    - 'pagingPrefix', 'pagingPostFix' 를 등록했습니다. 다른 mapper 에서 include 로 사용할 수 있습니다.
    - 'countPrefix', 'countPostFix' 를 등록했습니다. 다른 mapper 에서 include 로 사용할 수 있습니다.

* Code Convention

    - namespace 는 classpath 형태로 하되 접미어로 db 유형을 추가합니다.
      ```
          ex) com.vertexid.mybatis.mapper.viself.menu.MenuMng.mssql
      ```

    - id 는 동사로 작성합니다.
        * 조회의 경우 단건 조회는 'data', 여러건 조회는 'list' 라는 단어가 들어가도록 합니다.
        * 입력의 경우 'insert', 'save' 라는 단어가 들어가도록 합니다.
        * 수정의 경우 'update', 'save' 라는 단어가 들어가도록 합니다.
        * 삭제의 경우 'delete' 라는 단어가 들어가도록 합니다.

    - 부등호가 들어 있는경우 [CDATA[...]] 안에 넣어서 사용합니다.
  ```
      WHERE test <![CDATA[ >= ]]> 10
  ```

    - paging 시에는 common.xml의 pagingPrefix, pagingPostFix, countPrefix, countPostfix 를 활용합니다.
        * 접두, 접미용의 경우 접두는 ~Prefix, 접미는 ~Postfix 로 명명하고 공용일경우 common.xml에 등록합니다.
        * Paging 을 할 경우 리스트를 조회하는 id 는 '리소스명+ListPage' 형태로 하고 카운트를 조회하는 id의 경우 'count+리소스명+ListPage' 형태로 작성합니다.

  ```
      ex)
  
      <mapper namespace="com.vertexid.viself.auth.AuthMember.mssql">
  
          <!-- 조회 Query Body -->
          <sql id="authSelectSql">
              SELECT
                  tsam.*
                  , ROW_NUMBER() OVER(ORDER BY tsam.AUTH_MEMBER_TP_CD, tsam.AUTH_MEMBER_NM, tsam.AUTH_MEMBER_ID) AS RN
              FROM t_sys_auth_member tsam
              WHERE tsam.AUTH_CD = #{authCd}
          </sql>
  
          <!-- 리스트 Query (페이징) -->
          <select id="authListPage" parameterType="map" resultType="paramMap" fetchSize="1000">
              <!-- 페이징 prefix -->
              <include refid="com.vertexid.mybatis.mapper.Common.pagingPrefix"/>
              <!-- 조회 Query Body -->
              <include refid="com.vertexid.viself.auth.AuthMember.mssql.authSelectSql"/>
              <!-- 페이징 postfix -->
              <include refid="com.vertexid.mybatis.mapper.Common.pagingPostFix"/>
          </select>
  
          <!-- 카운트 Query -->
          <select id="countAuthListPage" parameterType="map" resultType="paramMap">
              <!-- 카운트 prefix -->
              <include refid="com.vertexid.mybatis.mapper.Common.countPrefix"/>
              <!-- 조회 Query Body -->
              <include refid="com.vertexid.viself.auth.AuthMember.mssql.authSelectSql"/>
              <!-- 카운트 postfix -->
              <include refid="com.vertexid.mybatis.mapper.Common.countPostfix"/>
          </select>
  
  ```

> [주의!!]
>
> 디렉터리는 시스템이름 등의 다음에 DB 유형이 나왔지만 namespace 는 일반 classpath 에 접미어 형태로 DB 유형이 나옵니다.
>
> 이와 같은 이유는 향후 db별, 시스템별 관리를 위해 디렉터리는 구성하지만 namespace 는 해당 mapper XML 파일의 ID 이므로 classpath 유형을 따르도록 했습니다.

> [정보]
>
> interceptor 를 활용하여 페이징과 카운터를 파라메터등을 통해서 변경처리할 수 있겠지만
>
> MyBatis 에서 기본적으로 제공하는 include 기능을 사용하도록 권장합니다.


#### 6.3.3. Logging 처리

* LogAspect(com.vertexid.viself.base.LogAspect)

    - Request 및 Controller 로깅
    - CmmFilter와 CmmRequestWrapper를 이용해서 Request 파라메터 수집
    - Spring AOP를 이용해서 Ctrl 수행시 관련 Request Parameter 및 Controller 정보 로깅

* LogInterceptor(com.vertexid.mybatis.LogInterceptor)

    - MyBatis Mapper 로깅
    - 기본 로깅보다 Query를 보기 쉽게 로깅
    - mybatis-config.xml에 plugin interceptor 설정

* GlobalExceptionHandler(com.vertexid.spring.extension.GlobalExceptionHandler)

    - Spring MVC 내부 Exception 로깅 및 처리
    - @ControllerAdvice 어노테이션 사용
    - ~/json으로 요청으로 Exception 발생시 JSON으로 Exception 정보 전달
    - 나머지는 에러 페이지 호출


#### 6.3.4. 접근제어

* 접근 제어를 위해서 Spring Security Filter 를 참고한 Simple Security Filter 를 사용하고 있습니다.
* 권한을 설정하기 위해서는 다음과 같은 과정을 거칩니다.
    1. URL 을 설정한다.
    2. 모듈을 설정하고 모듈에 URL 을 맵핑한다.
    3. 메뉴를 설정하고 메뉴에 모듈을 맵핑한다.
    4. 권한을 설정하고 권한에 메뉴와 사용자 정보를 맵핑한다.

* URL 설정
    - 시스템이 사용할 url 에 기본 속성인 IS_AUTHENTICATED_FULLY(인증된 사용자) 또는 IS_AUTHENTICATED_ANONYMOUSLY(익명 사용자) 를 설정합니다.

* 모듈 설정
    - 시스템이 사용할 모듈(id, 이름 등)을 설정하고 해당 모듈에 URL 들을 맵핑합니다.

* 메뉴 설정
    - 시스템이 사용할 메뉴(id, 이름 등)을 설정하고 해당 메뉴가 사용한 모듈을 맵핑합니다.

* 권한 설정
    - 시스템이 사용할 권한(code, 이름 등)을 설정하고 해당 권한이 접근 가능한 메뉴를 설정합니다.
    - 권한에 속할 사용자 정보를 맵핑합니다.(역으로 사용자 정보에 해당 사용자가 사용할 권한 코드를 맵핑합니다.)


### 6.4. 기타

#### 6.4.1. 주의

* URL, 경로 등이 유사하면서 조금씩 다르기 때문에 헷갈릴 수 있습니다.

1. Service URL(~/json)

    - REST를 염두하고 구성되었으며 URL 규칙을 따릅니다.
    - 소문자로 작성하며 단어와 단어 구분은 '-' (hyphen)을 사용합니다.

2. 웹 디렉터리 경로

    - URL 경로와 동일한 방식으로 작성합니다.
    - js 및 jsp 파일 명칭은 camel case 로 작성합니다.
    - js 및 jsp 파일 명칭은 명사를 권고하지만 동사를 포함할 경우 진행형이나 과거형이 아닌 현재형으로 작성합니다.

3. Java 팩키지 경로

    - 소문자로 명사형으로 작성합니다.
    - 되도록 1단어로 작성하고 1단어 이상일 경우 구분은 '_'(underscore)를 사용합니다.
    - java 파일 명칭은 Pascal case 로 작성합니다.
    - 명사를 권고하지만 동사를 포함할 경우 진행형이나 과거형이 아닌 현재형으로 작성합니다.

4. SQL mapper

    - namespace 의 경우 Java 팩키지 경로와 유사하나 리소스 다음에는 .DBMS 유형이 접미어 형태로 사용됩니다.
    - mapper 의 위치는 mapper 루트와 vendor mapper 루트 로 구분되며 모듈 상위 폴더명 이후 DBMS 유형 폴더가 오고 다음에 실제 모듈 폴더가 오도록 합니다.
    - mapper 파일명명 규칙은 java 파일명 규칙을 따라가도록 합니다.
