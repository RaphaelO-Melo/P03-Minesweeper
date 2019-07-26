/*
 Programa desenvolvido por Raphael Oliveira Melo
        Tecnologia em jogos digitais II
  Centro Universitário SENAC - Santo Amaro 2018
*/
package campo_minado;


//Realizando a importação das bibliotecas que serão utilizadas no projeto:
import java.util.Scanner;
import java.util.Random;

//Main CLASS, onde a função main sera chamada:
public class CAMPO_MINADO {
    public static void main(String[] args) {
        game();
     }
        
    
     /*
      game(): Essa função irá executar a lógica deste jogo e chamar todos as
     funções descritas a seguir e controlar todo o game, como por exemplo a 
     quantidade de minas no jogo e definir o tamanho do tabuleiro. A cada
     jogada, o tabuleiro campo minado deverá ser exibido na tela, lembre-se que
     só poderá ser exibido os quadradinhos que já foram descobertos (visão do 
     jogador). No final do jogo, a função deve mostrar se jogador ganhou ou 
     perdeu e mostrar o tabuleiro do campo minado atualizado.
     */
    
    
     public static void game() {
        //Inicializando Scanner e Variáveis que serão utilizadas na função:
        Scanner ler = new Scanner(System.in);
        int Linha, Coluna; 
        int Loop=-1, NumCoord=0, NumBomb=0, AreaBomb=0, Acao=0;
        int Dific=0, CheckPosi=0, Result=0, ContAcao=0, BkpBomb=0;
        boolean Final=false;
        // Inicializando interação com o jogador:
        System.out.println("Bem vindo, escolha um modo de jogo:");
        while (Dific == 0){
         System.out.println("1 - Fácil");
         System.out.println("2 - Médio");
         System.out.println("3 - Difícil");
         Dific = ler.nextInt();
         //Conferindo se o modo selecionado é válido:
         if (Dific <= 0 || Dific > 3){
             System.out.println("Escolha um modo de jogo válido!");
             Dific=0;
            }
        }        
        //Cadeia de IF's que identificaram o modo escolhido pelo jogador:
        //Caso seja "Fácil", define as configurações para o jogo:
        if (Dific == 1){
             //Coordenadas incrementadas com dois para a criação da borda:
             NumCoord = 6;
             NumBomb  = 2;
             AreaBomb = 4;
        }
        //Caso seja "Médio", define as configurações para o jogo:
        if (Dific == 2){
             //Coordenadas incrementadas com dois para a criação da borda:
             NumCoord = 7;
             NumBomb  = 5;
             AreaBomb = 5;
        }
        //Caso seja "Difícil", define as configurações para o jogo:
        if (Dific == 3){
             //Coordenadas incrementadas com dois para a criação da borda:
             NumCoord = 8;
             NumBomb  = 10;
             AreaBomb = 6;
        }   
        
        BkpBomb=NumBomb;
        /*Inicializando Matriz do sistema onde serão posicionas as bombas de 
        acordo com as configurações estabelecidas pelo modo escolhido pelo 
        player:
        */
        int Campo[][] = initialize(NumCoord,NumBomb,AreaBomb);
        /*Inicializando Matriz do Player que será
        utilizada pelo mesmo apenas para visualização e para definir suas
        jogadas, nela só é revelada as casas escolhidas pelo player que podem
        ser minas (caso perca) e números que dão dicas da quantidade de minas
        ao redor da respectiva casa:
        */
        char CampoPl[][]= initializePL(NumCoord);
        /*Após inicializar a Matriz do Sistema, entra neste loop que
        envia cada uma das coordenadas à função bomb, essa função é a 
        responsável por andar pelas casas do campo e checar quantas minas tem ao
        redor para assim, poder inserir o número que ira fornecer ao player 
        quantas bombas existem ao redor daquela casa:
        */
        for (int X=1; X<Campo.length-1; X++){
            for( int Y=1;Y<Campo[0].length-1;Y++){
                int Z = Campo [X][Y];
                bomb (Z,Campo);     
            }
        }
        /*Após a Matriz do Sistema estar definida, é chamada  a função print que
        imprime a Matriz do Player, que será por onde o player iirá conferir o
        estado do seu jogo:
        */
        print(Campo, CampoPl);
        System.out.println();
        //Aqui o Loop principal do jogo é inicializado:
        while (Loop == -1){
        //Revelando ao jogador a quantidade de minas no campo:
        System.out.println("Quantidade de minas = "+NumBomb);
        //Perguntando ao jogador qual a ação desejada:
        System.out.println("Escolha uma ação:");
        ContAcao=0;
        while (ContAcao==0){
         System.out.println();
         System.out.println("1 - Revelar posição;");
         System.out.println("2 - Marcar posição;");
         System.out.println("3 - Desmarcar posição;");
         Acao = ler.nextInt();
         /*Conferindo se a ação selecionada é válida, caso não seja, recomeça
         o loop e solicita uma nova coordenada
         */
         if (Acao <= 0 || Acao > 3){
             System.out.println("Escolha uma ação válida!");
             ContAcao=0;
            }
         else
             ContAcao=1;
        }      
        /*Após a escolha da ação, é perguntado ao jogador onde ele quer realizar
        a mesma:
        */
        System.out.println("Escolha uma posição:");
        CheckPosi=0;
        while (CheckPosi == 0){
        System.out.println("Linha:");
        //Lendo a linha e incrementando por causa da borda do tabulleiro:
        Linha=ler.nextInt() +1;
        System.out.println("Coluna:");
        //Lendo a coluna e incrementando por causa da borda do tabulleiro:
        Coluna=ler.nextInt()+1;
        //Conferindo se a posição é válida:
         if(Linha>NumCoord-2||Linha<0 || Coluna>NumCoord-2||Coluna<0){
             System.out.println("Selecione uma coordenada válida!");
             CheckPosi=0;                
            }
         //Se for válida, prossegue com o processamento:
         else{
             //Valida a ação para poder sair do loop posteriormente:
             CheckPosi=1;
             /*Envia as coordenadas do campo, do campo do player a coordenada
             escolhida pelo jogador e a ação desejada para a função step que ira
             retornar um valor inteiro dizendo se o jogador ganhou ou perdeu:
             */
             Result = step(Campo,CampoPl,Linha,Coluna,Acao);
             if (Result ==0){
                System.out.println("Você perdeu!");
                Loop=1;
             }
             /*Se o retorno for 1, significa que o jogador marcou uma posição,
             assim sendo, a quantidade de minas é decrementada:             
             */
             if (Result ==1){
                NumBomb--;
             }
             /*Se for uma marcação que o jogador resolveu revelar, a quantidade
             de minas é incrementada (Caso não seja uma bomba):
             */
             if (Result ==2){
                NumBomb++;
             }
             //Limite para o contar de bombas:          
             
             if (NumBomb<0){
                NumBomb=0;
             }
             //Limite para o contador de bombas:
             if (NumBomb>BkpBomb){
                NumBomb=BkpBomb; 
             }

            }
        }
        /*Chama a função status para conferir se o jogador abriu todas as casas
        que não são bombas, caso tenha tido sucesso, o loop é interrompido e o
        o jogador notificado de sua vitória:        
        */
         Final = status(CampoPl,BkpBomb,NumCoord);
         if (Final == true){
             System.out.println("Parabéns, você venceu!");
             Loop=1;
         }
        }
     }

     
     /*
      initialize(): Como vamos usar uma matriz NxM para representar o 
     tabuleiro do Campo Minado, a função initialize() deverá devolver uma matriz
     já com as minas já espalhadas na matriz e nas outras posições onde não 
     tiver minas você deve preencher quantas minas há nos quadradinhos
     adjacentes. A definição da quantidade de minas no jogo e o tamanho do 
     tabuleiro fica a critério dos alunos, bem como, como serão espalhadas as 
     minas na matriz. Para que o jogo fique desafiador tente espalhar as minas 
     de forma aleatória na matriz.
     */
    
     
     public static int[][] initialize(int Coord, int QuantMin, int LimBomb){
        //Inicializando uma matriz de acordo com o modo selecionado:
        int CampSis[][]= new int[Coord][Coord];
        //Inicializando Random e Variáveis que serão utilizadas na função:
        Random aleatorio = new Random();
        int num1, num2;
        //Loop onde a matriz do sistema será preenchida com 0:
        for( int i=0;i<CampSis.length;i++)        
           for( int j=0;j<CampSis[0].length;j++) 
               CampSis[i][j]= 0;
        /*Loop onde as bombas serão posicionadas dentro da matriz, aqui é 
        definido que elas devem estar dentro da matriz visível pelo player, ou
        seja, não podem estar nas bordas, e também é definido que as bombas não 
        podem estar na mesma coordenada, o númeo de bombas também é definido 
        pelo modo de jogo escolhido:
        */
        for (int ContBomb = 0; ContBomb != QuantMin; ContBomb++) {
            num1 = 0;
            num2 = 0;
            while (num1 == 0 || num2 == 0 || num1 == num2) {
                num1 = aleatorio.nextInt(LimBomb);
                num2 = aleatorio.nextInt(LimBomb);
            }
                /*Caso a posição seja uma bomba, a posição é ignorada e o 
                contador de bombas é decrementado para que uma nova posição seja
                escolhia:
                */
                if (CampSis[num1][num2] == -1)
                    ContBomb--;
                // Caso a posição seja válida, a bomba é posicionada:
                if (CampSis[num1][num2] != -1)
                    CampSis[num1][num2] = -1;
        }
        /*Após todas as bombas serem posicionadas, a função retorna a Matriz
        atualizada
        */
        return CampSis;
     }
     
   
     /*
      initializePL (): Similar à função initialize, inicia a matriz que será 
     visualizada pelo usuário, é uma matriz char que futuramente será comparada
     com a matriz int do sistema.
     */
     
    
     public static char[][] initializePL(int Coord){
        /* Inicializando a Matriz de visualização do player de acordo com a 
        dificuldade escolhida pelo mesmo:
        */
        char CampPL[][]= new char[Coord][Coord];
        //Loop onde a matriz do jogador será preenchida com "-"
        for( int i=0;i<CampPL.length;i++)        
            for( int j=0;j<CampPL[0].length;j++) 
               CampPL[i][j]= '-';  
       return CampPL;
     }


