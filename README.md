# ConversorMoeda

O problema consiste em fornecer um método que realiza o conversão de um montante em uma determinada moeda para outra.

Foi criado um projeto web (apenas o backend de negócio foi desenvolvido) no formato maven utilizando Spring.
O projeto está dividido nos seguintes pacotes: business, entity, persistence, presentation e util.
- Presentation
  Este pacote representa a camada de apresentação, contento controllers e demais classes relacionada a apresentação.
- Business
  Este pacote representa a camada de negócio, com classes que realizam a conversão, validações etc. Acessada pela camada de apresentação.
- Persistence
  Este pacote representa a camada de acesso a dados. Acessada pela camada de negócio.
- Entity
  Este pacote possui as entidades da aplicação, sendo acessada por todas as camadas.
- Util
  Pacote de classes utilitárias para ser utilizado por toda a aplicação.

Nesta solução encontre-se somente a classe de negócio para a conversão da moeda e os testes unitários para validação da solução. A solução  completa conteria a persistencia da infomação que é consultada no endereço externo, para facilitar posteriores consultas e possíveis indisponibilidades do serviço. E conteria também tela com formulário de consulta.
