### Java Operation Evaluator

Note: This program doesn't parse String, it evaluates tokens.
Valid tokens are
```
+
-
*
/
^
=
+=
-=
*=
/=
^=
```

Example Usage
```java
HashMap<String, Double> variables = new HashMap<>();
variables.put("PI", Math.PI);
variables.put("E", Math.E);
variables.put("abc", 5.0);
TokenList threePlusFive = new TokenList("+", "3", "PI");
TokenList fiveMinusThree = new TokenList("-", "5", "3");
TokenList multiplyFMTAndTPF = new TokenList("*", fiveMinusThree, threePlusFive);
TokenList variableXYZ = new TokenList("=", "xyz", multiplyFMTAndTPF);
TokenList variableAdd = new TokenList("*=", "abc", variableXYZ);
System.out.println(parse(variableAdd, variables) + "\n" + variables);
```