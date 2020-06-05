Bexs

***** Java 11 ***** 

Para rodar o programa basta entrar no diretorio que está o projeto 
cd bexs/target
bexs/targert $ java -jar bexs-ap1.0.0-SNAPSHOT.jar "path/file.csv"


No próprio console aparecerá a mensagem "please enter the route:"
Basta colocar a rota no formato NNN-NNN e aparecerá o resultado
GRU - BRC > $10

Para maior comodidade eu configurei um swagger com openapi, com intuito de aglizar os testes

http://localhost:8075/bexs/swagger-ui/index.html?configUrl=/bexs/v3/api-docs/swagger-config

Um endpoint GET para consulta com parâmetro:
NNN-NNN

Outro endpoint POST para adicionar nova rota ao arquivo

Como era premícia 
* Evite o uso de frameworks ou bibliotecas externas à linguagem. Utilize apenas o que for necessário para a exposição do serviço

Não há problema algum em não utilizar o swagger basta 

curl -X GET "http://localhost:8075/bexs/calc/route?origDest=gru-mia" -H "accept: */*"

curl -X POST "http://localhost:8075/bexs/calc/route" -H "accept: */*" -H "Content-Type: application/json" -d "{\"rotaOrigem\":\"GRU\",\"rotaDestino\":\"SAO\",\"cost\":10}"

Acabei deixando o lombok configurado no projeto apenas para log , de inicio usei uma api para ler csv, porem acabei retirando 
com medo de infrigir alguma regra

Nesta aplicação temos os seguintes pacotes

 - controller: camada de endpoints das apis Rest
 - service: camada de serviço responsavel por orquestrar as classes de processo  
 - process: camada responsavel por processos independentes que executam as regras de negócio
 
 Tanto a interface quanto os endpoints do sistema, se utilizam da camada service para execução de suas regras de negócio e somente 
  a service tem acesso as camadas process, caso quisermos trocar a leitura do arquivo, basta retirar a chamada do process responsável da camada
  de services e todas as outras camadas continuarão funcionando normalmente 
  
  
  
   