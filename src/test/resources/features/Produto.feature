Feature: Gerenciamento de produtos

  Scenario: Listar todos os produtos
    Given que existem produtos no sistema
    When eu solicitar a listagem de todos os produtos
    Then eu devo receber uma lista contendo todos os produtos

  Scenario: Cadastrar um novo produto
    Given que eu tenho os dados de um novo produto
    When eu solicitar o cadastro do produto
    Then o produto deve ser cadastrado com sucesso

  Scenario: Atualizar um produto existente
    Given que um produto existe no sistema
    When eu solicitar a atualização do produto
    Then o produto deve ser atualizado com sucesso

  Scenario: Deletar um produto existente
    Given que um produto existe no sistema
    When eu solicitar a deleção do produto
    Then o produto deve ser deletado com sucesso
