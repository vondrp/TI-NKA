package main;

public class Automat {

    static final int STATE_COUNT = 10;
    static final int MICRO_STATE_COUNT = 12;
    static final int ALPHABET_SIZE = 2;
    boolean[][] activations;

    /**
     * from A to J
     */
    private int state;


    public int[][] transitions;


    private void setupMicroStates() {
        //A
        activations[0][0] = true;
        activations[0][1] = true;
        activations[0][2] = true;
        activations[0][3] = true;

        //B
        activations[1][1] = true;
        activations[1][3] = true;
        activations[1][4] = true;

        //C
        activations[2][2] = true;
        activations[2][3] = true;

        //D
        activations[3][5] = true;
        //E
        activations[4][4] = true;

        //F
        activations[5][6] = true;

        //G
        activations[6][7] = true;

        //H
        activations[7][8] = true;
        activations[7][9] = true;
        activations[7][10] = true;
        activations[7][11] = true;

        //I
        activations[8][9] = true;
        activations[8][11] = true;

        //J
        activations[9][9] = true;
        activations[9][11] = true;
    }

    private static int cc(char c){
        if(c>='a')
            c-='a';
        else if(c>='A')
            c-='A';
        return c;
    }
    private void setupTransitions() {
        //A - B,C
        transitions[0][0] = cc('B');
        transitions[0][1] = cc('C');

        //B - B,D
        transitions[1][0] = cc('B');
        transitions[1][1] = cc('D');

        //C - E,C
        transitions[2][0] = cc('E');
        transitions[2][1] = cc('C');

        //D - F,
        transitions[3][0] = cc('F');
        transitions[3][1] = -1;

        //E - ,D
        transitions[4][0] = -1;
        transitions[4][1] = cc('D');

        //F - ,G
        transitions[5][0] = -1;
        transitions[5][1] = cc('G');

        //G - H,
        transitions[6][0] = cc('H');
        transitions[6][1] = -1;

        //H - I,J
        transitions[7][0] = cc('I');
        transitions[7][1] = cc('J');

        //I - I,
        transitions[8][0] = cc('I');
        transitions[8][1] = -1;

        //J - ,J
        transitions[9][0] = -1;
        transitions[9][1] = cc('J');
    }

    public Automat() {
        this.activations = new boolean[STATE_COUNT][MICRO_STATE_COUNT];
        this.transitions = new int[STATE_COUNT][ALPHABET_SIZE];
        state=0;

        setupMicroStates();
        setupTransitions();
    }
    public void input(int i){
        int nextState = transitions[state][i];
        System.out.println("From "+(char)(state+'A')+" to "+(char)(nextState+'A')+" using symbol "+(char)(i+'a'));
        if (nextState==-1){
            //reset to zero?
            reset();
            //System.out.println("idk");
        }else
            state=nextState;


    }
    public boolean[] getActivations(){
        return activations[state];
    }
    public void reset(){
        state=0;
    }

    public boolean isEndState() {
        // H, I, J
        return state >= 7 && state <= 9;
    }
}
