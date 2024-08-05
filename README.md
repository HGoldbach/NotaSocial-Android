# NotaSocial-Android

Este projeto é o frontend mobile do sistema NotaSocial, desenvolvido para a disciplina de Trabalho de Conclusão de Curso (TCC). Este readme descreve como configurar e executar o sistema.

## Pré-requisitos

Certifique-se de ter instalado:

- [Android Studio](https://developer.android.com/studio)
- Execute os passos do repositório de backend da aplicação: [customer-bff](https://github.com/juanfernandes-rrm/customer-bff/tree/main)

## Configuração

Para executar o aplicativo, será necessário:

1. **Dispositivo Móvel:**
   - Certifique-se de ter um dispositivo móvel para leitura do QR code.

2. **Habilitar USB-Tethering:**
   - Siga este tutorial para habilitar o USB-Tethering: [Tutorial USB-Tethering](https://www.minhaconexao.com.br/blog/software/tethering-usb).

3. **Obter o IP Local:**
   - Na máquina que está executando o backend, abra um terminal e digite:
     - `ipconfig` (Windows) ou `ifconfig` (Linux)
   - Copie o primeiro endereço IPv4.

4. **Configurar o IP no Projeto:**
   - Abra o arquivo `network_security_config.xml` em `app/src/main/res/xml` e altere o IP pelo seu.
   - Abra o arquivo `AppContainer.kt` em `app/src/main/java/data` e altere o IP da `baseUrl` para o seu IP (altere apenas o IP, mantenha o protocolo e a porta).

## Executando o Aplicativo

1. Conecte o dispositivo móvel ao computador via USB.
2. Habilite o USB-Tethering conforme o tutorial fornecido.
3. No Android Studio, abra o projeto NotaSocial-Android.
4. Execute o aplicativo no dispositivo conectado.