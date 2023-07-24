usuario=$USER
echo
echo "BUILD LUMAQ: DEV by $usuario";
read -p "CONFIRMA? (S/N): " escolha ;

case "$escolha" in
s | S | sim) {

  echo "EXECUTANDO MVN CLEAN INSTALL -U"
  echo
  mvn clean install -U
  STATUS=$?
  if [ $STATUS -eq 0 ]; then
    echo
    echo "PROJETO COMPILADO E ATUALIZADO"
    echo "EXECUTANDO SONAR"
    mvn sonar:sonar -Dsonar.projectKey=conta paciente -Dsonar.host.url=https://sonarqube.preventsenior.com.br -Dsonar.login=865f1ccafa94ebf20f50d1546c5b3193f58fa799
    echo
    echo "BUILD IMAGEM DOCKER" ;
    docker build -t registry.preventsenior.com.br/conta-paciente:dev .
    echo
    echo "ENVIANDO IMAGEM PARA O REGISTRY" ;
    docker push registry.preventsenior.com.br/conta-paciente:dev
    echo
    echo "JENKINS: https://ci.preventsenior.com.br/view/DEV%20-%20K8S%20-%20DEPLOY/job/DEV%20-%20K8S%20-%20DEPLOY%20-%20Conta%20Paciente/build?delay=0sec"
    echo "NAME: conta-paciente"
    echo "TAG: dev"
    echo "SUCESSO NO BUILD"
  else
    echo "ERRO NO MVN CLEAN INSTALL -U"
  fi
} ;;
*) echo "SCRIPT CANCELADO" ;
esac
