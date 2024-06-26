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
        int n_processos = 3;

        if (!preemptivo) {

            // ordena os processos

            for (int i = 0; i < n_processos; i++) {
                for (int j = i + 1; j < n_processos; j++) {
                    if (tempo_execucao[i] > tempo_execucao[j]) {
                        int temp = tempo_execucao[i];
                        tempo_execucao[i] = tempo_execucao[j];
                        tempo_execucao[j] = temp;

                        temp = tempo_chegada[i];
                        tempo_chegada[i] = tempo_chegada[j];
                        tempo_chegada[j] = temp;
                    }
                }
            }

            // calcular o tempo de espera e simular a execução dos processos

            int tempo_atual = 0;
            for (int i = 0; i < n_processos; i++) {
                if (tempo_atual < tempo_chegada[i]) {
                    tempo_atual = tempo_chegada[i];
                }

                // Executa o processo completo e imprime o tempo restante

                while (tempo_restante[i] > 0) {
                    tempo_restante[i]--;
                    tempo_atual++;
                    System.out.println("tempo[" + tempo_atual + "]: processo[" + i + "] restante=" + tempo_restante[i]);
                }

                if (i > 0) {
                    tempo_espera[i] = tempo_atual - tempo_chegada[i] - tempo_execucao[i];
                } else {
                    tempo_espera[i] = 0;
                }
            }

            // calcular o tempo de resposta

            int[] tempo_resposta = new int[n_processos];
            for (int i = 0; i < n_processos; i++) {
                tempo_resposta[i] = tempo_execucao[i] + tempo_espera[i];
            }
        } else {

            // preemptivo

            int tempo_atual = 0;
            int processos_concluidos = 0;

            while (processos_concluidos < n_processos) {
                int menor_tempo_restante = Integer.MAX_VALUE;
                int proximo_processo = -1;

                // acha o processo com menor tempo de execucao

                for (int i = 0; i < n_processos; i++) {
                    if (tempo_chegada[i] <= tempo_atual && tempo_restante[i] < menor_tempo_restante && tempo_restante[i] > 0) {
                        menor_tempo_restante = tempo_restante[i];
                        proximo_processo = i;
                    }
                }

                // se nao achar nenhum processo disponivel, avanca para o proximo tempo

                if (proximo_processo == -1) {
                    tempo_atual++;
                    continue;
                }

                // executa uma unidade de tempo para o proximo processo

                tempo_restante[proximo_processo]--;
                tempo_atual++;

                // printa o tempo do processo

                System.out.println("tempo[" + tempo_atual + "]: processo[" + proximo_processo + "] restante=" + tempo_restante[proximo_processo]);

                // se o processo for concluido, calcula o tempo de espera e atualiza o número de processos concluídos

                if (tempo_restante[proximo_processo] == 0) {
                    tempo_espera[proximo_processo] = tempo_atual - tempo_execucao[proximo_processo] - tempo_chegada[proximo_processo];
                    processos_concluidos++;
                }
            }
        }

        imprime_stats(tempo_espera);
    }

    public static void PRIORIDADE(boolean preemptivo, int[] execucao, int[] espera, int[] restante, int[] chegada, int[] prioridade){
        int n_processos = execucao.length;
        int tempo_corrente = 0;
        int processos_completos = 0;

        // Inicializa tempos de espera e restante

        for (int i = 0; i < n_processos; i++) {
            espera[i] = 0;
            restante[i] = execucao[i];
        }

        while (processos_completos < n_processos) {
            int processoAtual = -1;
            int maior_prioridade = Integer.MAX_VALUE;

            // Seleciona o próximo processo a ser executado

            for (int i = 0; i < n_processos; i++) {
                if (chegada[i] <= tempo_corrente && restante[i] > 0) {
                    if (prioridade[i] < maior_prioridade || (prioridade[i] == maior_prioridade && !preemptivo)) {
                        maior_prioridade = prioridade[i];
                        processoAtual = i;
                    }
                }
            }

            if (processoAtual == -1) {
                tempo_corrente++;
                continue;
            }

            if (preemptivo) {
                restante[processoAtual]--;
                System.out.println("tempo[" + tempo_corrente + "]: processo[" + processoAtual + "] restante=" + restante[processoAtual]);
                if (restante[processoAtual] == 0) {
                    processos_completos++;
                    espera[processoAtual] = tempo_corrente + 1 - execucao[processoAtual] - chegada[processoAtual];
                }
                tempo_corrente++;
            } else {
                System.out.println("tempo[" + tempo_corrente + "]: processo[" + processoAtual + "] restante=" + restante[processoAtual]);
                tempo_corrente += restante[processoAtual];
                restante[processoAtual] = 0;
                processos_completos++;
                espera[processoAtual] = tempo_corrente - execucao[processoAtual] - chegada[processoAtual];
            }
        }

        imprime_stats(espera);
    }

    public static void Round_Robin(int[] execucao, int[] espera, int[] restante){
        Scanner scanner = new Scanner(System.in);

        int n_processos = execucao.length;
        int[] tempo_restante = execucao.clone();
        int[] tempo_espera = new int[n_processos];

        System.out.print("Digite o valor do time slice: ");
        int timeSlice = scanner.nextInt();

        int tempo = 0;
        boolean processos_restantes = true;

        while (processos_restantes) {
            processos_restantes = false;

            for (int i = 0; i < n_processos; i++) {
                if (tempo_restante[i] > 0) {
                    processos_restantes = true;

                    if (tempo_restante[i] > timeSlice) {
                        tempo += timeSlice;
                        tempo_restante[i] -= timeSlice;

                        for (int j = 0; j < n_processos; j++) {
                            if (j != i && tempo_restante[j] > 0) {
                                tempo_espera[j] += timeSlice;
                            }
                        }
                    } else {
                        tempo += tempo_restante[i];
                        for (int j = 0; j < n_processos; j++) {
                            if (j != i && tempo_restante[j] > 0) {
                                tempo_espera[j] += tempo_restante[i];
                            }
                        }
                        tempo_restante[i] = 0;
                    }
                    System.out.println("tempo[" + tempo + "]: processo[" + i + "] restante=" + tempo_restante[i]);
                }
            }
        }

        imprime_stats(tempo_espera);
    }
}