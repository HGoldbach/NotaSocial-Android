# NotaSocial-Android

Este projeto é o Frontend mobile do sistema NotaSocial, desenvolvido para a disciplina de Trabalho de Conclusão do Curso(TCC). 
Este readme descreve como configurar e executar o sistema.

# Pré-requisitos

Certifique-se de ter instalado:
Android Studio

Execute os passos do repositório de backend da aplicação:
<https://github.com/juanfernandes-rrm/customer-bff/tree/main>

# Configuração

Para que você possa executar o aplicativo será necessário ter um dispositivo movel, visto que a leitura do qrcode será feito por ele.
É necessário habilitar o USB-TETHERING do dispositivo, link com tutorial: <https://www.minhaconexao.com.br/blog/software/tethering-usb>

Como o aplicativo acessará o localhost da aplicação backend, será necessário obter o ip para que o dispositivo móvel conecte, para isso siga os seguintes passos:

1. Na máquina que está executando o backend, abra um terminal e digite ipconfig(Windows) ou ifconfig(Linux) e copie o primeiro endereço IPv4.
2. Na pasta app/src/main/res/xml abra o arquivo network_security_config.xml e altere o ip do arquivo pelo seu.
3. Na pasta app/src/main/java/data abra o arquivo AppContainer.kt altere o ip da baseUrl para utilizar o seu ip (altere apenas o ip, mantenha o protolo e a porta).




