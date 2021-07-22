### Java Operation Evaluator

Note: This program doesn't parse String, it evaluates tokens with operators.
Valid operators are
```
+ - * / ^
= += -= *= /= ^=
```

Valid tokens are
```
"5"                (String)
"variable-name"    (Variable name stored in 'variables' HashMap)
"5.0"              (String)
5                  (Integer)
5.0                (Double)
otherwise      ->  Exception
```

Example Usage
```java
HashMap<String, Double> variables = new HashMap<>();
variables.put("PI", Math.PI);
variables.put("E", Math.E);
variables.put("abc", 5.0);
TokenList threePlusPI = new TokenList("+", 3, "PI");
TokenList fiveMinusE = new TokenList("-", 5.0, "E");
TokenList multiplyFMTAndTPF = new TokenList("*", fiveMinusE, threePlusPI);
TokenList variableXYZ = new TokenList("=", "xyz", multiplyFMTAndTPF);
TokenList variableAdd = new TokenList("*=", "abc", variableXYZ);
System.out.println(parse(variableAdd, variables) + "\n" + variables);
```

Evaluates

`PI = 3.1415...`

`E = 2.71...`

`abc = 5.0...`

`abc *= xyz = (5.0 - E) * (3 + PI)`

Evaluation order
`(5.0 - E) * (3 + PI)` -> `xyz` -> `abc`
