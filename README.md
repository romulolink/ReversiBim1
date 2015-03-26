# ReversiBim1

## Projeto do Intellij 

Prova Bimestral 1 da Disciplina Tecnologias para Jogos do curso de Ciência da Computação do IBTA


Código baixado em: http://trovami.altervista.org/java/reversi



### Requisitos

1. Gerar Tabuleiro PROFESSOR
1. Resetar Tabuleiro 
1. Selecionar Single/Multiplayer
1. Pegar clique 
1. Inserir Peça na posição
1. Verificar posições válidas ( Está no intervalo && Não há peças na posição && Pegou alguma peça do oponente )
1. Encontrar melhor jogada pra o computador
1. Conquistar peças do oponente
2. Computar Placar


### Recursos
1. Matriz[8][8]
2. Move()
1. Boolean enable_multiplayer
2. enum ['Nil','Black','White']
3. counter[2] -> 64 peças
4. sleep(1000)
5. Check (Move move, int incx, int incy, kind,  Boolean set ) - Percorre uma direção e retorna total de perças ganhas e as conquista se Boolean set estive habilitado
6. isValid usa Check() com set = false - Percorre direções
7. checkBoard usa Check() com set = true  - Percorre direções
8. userCanMove usa nil e isValid para verificar jogada valida
9. findMove() | findMax() | strategy()  - Recursos de IA

### Fluxo de Execução



