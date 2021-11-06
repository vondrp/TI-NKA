package main;

import java.util.Stack;

/**
 * Class Automat represents a representation
 * of a finite automaton
 */
public class Automat {

    /** how many group of states */
    static final int STATE_COUNT = 10;

    /** how many  single states */
    static final int MICRO_STATE_COUNT = 12;

    /** size of used alphabet */
    static final int ALPHABET_SIZE = 2;

    /** status of states activations */
    boolean[][] activations;

    /** number of the current state
     * can be imagined as from A to J
     */
    private int state;

    /** history of states */
    private final Stack<Integer> statesHistory = new Stack<>();

    /** transitions for states
     *  first dimension represents
     *  a state and second one
     *  has size of alphabet which
     *  can be on the input
     */
    public int[][] transitions;

    /**
     * Create an instance
     * of the finite automaton
     */
    public Automat() {
        this.activations = new boolean[STATE_COUNT][MICRO_STATE_COUNT];
        this.transitions = new int[STATE_COUNT][ALPHABET_SIZE];
        state=0;

        setupMicroStates();
        setupTransitions();
    }

    /**
     * for each group of states
     * sets which of the micro states
     * are going to be active
     */
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

    /**
     * Return integer representation of a char
     * @param c     input char
     * @return      integer value of the char
     */
    private static int cc(char c){
        if(c>='a')
            c-='a';
        else if(c>='A')
            c-='A';
        return c;
    }

    /**
     * for all group of states
     * set up their transitions
     */
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

    /**
     * Read the input and change accordingly to it current state
     * @param i     input
     */
    public void input(int i){
        statesHistory.push(state);
        int nextState = transitions[state][i];
        System.out.println("From "+(char)(state+'A')+" to "+(char)(nextState+'A')+" using symbol "+(char)(i+'a'));
        if (nextState==-1){
            //reset to zero?
            state = 0;
            //reset();
            //System.out.println("idk");
        }else {
            state = nextState;
        }
    }

    /**
     * @return  how are states activated in their group
     */
    public boolean[] getActivations(){
        return activations[state];
    }

    /**
     * reset finite automaton to default state
     */
    public void reset(){
        state=0;
        statesHistory.clear();
    }

    /**
     * return to the previous state
     */
    public void backwards(){
        if(!statesHistory.isEmpty())
            state = statesHistory.pop();
    }

    /**
     * @return  if the state is one of the end states
     */
    public boolean isEndState() {
        // H, I, J
        return state >= 7 && state <= 9;
    }
}