      /*
      bomb (): Esta função receberá uma coordenada do campo da função game
     e realizará a contagem de bombas ao redor da respectiva coordenada:
     */

     
     public static void bomb(int cord, int Campo[][]) {
        /*Loop que irá andar pela matriz visivel, ou seja, sem percorrer a 
        borda, conferindo cada uma das posições:
        */
        for( int i=1;i<Campo.length-1;i++){
            for( int j=1;j<Campo[0].length-1;j++){
                /*Se a coordenada for diferente de uma bomba, inicia a contagem 
                das casas ao redor da coordenada que está sendo verificada:    
                */  
                if (Campo[i][j] != -1){
                //Inicia o contador de bombas]:
                int Cont = 0;    
                //Verifica a posição Noreste:                    
                if (Campo[i-1][j-1]==-1){
                    Campo[i][j] = Cont+1;
                    Cont++;
                }
                //Verifica a posição Norte:
                if (Campo[i-1][j]==-1){
                    Campo[i][j] =Cont+1;
                    Cont++;
                }
                //Verifica a posição Nordeste:
                if (Campo[i-1][j+1]==-1){
                    Campo[i][j] =Cont+1;
                    Cont++;
                }
                //Verifica a posição Oeste:
                if (Campo[i][j-1]==-1){
                    Campo[i][j] =Cont+1;
                Cont++;
                }
                //Verifica a posição Leste:
                if (Campo[i][j+1]==-1){
                    Campo[i][j] =Cont+1;
                Cont++;
                }
                //Verifica a posição Sudoeste:
                if (Campo[i+1][j-1]==-1){
                    Campo[i][j] =Cont+1;
                Cont++;
                }
                //Verifica a posição Sul:
                if (Campo[i+1][j]==-1){
                    Campo[i][j] =Cont+1;
                Cont++;
                }
                //Verifica a posição Sudeste:
                 if (Campo[i+1][j+1]==-1){
                    Campo[i][j] =Cont+1;
                Cont++;
                 }
               }
                    
            }
        }
     }
     
     
     /*
      print(): Essa função imprime o campo minado, na visão do jogador, na 
     tela em modo texto, não se esqueça que a impressão deve ser o mais 
     informativa possível, para o que jogador consiga decidir claramente qual 
     jogada irá fazer.
     */


