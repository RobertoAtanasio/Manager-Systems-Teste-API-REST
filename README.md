"# Manager-Systems-Teste-API-REST" 

A segurança da API foi implementada com com OAuth 2

O TOKEN e o Refresh TOKEN dverão ser obtidos pela chamada ao método POST Obter Access Token : http://localhost:8080/oauth/token que têm ambos a mesma assinatura, diferenciando na passagem dos parâmetros. 

Ver modelo das chamadas a partir do link https://documenter.getpostman.com/view/3275259/S1ZxbpwY do POSTMAN.

Documentação da API: http://localhost:8080/swagger-ui.html#/

Obs.: 
Por uma questão técnica ainda não identificada, ao incluir o módulo de segurança, a chamada ao módulo da documentação fica também exigido uma autorização para acesso. Que não deve ser solicitado para a chamada da documentação. Estou avaliando o problema e assim que resolvido atualizarei o github.




