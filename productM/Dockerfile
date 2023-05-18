# back
# устанавливаем самую лёгкую версию JVM
FROM openjdk:17-jdk-alpine3.14

# указываем точку монтирования для внешних данных внутри контейнера (как мы помним, это Линукс)
VOLUME /tmp

# внешний порт, по которому наше приложение будет доступно извне
EXPOSE 8082

# указываем, где в нашем приложении лежит джарник
ARG JAR_FILE=target/productM-0.0.1-SNAPSHOT.jar

# добавляем джарник в образ под именем choreographerM.jar
ADD ${JAR_FILE} productM.jar

# команда запуска джарника
ENTRYPOINT ["java","-jar","/productM.jar"]