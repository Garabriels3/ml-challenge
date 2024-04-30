# Documentação do Projeto

## Introdução

Este documento detalha a arquitetura, as tecnologias e as estratégias de teste do projeto Mini Mercado Livre, um aplicativo Android que demonstra produtos e seus detalhes e oferece um histórico de pesquisa. O projeto é modularizado, segue padrões de arquitetura limpa para promover a separação de contextos, testabilidade, trabalho com múltiplos times e escalabilidade.

## Módulos

O projeto é dividido em módulos para promover a reutilização de código, a separação de responsabilidades e a escalabilidade:

* **Network:** Gerencia a comunicação com a rede utilizando Retrofit.
* **Infrastructure:** Fornece infraestrutura de suporte, como utilitários e classes de extensão.
* **Design System:** Contém componentes de interface de usuário reutilizáveis e estilos criados com Jetpack Compose.
* **Navigation:** Gerencia a navegação entre telas usando a biblioteca de navegação do Jetpack.
* **Feature-Products:** Implementa a funcionalidade de busca e exibição de produtos.

## Arquitetura Limpa

A funcionalidade de produtos segue a Arquitetura Limpa, dividida em três camadas:

**Data**

* Responsável por recuperar e persistir dados e consumo de dados vindos de forma remota.
* **DataSource:** Responsável por interagir diretamente com interfaces do consumo de dados remotos ou locais.
* **Repository-Impl:** Implementa a lógica de acesso a dados, interagindo com DataSources locais e remotos e faz a estrategia de cacheamento se necessario.
* **DataModelResponse:** Data Class exclusiva do layer de data para mapear dados de origem local ou remota

**Domain**

* Contém a lógica de negócios do aplicativo.
* **Interface Repository:** Interface para fazer boundary com layer de data para evitar acoplamento e manter domain isolado.
* **UseCase:** Implementam casos de uso específicos, orquestrando a lógica de negócios e interagindo com os repositórios.

**Presentation**

Tornar os estados, ações e eventos mais claros com o MVI, mantendo a separação de responsabilidades com o MVVM.

**MVVM:**

* Boa separação de responsabilidades.
* Falta clareza nos estados e ações.

**MVI:**

* Complementa o MVVM trazendo clareza.
* Permite que um complete o outro.

**Funcionamento Geral Presentation:**

* **State:** Setado através de uma Action dentro da ViewModel para definir possíveis estados da tela com seus dados
* **UiModel:** Data Class usada pelos estados(Sealed Class) para conseguir compartilhar os dados durante a passagem de um estado para outro, para evitar perda de dados e estado da tela entre mudanças de estado.
* **Actions:** O usuário interage com a tela, gerando ações que são capturadas que podem carregar dados ou não..
* **SideEffects:** Disparado através de uma Action dentro da ViewModel e observada na View para navegar ou exibir alguma mensagem ou comunicação(Bottom Sheets, Dialogs, SnackBars)

## Estratégias de Teste

* **Testes de Unidade:** Foco em testar unidades de código isoladas, como ViewModels e Use Cases, usando frameworks de mocking como MockK. Verificação da ordem correta e dos dados corretos dos estados emitidos.
* **Testes de Integração:** Foco em testar a integração das camadas Data, desde o Repository até o Retrofit, usando MockWebServer para simular respostas da API.

## Benefícios da Arquitetura e das Tecnologias Escolhidas

* **Reutilização de Código:** A modularização e a abstração de camadas facilitam a reutilização de código em diferentes partes do aplicativo.
* **Separação de Responsabilidades:** Cada camada tem responsabilidades bem definidas, tornando o código mais fácil de entender, manter e testar.
* **Testabilidade Aprimorada:** A arquitetura modular e o uso de frameworks de teste facilitam a criação de testes abrangentes e confiáveis.
* **Escalabilidade:** O design modular e as tecnologias escolhidas permitem que o aplicativo seja facilmente dimensionado para atender a demandas crescentes.
* **Experiência do Usuário Moderna:** O Jetpack Compose e outras tecnologias permitem criar interfaces de usuário fluidas, responsivas e visualmente atraentes.

## Tecnologias Utilizadas

* Jetpack Compose: Para construção de interfaces de usuário modernas e declarativas.
* Kotlin Coroutines e Flow: Para gerenciar operações assíncronas e fluxos de dados.
* DataStore: Para armazenar dados simples, como histórico de pesquisa.
* Koin: Para gerenciar dependências e facilitar a testabilidade.
* Retrofit: Para comunicação com APIs REST.
* Coil: Para carregamento e exibição de imagens.
* Paging 3: Para lidar com grandes conjuntos de dados de forma eficiente.