     public static void print (int Test [][], char View[][]) {
        /*Anda nas linhas e colunas criando uma "borda []" para cada coordenada,
        imprime o número da linha e coluna correspondente do tabuleiro e mostra
        ao jogador um tabuleiro visível
        */   
        System.out.print("    ");
        for( int j=1;j<View[0].length-1;j++){ 
           System.out.print((j-1)+"  ");     
           }
           System.out.println();
           for( int i=1;i<View.length-1;i++){    
               System.out.print(i-1+"  ");
                for( int j=1;j<View[0].length-1;j++){
                   System.out.print("["+View[i][j]+"]");
                 }
                System.out.println();
            }
           
           
        /*O Print abaixo serve para mostrar o tabuleiro do sistema, caso seja de 
           seu interesse conferir, é só remove-lo do comentário que funcionará
           perfeitamente:
        
        System.out.print("Campo do sistema:");
        System.out.println("    ");
        for( int j=1;j<Test[0].length-1;j++){ 
           System.out.print((j-1)+"  ");     
           }
           System.out.println();
           for( int i=1;i<Test.length-1;i++){    
               System.out.print(i-1+"  ");
                for( int j=1;j<Test[0].length-1;j++){
                   System.out.print("["+Test[i][j]+"]");
                 }
                System.out.println();
            }
        */
     }
     
     
     /*
      step(): Essa função recebe o campo minado, uma posição e uma ação a ser
     feita no campo minado (revelar ou marcar a posição). Caso tenha sido 
     solicitado a ação revelar a posição e o jogador tenha sucesso é retornado 
     -1 (o jogo pode continuar), caso tenha uma mina na posição solicitada
     para revelar é retornado 0 (jogador perdeu), caso seja solicitado marcar a 
     posição, é retornado 1 (jogo continua e quantidade de minas é decrementada)
     . Ao final a matriz atualizada é retornada. Caso julgue necessário pode 
     passar outros parâmetros para função.
     */
     
     
     public static int step(int M[][], char MP[][], int lin, int col,int acao) {
        /*Caso a corrdenada recebida seja uma bomba, envia o tabuleiro para a
        função vision que transformará o tabuleiro em algo visível ao jogador 
        com todas as posições sendo reveladas e retorna 0:
        */
        if (M[lin][col] == -1 && acao != 2 && M[lin][col] == -1 && acao != 3){
            vision(-2,M,MP);
            print(M,MP);
            return 0;
        }
        /*Caso ação seja revelar uma posição, primeiro a função confere se é 
        uma posição válida, caso não seja, notifica o jogador:
        */
        if (acao==1){
            if (MP[lin][col] != '-' && MP[lin][col] != '!'){
                System.out.println("Lugar já selecionado, escolha novamente");
                print(M,MP);
                return -2;        
        }   
        /*Caso não seja uma bomba, mas seja uma marcação, revela a posição para 
        o jogador, retorna 2 (para incrrementar o número de minas e em seguida
        o jogo continua. Para revelar a posição, envia a coordenada à função 
        vision, que converte a matriz int para uma matriz char:
        */
        if (MP[lin][col]=='!'){
        char resultado = vision(M[lin][col], M,MP);
        MP[lin][col] = resultado;
        print(M,MP);
        return 2;
        /*Caso não seja uma bomba, revela a posição para o jogador, retorna -1 e
        o jogo continua. Para revelar a posição, envia a coordenada à função 
        visi/on, que converte a matriz int para uma matriz char:
        */    
        }
        char resultado = vision(M[lin][col], M,MP);
        MP[lin][col] = resultado;
        print(M,MP);
        return -1;
        }
        /*Caso a ação seja marcar, o jogador quer marcar a posição e decrementar
        a quantidade de minas, mas caso já esteja marcada, é solicitada uma nova
        posição:
        */
        if (acao==2){
            if (MP[lin][col] == '!'){
            print(M,MP);
            System.out.println("Posição já marcada!");
            return 5;
            }
        MP[lin][col] = '!';
        print(M,MP);
        return 1;
        }
        /*Caso não seja nenhuma das duas, ele quer desmarcar uma posição e
        incrementar a quantida de linhas:
        */
        if (acao==3){
         if (MP[lin][col] != '!'){
            System.out.println("Nada para desmarcar!");
            return -1;
         }
        MP[lin][col] = '-';
        print(M,MP);
        return 2;
        }
        return -1;
     }

     
     /*
      vision (): função que converte os valores de int para char e revela ao 
     jogador a posição escolhida ou o tabuleiro por inteiro (caso ele tenha 
     perdido:
     */
     
     
     public static char vision (int ref, int V[][], char V2[][]){
        /*Caso o referencial seja 1, significa que o jogador perdeu, então se 
         inicia o loop para revelar o tabuleiro para o jogador:
        */
        if (ref == -2){
            for (int X=1; X<V.length-1; X++){
             for( int Y=1;Y<V[0].length-1;Y++){
                if (V[X][Y] == 0)
                    V2[X][Y] = '0';
                if (V[X][Y] == 1)
                    V2[X][Y] = '1';
                if (V[X][Y] == 2)
                    V2[X][Y] = '2';
                if (V[X][Y] == 3)
                    V2[X][Y] = '3';
                if (V[X][Y] == 4)
                    V2[X][Y] = '4';
                if (V[X][Y] == 5)
                    V2[X][Y] = '5';
                if (V[X][Y] == 6)
                    V2[X][Y] = '6';
                if (V[X][Y] == 7)
                    V2[X][Y] = '7';
                if (V[X][Y] == 8)
                    V2[X][Y] = '8';
                if (V[X][Y] == -1)
                    V2[X][Y] = '*';                   
                }             
            }
        }
        //Caso ref seja != de 0, só é revelada parte do tabuleiro:        
        if (ref == 1)
           return '1';
        if (ref == 2)
           return '2';
        if (ref == 3)
           return '3';
        if (ref == 4)
           return '4';
        if (ref == 5)
           return '5';
        if (ref == 6)
           return '6';
        if (ref == 7)
           return '7';
        if (ref == 8)
           return '8';
        return '0';
     }
     /*
      status (): E por fim, a função status() verifica se o jogador já 
     encontrou todas as minas corretamente, ou seja, o jogador abriu todos os 
     quadradinhos que não têm minas. Caso o jogador tenha cumprido o objetivo do
     jogo a função retorna true, ou false caso contrário
     */
     
     
     public static boolean status(char Camp[][], int ContBomb, int Coord) { 
        /*Loop o que ira percorrer no campo contabilizando os locais já abetos
         pelo jogador, caso só restem as bombas ou marcações, significa que o 
         jogador venceu:         
         */ 
        int ContSucess=0;
        for( int i=0;i<Camp.length;i++)        
            for( int j=0;j<Camp[0].length;j++)
                if (Camp[i][j]!='-'){
                     if (Camp[i][j]=='!')
                        ContSucess--;
                ContSucess++;
                }
        /*
        Contabiliza ass casas disponíveis de acordo com o modo de jogo que foi
        selecionado pelo jogador:
        */
        if (Coord==6){
            if (ContSucess == 14){
                return true;
            }
        }
        /*
        Contabiliza ass casas disponíveis de acordo com o modo de jogo que foi
        selecionado pelo jogador:
        */
        if (Coord==7){
            if (ContSucess == 20){
                return true;
            }
        }
        /*
        Contabiliza ass casas disponíveis de acordo com o modo de jogo que foi
        selecionado pelo jogador:
        */
        if (Coord==8){
            if (ContSucess == 26){
                return true;
            }
        }
        return false;

        }
     }
