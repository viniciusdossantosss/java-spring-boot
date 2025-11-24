#!/bin/bash
# Instalar Java e Maven
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 17.0.2-tem
sdk install maven
# Compilar o projeto
mvn clean package -DskipTests
