# knu-shuttle-bus-server
Provides real-time shuttle bus information for Kyungpook National University. It allows users to access bus routes, schedules, and track bus locations in real-time, making the commuting experience more convenient.

## Docker를 이용한 로컬 환경 구성  
  해당 프로젝트의 개발 환경의 일관성과 배포를 용이하게 해주기 위해 **Docker** 를 사용한다.  
  1. Spring Boot 서버 이미지 만들기 **(생략 가능)**

     **Gradle Clean 후 Gradle BootJar 빌드를 통해 프로젝트 Jar 파일 생성 후 진행**  
      ```  
      docker build -t  knu_shuttle_bus_server . 
      ```
      ⚠️ 프로젝트의 docker-compose.yml 파일 수정(현재 DockerHub에 배포된 이미지(fight0105/v2psmombie-image) 사용 중)      
  3.  Docker-Compose를 통한 Spring Boot 서버 및 Mysql 컨테이너화
      ```
      docker-compose up -d
      ```
  4. 실행
     ```
     docker-compose start
     ```  
      * *컨테이너의 실행 종료*
        ```
        docker-compose stop
        ```
      * *생성한 컨테이너 삭제 및 초기화*
        ```
        docker-compose down
        ```
        + mysql 폴더 내의 db 폴더 내부를 삭제해야 완전 초기화 가능(⚠️ 숨김 파일 포함 전체 삭제 요망 &rarr; db 폴더 삭제 후 재생성 추천 )
          
## MQTT Broker Server
    # Mosquitto-broker setup
    docker run -it -d --name mosquitto-broker -p 1883:1883 -p 9001:9001 \
    -v <path>/mosquitto.conf:/mosquitto/config/mosquitto.conf \
    -v <path>/data:/mosquitto/data \
    -v <path>/log:/mosquitto/log \
    eclipse-mosquitto

+ mosquitto.conf\
      + allow_anonymous true\
      + listener 9001\
      + protocol websockets
    
    
      
  
