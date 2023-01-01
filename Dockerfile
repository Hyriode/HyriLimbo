# Build Application Jar
FROM gradle:jdk16 AS build

WORKDIR /usr/app/

# Copy HyriLimbo project files
COPY . .

# Get username and token used in build.gradle
ARG USERNAME
ARG TOKEN
ENV USERNAME=$USERNAME TOKEN=$TOKEN

RUN gradle shadowJar

# Run Application
FROM openjdk:18.0.1.1-jdk

# Set working directory
WORKDIR /usr/app/

# Get all environments variables
ENV MEMORY="1G"

# Copy previous builded Jar
COPY --from=build /usr/app/build/libs/HyriLimbo-all.jar /usr/app/HyriLimbo.jar

# Start application
ENTRYPOINT java -Xmx${MEMORY} -jar HyriLimbo.jar