FROM openjdk:8-jdk-alpine
MAINTAINER Yury Tveritin
#устанавливаем в образе БД
#RUN apk add mysql
#монтируем папку базового хоста /tmp к образу (т.е. в образе будет доступна папка хоста)
#VOLUME /tmp
#объявляем переменную
#ARG DEPENDENCY=target/dependency

#копируем из папки контекста сборки (где лежит файл Dockerfile) файл в будующий образ в папку /app
COPY target/springbootactiviti-1.0.jar  /app/
#переходим в рабочую дирректорию в образе
WORKDIR /app/
#запускаем исполняемые файл в образе


ENTRYPOINT ["java","-jar","springbootactiviti-1.0.jar"]