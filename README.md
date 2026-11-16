# Sistema Automotivo – Gestão de Estoque de Veículos

CRUD orientado a objetos para gerenciar o estoque de uma concessionária.
Projeto da disciplina de Programação Orientada a Objetos (UniFECAF).

**Tecnologias:** Java 17, Spring Boot 3, Spring Data JPA, MySQL, HTML/JavaScript simples.

## Estrutura

```
estoque-veiculos/
├── pom.xml
├── script.sql                  # modelo físico + dados de exemplo (MySQL)
└── src/main/
    ├── java/com/unifecaf/estoque/
    │   ├── EstoqueApplication.java
    │   ├── model/              # Marca, Modelo, Veiculo, StatusVeiculo
    │   ├── repository/         # interfaces Spring Data JPA
    │   ├── service/            # regras de negócio (VeiculoService)
    │   └── controller/         # API REST
    └── resources/
        ├── application.properties
        └── static/index.html   # front-end simples
```

## Como executar

1. Instale Java 17+, Maven e MySQL.
2. Edite `src/main/resources/application.properties` com seu usuário e senha do MySQL.
3. (Opcional) execute `script.sql` no MySQL para criar tabelas e dados de exemplo.
   Sem isso, o Hibernate cria as tabelas automaticamente.
4. Rode:
   ```
   mvn spring-boot:run
   ```
5. Abra http://localhost:8080 para usar a interface.

## Endpoints da API

| Método | Rota                  | Descrição                                   |
|--------|-----------------------|---------------------------------------------|
| GET    | /api/veiculos         | Lista com filtros opcionais                 |
| GET    | /api/veiculos/{id}    | Busca um veículo                            |
| POST   | /api/veiculos         | Cadastra veículo                            |
| PUT    | /api/veiculos/{id}    | Atualiza veículo                            |
| DELETE | /api/veiculos/{id}    | Remove veículo                              |
| GET/POST/PUT/DELETE | /api/marcas, /api/modelos | CRUD de marcas e modelos |

Filtros de `GET /api/veiculos`: `marcaId`, `modeloId`, `ano`, `precoMin`, `precoMax`, `status`
(`DISPONIVEL`, `RESERVADO`, `VENDIDO`).

Exemplo de corpo para `POST /api/veiculos`:

```json
{
  "modelo": { "id": 1 },
  "ano": 2021,
  "cor": "Prata",
  "preco": 115000.00,
  "quilometragem": 32000,
  "status": "DISPONIVEL"
}
```
