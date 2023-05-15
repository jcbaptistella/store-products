# Nome da Aplicação

    - store-products

# Descrição
    - Api de gerenciar produtos

# Funcionalidades
    - Endpoint para listar todos os produtos.
    - Endpoint para ver somente um produto por id.
    - Endpoint para inserir um novo produto.
    - Endpoint para atualizar um produto.
    - Endpoint para deletar um produto.
    - Endpoint para incrementar ou decrementar a quantidade de stock do produto.

# Pré-requisitos

    - JDK 11
    - Maven
    - Mysql
    
# Uso via docker
    1 - Executar o comando: docker-compose up -d
    2 - Se quiser verificar o banco de dados via web, acessar: http://localhost:8081/?server=db&username=root&db=cayena
    3 - Usario: root / Senha: mysql
    4 - Para facilitar o uso da aplicação, deixamos uma documentação do postman no diretório raiz da aplicação. Basta importar a coleção no postman e utiliza-la.

# Uso subindo aplicação
    1 - Executar o comando: mvn clean install
    2 - Executar o comando: java -jar .\target\store-products-0.0.1-SNAPSHOT.jar
    3 - Acessar o banco de dados e adicionar os suppliers. Na raiz do projeto tem um documento chamdo init.sql que o script está lá.
    3 - Para facilitar o uso da aplicação, deixamos uma documentação do postman no diretório raiz da aplicação. Basta importar a coleção no postman e utiliza-la.

# Autor
    - Julio Baptistella
