import java.util.Scanner;
import java.util.Random;

public class Main {
    static int MAXIMO_TEMPO_EXECUCAO = 65535;
    static int n_processos = 3;
    public static void main(String[] args) {

        int[] tempo_execucao = new int[n_processos];
        int[] tempo_chegada = new int[n_processos];
        int[] prioridade = new int[n_processos];
        int[] tempo_espera = new int[n_processos];
        int[] tempo_restante = new int[n_processos];

        Scanner teclado = new Scanner (System.in);

        popular_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);

        imprime_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);

        while(true) {
            System.out.print("Escolha o algoritmo: \n1 = FCFS\n2 = SJF Preemptivo\n3 = SJF Não Preemptivo\n4 = Prioridade Preemptivo\n5 = Prioridade Não Preemptivo\n6 = Round_Robin\n7 = Imprime lista de processos\n8 = Popular processos novamente\n9 = Sair: ");
            int alg =  teclado.nextInt();

            switch (alg) {
                case 1: // FCFS
                    FCFS(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada);
                    break;
                case 2: // SJF PREEMPTIVO
                    SJF(true, tempo_execucao, tempo_espera, tempo_restante, tempo_chegada);
                    break;
                case 3: // SJF NÃO PREEMPTIVO
                    SJF(false, tempo_execucao, tempo_espera, tempo_restante, tempo_chegada);
                    break;
                case 4: // PRIORIDADE PREEMPTIVO
                    PRIORIDADE(true, tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
                    break;
                case 5: // PRIORIDADE NÃO PREEMPTIVO
                    PRIORIDADE(false, tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
                    break;
                case 6: // Round_Robin
                    Round_Robin(tempo_execucao, tempo_espera, tempo_restante);
                    break;
                case 7: // IMPRIME CONTEÚDO INICIAL DOS PROCESSOS
                    imprime_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
                    break;
                case 8: // REATRIBUI VALORES INICIAIS
                    popular_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
                    imprime_processos(tempo_execucao, tempo_espera, tempo_restante, tempo_chegada, prioridade);
                    break;
                case 9:
                    // Sair
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }
    }

    public static void popular_processos(int[] tempo_execucao, int[] tempo_espera, int[] tempo_restante, int[] tempo_chegada,  int [] prioridade ){
        Random random = new Random();
        Scanner teclado = new Scanner (System.in);
        int aleatorio;

        System.out.print("Como irá popular os processos? \n1 - Aleatório\n2 - Manual \nDigite a opção desejada: ");
        aleatorio =  teclado.nextInt();

        for (int i = 0; i < n_processos; i++) {
            //Popular Processos Aleatorio
            if (aleatorio == 1){
                tempo_execucao[i] = random.nextInt(10)+1;
                tempo_chegada[i] = random.nextInt(10)+1;
                prioridade[i] = random.nextInt(15)+1;
            }
            //Popular Processos Manual
            else {
                System.out.print("Digite o tempo de execução do processo["+i+"]:  ");
                tempo_execucao[i] = teclado.nextInt();
                System.out.print("Digite o tempo de chegada do processo["+i+"]:  ");
                tempo_chegada[i] = teclado.nextInt();
                System.out.print("Digite a prioridade do processo["+i+"]:  ");
                prioridade[i] = teclado.nextInt();
            }
            tempo_restante[i] = tempo_execucao[i];
        }
    }

    public static void imprime_processos(int[] tempo_execucao, int[] tempo_espera, int[] tempo_restante, int[] tempo_chegada,  int []prioridade){
        //Imprime lista de processos
        for (int i = 0; i < n_processos; i++) {
            System.out.println("Processo["+i+"]: tempo_execucao="+ tempo_execucao[i] + " tempo_restante="+tempo_restante[i] + " tempo_chegada=" + tempo_chegada[i] + " prioridade =" +prioridade[i]);
        }
    }

    public static void imprime_stats (int[] espera) {
        int[] tempo_espera = espera.clone();
        //Implementar o calculo e impressão de estatisticas

        double tempo_espera_total = 0;

        for(int i=0; i<n_processos; i++){
            System.out.println("Processo["+i+"]: tempo_espera="+tempo_espera[i]);
            tempo_espera_total = tempo_espera_total + tempo_espera[i];
        }
        System.out.println("Tempo médio de espera: "+(tempo_espera_total/n_processos));
    }

    public static void FCFS(int[] execucao, int[] espera, int[] restante, int[] chegada){
        int[] tempo_execucao = execucao.clone();
        int[] tempo_espera = espera.clone();
        int[] tempo_restante = restante.clone();
        //int[] tempo_chegada = chegada.clone();

        int processo_em_execucao = 0; //processo inicial no FIFO é o zero

        //implementar código do FCFS
        for (int i=1; i<MAXIMO_TEMPO_EXECUCAO; i++) {
            System.out.println("tempo["+i+"]: processo["+processo_em_execucao+"] restante="+tempo_restante[processo_em_execucao]);

            if (tempo_execucao[processo_em_execucao] == tempo_restante[processo_em_execucao])
                tempo_espera[processo_em_execucao] = i-1;

            if (tempo_restante[processo_em_execucao] == 1) {
                if (processo_em_execucao == (n_processos-1))
                    break;
                else
                    processo_em_execucao++;
            }
            else
                tempo_restante[processo_em_execucao]--;
        }
        imprime_stats(tempo_espera);
    }

    public static void SJF(boolean preemptivo, int[] execucao, int[] espera, int[] restante, int[] chegada){
        int[] tempo_execucao = execucao.clone();
        int[] tempo_espera = espera.clone();
        int[] tempo_restante = restante.clone();
        int[] tempo_chegada = chegada.clone();

        //implementar código do SJF preemptivo e não preemptivo
        //...
        //

        imprime_stats(tempo_espera);

    }

    public static void PRIORIDADE(boolean preemptivo, int[] execucao, int[] espera, int[] restante, int[] chegada, int[] prioridade){
        int[] tempo_execucao = execucao.clone();
        int[] tempo_espera = espera.clone();
        int[] tempo_restante = restante.clone();
        int[] tempo_chegada = chegada.clone();
        int[] prioridade_temp = prioridade.clone();

        //implementar código do Prioridade preemptivo e não preemptivo
        //...
        //

        imprime_stats(tempo_espera);

    }

    public static void Round_Robin(int[] execucao, int[] espera, int[] restante){
        int[] tempo_execucao = execucao.clone();
        int[] tempo_espera = espera.clone();
        int[] tempo_restante = restante.clone();


        //implementar código do Round-Robin
        //...
        //

        imprime_stats(tempo_espera);
    }
}