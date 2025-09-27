# Estágio 1: Build da Aplicação com Maven
# Alteramos a imagem para usar uma com JDK 21
FROM eclipse-temurin:21-jdk-jammy as builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos do Maven Wrapper para o container
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Copia o código fonte do projeto
COPY src ./src

# Dá permissão de execução para o Maven Wrapper
RUN chmod +x ./mvnw

# Executa o comando para compilar o projeto e gerar o arquivo .jar
# -DskipTests pula os testes para acelerar o build no deploy
RUN ./mvnw clean package -DskipTests

# Estágio 2: Imagem Final de Execução
# Usamos uma imagem JRE 21, que é menor e mais segura
FROM eclipse-temurin:21-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar gerado no estágio de build para a imagem final
# Certifique-se que o nome do .jar está correto
COPY --from=builder /app/target/cryptography-0.0.1-SNAPSHOT.jar ./app.jar

# Expõe a porta que o Spring Boot usa. Render usa a porta 10000.
# O Spring Boot vai detectar a variável de ambiente PORT automaticamente.
EXPOSE 10000

# Comando para iniciar a aplicação quando o container for executado
ENTRYPOINT ["java", "-jar", "app.jar"]