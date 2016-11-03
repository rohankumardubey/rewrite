package com.netflix.java.refactor.refactor.op

import com.netflix.java.refactor.parse.OracleJdkParser
import com.netflix.java.refactor.parse.Parser

abstract class ChangeMethodInvocationTest(p: Parser): Parser by p {

//    @Test
//    fun transformStringArgument() {
//        val a = """
//            |class A {
//            |   public void test() {
//            |       String s = "bar";
//            |       new B().singleArg("foo (%s)" + s + 0L);
//            |   }
//            |}
//        """
//
//        val cu = parse(a, b)
//        cu.refactor()
//                .findMethodCalls("B singleArg(String)")
//                    .changeArguments()
//                        .arg(String::class.java)
//                            .changeLiterals { s -> s.toString().replace("%s", "{}") }
//                            .done()
//                        .done()
//                    .done()
//                .fix()
//
//        assertRefactored(cu, """
//            |class A {
//            |   public void test() {
//            |       String s = "bar";
//            |       new B().singleArg("foo ({})" + s + 0L);
//            |   }
//            |}
//        """)
//    }
//
//    @Test
//    fun refactorExplicitStaticToVariable() {
//        val a = """
//            |package a;
//            |public class A {
//            |   public void foo() {}
//            |}
//        """
//
//        val b = """
//            |package b;
//            |public class B {
//            |   public static void foo() {}
//            |}
//        """
//
//        val c = """
//            |import a.*;
//            |import b.B;
//            |public class C {
//            |   A a;
//            |   public void test() {
//            |       B.foo();
//            |   }
//            |}
//        """
//
//        val cu = parse(c, a, b)
//        cu.refactor()
//                .findMethodCalls("b.B foo()")
//                    .changeTargetToVariable("a")
//                    .done()
//                .fix()
//
//        assertRefactored(cu, """
//            |import a.*;
//            |import b.B;
//            |public class C {
//            |   A a;
//            |   public void test() {
//            |       a.foo();
//            |   }
//            |}
//        """)
//    }
//
//    @Test
//    fun refactorStaticImportToVariable() {
//        val a = """
//            |package a;
//            |public class A {
//            |   public void foo() {}
//            |}
//        """
//
//        val b = """
//            |package b;
//            |public class B {
//            |   public static void foo() {}
//            |}
//        """
//
//        val c = """
//            |import a.*;
//            |import static b.B.*;
//            |public class C {
//            |   A a;
//            |   public void test() {
//            |       foo();
//            |   }
//            |}
//        """
//
//        val cu = parse(c, a, b)
//        cu.refactor()
//                .findMethodCalls("b.B foo()")
//                    .changeTargetToVariable("a")
//                    .done()
//                .fix()
//
//        assertRefactored(cu, """
//            |import a.*;
//            |import static b.B.*;
//            |public class C {
//            |   A a;
//            |   public void test() {
//            |       a.foo();
//            |   }
//            |}
//        """)
//    }
//
//    @Test
//    fun refactorLiteralStringWithEscapableCharacters() {
//        val a = """
//            |package a;
//            |public class A {
//            |    public void foo(String s) {}
//            |}
//        """
//
//        val b = """
//            |import a.*;
//            |public class B {
//            |    A a;
//            |    public void test() {
//            |        a.foo("mystring '%s'");
//            |    }
//            |}
//        """
//
//        val cu = parse(b, a)
//        cu.refactor()
//            .findMethodCalls("a.A foo(..)")
//                .changeArguments()
//                    .arg(String::class.java)
//                        .changeLiterals { s -> s.toString().replace("%s", "{}") }
//                        .done()
//                    .done()
//                .done()
//            .fix()
//
//        assertRefactored(cu, """
//            |import a.*;
//            |public class B {
//            |    A a;
//            |    public void test() {
//            |        a.foo("mystring '{}'");
//            |    }
//            |}
//        """)
//    }
//
//    @Test
//    fun refactorReorderArguments() {
//        val a = """
//            |package a;
//            |public class A {
//            |   public void foo(String s, Integer n) {}
//            |   public void foo(Integer n, String s) {}
//            |}
//        """
//
//        val b = """
//            |import a.*;
//            |public class B {
//            |   A a;
//            |   public void test() {
//            |       a.foo("mystring", 0);
//            |   }
//            |}
//        """
//
//        val cu = parse(b, a)
//        cu.refactor()
//                .findMethodCalls("a.A foo(..)")
//                    .changeArguments()
//                        .arg(String::class.java)
//                            .changeLiterals { s -> "anotherstring" }
//                            .done()
//                        .reorderByArgName("n", "s")
//                        .done()
//                    .done()
//                .fix()
//
//        assertRefactored(cu, """
//            |import a.*;
//            |public class B {
//            |   A a;
//            |   public void test() {
//            |       a.foo(0, "anotherstring");
//            |   }
//            |}
//        """)
//    }
//
//    @Test
//    fun refactorReorderArgumentsWithNoSourceAttachment() {
//        val a = """
//            |package a;
//            |public class A {
//            |   public void foo(String arg0, Integer... arg1) {}
//            |   public void foo(Integer arg0, Integer arg1, String arg2) {}
//            |}
//        """
//
//        val b = """
//            |import a.*;
//            |public class B {
//            |   A a;
//            |   public void test() {
//            |       a.foo("s", 0, 1);
//            |   }
//            |}
//        """
//
//        val cu = parse(b, a)
//        cu.refactor()
//                .findMethodCalls("a.A foo(..)")
//                    .changeArguments()
//                        .whereArgNamesAre("s", "n")
//                        .reorderByArgName("n", "s")
//                        .done()
//                    .done()
//                .fix()
//
//        assertRefactored(cu, """
//            |import a.*;
//            |public class B {
//            |   A a;
//            |   public void test() {
//            |       a.foo(0, 1, "s");
//            |   }
//            |}
//        """)
//    }
//
//    @Test
//    fun refactorReorderArgumentsWhereOneOfTheOriginalArgumentsIsVararg() {
//        val a = """
//            |package a;
//            |public class A {
//            |   public void foo(String s, Integer n, Object... o) {}
//            |   public void bar(String s, Object... o) {}
//            |}
//        """
//
//        val b = """
//            |import a.*;
//            |public class B {
//            |   A a;
//            |   public void test() {
//            |       a.foo("mystring", 0, "a", "b");
//            |   }
//            |}
//        """
//
//        val cu = parse(b, a)
//        cu.refactor()
//                .findMethodCalls("a.A foo(..)")
//                    .changeName("bar")
//                    .changeArguments()
//                        .reorderByArgName("s", "o", "n")
//                        .done()
//                    .done()
//                .fix()
//
//        assertRefactored(cu, """
//            |import a.*;
//            |public class B {
//            |   A a;
//            |   public void test() {
//            |       a.bar("mystring", "a", "b", 0);
//            |   }
//            |}
//        """)
//    }
//
//    @Test
//    fun refactorReorderArgumentsWhereTheLastArgumentIsVarargAndNotPresentInInvocation() {
//        val a = """
//            |package a;
//            |public class A {
//            |   public void foo(String s, Object... o) {}
//            |}
//        """
//
//        val b = """
//            |import a.*;
//            |public class B {
//            |   public void test() {
//            |       new A().foo("mystring");
//            |   }
//            |}
//        """
//
//        val cu = parse(b, a)
//        cu.refactor()
//                .findMethodCalls("a.A foo(..)")
//                    .changeArguments()
//                        .whereArgNamesAre("s", "o")
//                        .reorderByArgName("o", "s")
//                        .done()
//                    .done()
//                .fix()
//
//        assertRefactored(cu, """
//            |import a.*;
//            |public class B {
//            |   public void test() {
//            |       new A().foo("mystring");
//            |   }
//            |}
//        """)
//    }
//

    private val b: String = """
                |class B {
                |   public void singleArg(String s) {}
                |   public void arrArg(String[] s) {}
                |   public void varargArg(String... s) {}
                |}
            """
}

class OracleJdkChangeMethodInvocationTest: ChangeMethodInvocationTest(OracleJdkParser())