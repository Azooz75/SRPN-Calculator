import java.util.Stack;
import java.math.BigInteger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SRPN {
  private Stack<Integer> stack = new Stack<>();
  private int[] randomArray = { 1804289383, 846930886, 1681692777, 1714636915, 1957747793, 424238335, 719885386,
      1649760492, 596516649, 1189641421, 1025202362, 1350490027, 783368690, 1102520059, 2044897763, 1967513926,
      1365180540, 1540383426, 304089172, 1303455736, 35005211, 521595368, 1804289383 }; // array of pre-determined
                                                                                        // pseudo-random numbers
  private int randomIndex = 0; // Index to monitor position in randomArray during instance run

  public void SRPN() { // (unused) contructor for SRPN object
  }

  public Stack<Integer> getStack() { // (unused) returns the stack
    return stack;
  }

  public void printStack() {
    for (int item : stack) {
      System.out.println(item);
    }
  }

  public void processCommand(String input) {
    processSingleToken(input);
  }

  private void processSingleToken(String input) {
    Pattern pattern = Pattern.compile("-?\\d+|[-+*/%=^]|[dr]|#(.*)#|\\s|\\x00|."); // regular expression encompassing
                                                                                   // all possible patterns, explained
                                                                                   // below
    Matcher matcher = pattern.matcher(input);

    while (matcher.find()) { // finds one of the defined patterns (returns false when no more patterns are
                             // found)
      String token = matcher.group(); // stores the matched sub-string to a string (group method returns last match)
      if (token.matches("-?\\d+")) { // pattern: -? zero or one occurence of -, followed by one or more digit
        BigInteger bigInt = new BigInteger(token);
        processInt(bigInt);
      } else if (token.matches("[-+*/%=^]")) {// pattern: any of the characters within brackets
        processOperator(token);
      } else if (token.matches("[dr]")) { // pattern: character d or r
        processChar(token);
      } else if (token.matches("\\x00") || token.matches("#(.*)#") || token.matches("\\s")) { // pattern: # followed by
                                                                                              // any character (.)
        // zero or more times, or spaces or null
        // do nothing
      } else if (token.matches(".")) { // if none of the above patterns are found, then any other character is invalid
                                       // (sequence of the if statements matters here)
        System.out.println("Unrecognised operator or operand \"" + token + "\".");
      }
    }
  }
  // below methods are private as there's no point for other classes to access
  // them, they are integrated functionalities of the SRPN calculator

  // method to process an integer into the stack
  private void processInt(BigInteger input) { // method to process an integer, also handles saturation
    int intValue = input.intValue();
    if (input.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) >= 0) { // 1 (>0) returned if greater than max value of
      // int, 0 returned if equal
      stack.push(Integer.MAX_VALUE);
    } else if (input.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) <= 0) {// -1 (< 0) returned if less than min
      // value of int, 0 returned if equal
      stack.push(Integer.MIN_VALUE);
    } else {
      stack.push(intValue);
    }
  }

  // method to process an operator
  private void processOperator(String input) { // method to process an operator
    char operator = input.charAt(input.length() - 1); // to be generalised when multiple arguments per line

    if (operator == '=') { // this is checked first as it does not involve operating on the stack
      if (stack.empty()) {
        System.out.println("Stack empty.");
      } else {
        System.out.println(stack.peek());
      }
      return;
    }
    if (stack.size() < 2) { // if not enough elements exist in the stack to perform below operations, print
      // error and return from method
      System.out.println("Stack underflow.");
      return;
    }
    BigInteger result;
    int xi1 = stack.pop(); // store value of x at i-1 (top item), then pop from stack
    int xi2 = stack.pop(); // store value of x at i-2 (second item), then pop
    switch (operator) {
      case '+':
        result = new BigInteger(Integer.toString(xi1)).add(new BigInteger(Integer.toString(xi2)));
        processInt(result);
        break;
      case '-':
        result = new BigInteger(Integer.toString(xi2)).subtract(new BigInteger(Integer.toString(xi1)));
        processInt(result);
        break;
      case '/':
        try {
          result = new BigInteger(Integer.toString(xi2)).divide(new BigInteger(Integer.toString(xi1)));
          processInt(result);
        } catch (ArithmeticException e) {
          System.out.println("Divide by 0.\n");
          stack.push(xi2); // return items to the stack as operation didn't complete
          stack.push(xi1);
          return;
        }
        break;
      case '%': // divide by zero exception is not caught in the emulator.
        result = new BigInteger(Integer.toString(xi2)).remainder(new BigInteger(Integer.toString(xi1)));
        processInt(result);
        break;
      case '*':
        result = new BigInteger(Integer.toString(xi2)).multiply(new BigInteger(Integer.toString(xi1)));
        processInt(result);
        break;
      case '^':
        if (xi1 < 0) {
          System.out.println("Negative power.");
          stack.push(xi2); // return items to the stack as operation didn't complete
          stack.push(xi1);
          return;
        }
        try {
          result = new BigInteger(Integer.toString(xi2)).pow(xi1);
          processInt(result);
        } catch (ArithmeticException e) { // BigInteger returns this exception if the result overflows the supported
                                          // range (memory dependent) so it is caught to return saturated value instead
          if (xi2 > 0) // if the base is positive, the result is positive no matter the exponent
          {
            result = BigInteger.valueOf(Integer.MAX_VALUE);
          } else if (xi2 < 0 && xi1 % 2 == 0) { // if the base is negative, the result is positive if the exponent is
                                                // divisable by zero (ending with 2)
            result = BigInteger.valueOf(Integer.MAX_VALUE);
          } else // if the base is negative, the result is negative if the exponent is not
                 // divisable by zero (not ending with 2)
            result = BigInteger.valueOf(Integer.MIN_VALUE);
        }
        processInt(result);
        break;
      }
    }


  // method to process an acceptable character
  private void processChar(String input) {
    if (input.equals("d") && stack.size() > 0) {
      printStack();
    } else if (input.equals("d") && stack.size() == 0) {
      System.out.println(Integer.MIN_VALUE); // print min value but don't push it to stack, so it can be replaced when
                                             // actual values are entered and so '=' still prints stack empty
    } else if (input.equals("r")) {
      pushRandom();
    }
  }

  // method to push a pseudo-random pre-defined number
  private void pushRandom() {
    BigInteger randomNum;
    try {
      randomNum = new BigInteger(String.valueOf(randomArray[randomIndex]));
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Stack overflow.");
      return;
    }
    randomIndex++;
    processInt(randomNum);
  }
}
