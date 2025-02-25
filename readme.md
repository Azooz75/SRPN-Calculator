# SRPN Calculator

The SRPN (Saturated Reverse Polish Notation) Calculator is a Java-based calculator that processes mathematical
expressions using Reverse Polish Notation (RPN). 
It supports basic arithmetic operations, saturation arithmetic, and a set of predefined pseudo-random numbers.

# Purpose

The motivation behind this project was to advance my understanding of OOP and stack data structures. This project was submitted
as part of the required coursework for the "Principles of Programming" module at the University of Bath.

## Features

- Processes commands in Reverse Polish Notation.
- Supports basic arithmetic operations: addition, subtraction, multiplication, division, modulus, and exponentiation.
- Uses a stack to store results.
- Handles integer overflow and underflow using saturation arithmetic.

## Usage

### Running the Calculator

To run the SRPN Calculator, compile the Java source files and execute the main class.

### Commands

- **Numbers**: Pushes the number onto the stack.
- **Operators**: Performs the operation using the top two numbers on the stack.
    - `+`: Addition
    - `-`: Subtraction
    - `*`: Multiplication
    - `/`: Division
    - `%`: Modulus
    - `^`: Exponentiation
- **Special Commands**:
    - `d`: Displays the current stack.
    - `r`: Pushes a pseudo-random number onto the stack.

### Example

```sh
> 5 3 + = 
8
> 10 2 / =
5
> d
5
```

## License

MIT License

Copyright (c) 2024 - Abdel Mauz Bakri

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

