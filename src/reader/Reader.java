/* Generated By:JavaCC: Do not edit this line. Reader.java */
package reader;
import java.io.*;
import java.nio.charset.StandardCharsets;
import carte.*;
import entite.*;
import gui.*;
import moteurDuJeu.*;
import operateur.*;
import personnages.*;
import pickable.*;
import sequence.*;


public class Reader implements ReaderConstants {
  //TODO Mettre un return pour retourner l'arbre et modifier le nom de la fonction
  public static void main(String [] args)
  {
    String str = Test.giveString();
        Reader parser = new Reader(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)));
        try
        {
          _Sequence resultingSequence = Reader.SEQUENCE();
        }
        catch (Exception e)
        {
          System.out.println("NOK");
          System.out.println(e.getMessage());
          Reader.ReInit(new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)));
        }
        catch (Error e)
        {
          System.out.println("Oops");
          System.out.println(e.getMessage());
        }
    //return _Sequence ;
  }

  static final public Behavior BEH() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case BARRE:
      jj_consume_token(BARRE);
    {if (true) return new RandomBar();}
      break;
    case SEMICOLON:
      jj_consume_token(SEMICOLON);
    {if (true) return new Succession();}
      break;
    case CHEVRON:
      jj_consume_token(CHEVRON);
    {if (true) return new Priority();}
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public _Sequence SEQUENCE() throws ParseException {
  Action act ;
  Behavior beh ;
  _Sequence left, right ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LPAR:
      jj_consume_token(LPAR);
      left = SEQUENCE();
      beh = BEH();
      right = SEQUENCE();
      jj_consume_token(RPAR);
        {if (true) return new Tree(beh, left, right) ;}
      break;
    case A:
    case M:
      act = ACTION();
        {if (true) return act;}
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public Action ACTION() throws ParseException {
        Movement mov ;
        Attack att ;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case M:
      jj_consume_token(M);
      mov = MOVEMENT();
        {if (true) return mov ;}
      break;
    case A:
      jj_consume_token(A);
      att = ATT();
        {if (true) return att ;}
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public Attack ATT() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case C:
      jj_consume_token(C);
          {if (true) return new ClassicAck();}
      break;
    case S:
      jj_consume_token(S);
          {if (true) return new SuicideBomber();}
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public Movement MOVEMENT() throws ParseException {
        Token tok1, tok2 ;
        int len ;
        int x, y;
        Direction dir;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case C:
      jj_consume_token(C);
      tok1 = jj_consume_token(DIGIT);
      dir = DIRECTION();
          len = Integer.parseInt( tok1.image );
          {if (true) return new MoveDir(dir, len);}
      break;
    case R:
      jj_consume_token(R);
      tok1 = jj_consume_token(DIGIT);
          len = Integer.parseInt( tok1.image );
          {if (true) return new Recall(len);}
      break;
    case T:
      jj_consume_token(T);
      tok1 = jj_consume_token(DIGIT);
      tok2 = jj_consume_token(DIGIT);
          x = Integer.parseInt( tok1.image );
          y = Integer.parseInt( tok2.image );
          {if (true) return new Tunnel(x, y);}
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public Direction DIRECTION() throws ParseException {
    jj_consume_token(N);
          {if (true) return Direction.NORTH;}
    jj_consume_token(W);
          {if (true) return Direction.WEST;}
    jj_consume_token(S);
          {if (true) return Direction.SOUTH;}
    jj_consume_token(E);
          {if (true) return Direction.EAST;}
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public ReaderTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[5];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x0,0x0,0x0,0x0,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x700000,0x40180,0x180,0x600,0x1a00,};
   }

  /** Constructor with InputStream. */
  public Reader(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Reader(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ReaderTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Reader(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ReaderTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Reader(ReaderTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ReaderTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[57];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 5; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 57; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}
