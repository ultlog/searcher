package com.ultlog.searcher.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LogReaderTest {

    @Autowired
    private LogReader logReader;

    @Test
    public void testLog() {
        String log1 = "2020-07-05 12:22:47.486 [main] ERROR com.example.demo.DemoApplicationTests - test error \n" +
                "java.lang.RuntimeException: tea das asdsawda asdas a\n" +
                "\tat com.example.demo.DemoApplicationTests.contextLoads(DemoApplicationTests.java:21)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n" +
                "\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n" +
                "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "\tat java.lang.reflect.Method.invoke(Method.java:498)\n" +
                "\tat org.junit.platform.commons.util.ReflectionUtils.invokeMethod(ReflectionUtils.java:675)\n" +
                "\tat org.junit.jupiter.engine.execution.MethodInvocation.proceed(MethodInvocation.java:60)\n" +
                "\tat org.junit.jupiter.engine.execution.InvocationInterceptorChain$ValidatingInvocation.proceed(InvocationInterceptorChain.java:125)\n" +
                "\tat org.junit.jupiter.engine.extension.TimeoutExtension.intercept(TimeoutExtension.java:132)\n" +
                "\tat org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestableMethod(TimeoutExtension.java:124)\n" +
                "\tat org.junit.jupiter.engine.extension.TimeoutExtension.interceptTestMethod(TimeoutExtension.java:74)\n" +
                "\tat org.junit.jupiter.engine.execution.ExecutableInvoker$ReflectiveInterceptorCall.lambda$ofVoidMethod$0(ExecutableInvoker.java:115)\n" +
                "\tat org.junit.jupiter.engine.execution.ExecutableInvoker.lambda$invoke$0(ExecutableInvoker.java:105)\n" +
                "\tat org.junit.jupiter.engine.execution.InvocationInterceptorChain$InterceptedInvocation.proceed(InvocationInterceptorChain.java:104)\n" +
                "\tat org.junit.jupiter.engine.execution.InvocationInterceptorChain.proceed(InvocationInterceptorChain.java:62)\n" +
                "\tat org.junit.jupiter.engine.execution.InvocationInterceptorChain.chainAndInvoke(InvocationInterceptorChain.java:43)\n" +
                "\tat org.junit.jupiter.engine.execution.InvocationInterceptorChain.invoke(InvocationInterceptorChain.java:35)\n" +
                "\tat org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:104)\n" +
                "\tat org.junit.jupiter.engine.execution.ExecutableInvoker.invoke(ExecutableInvoker.java:98)\n" +
                "\tat org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.lambda$invokeTestMethod$6(TestMethodTestDescriptor.java:202)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                "\tat org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.invokeTestMethod(TestMethodTestDescriptor.java:198)\n" +
                "\tat org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:135)\n" +
                "\tat org.junit.jupiter.engine.descriptor.TestMethodTestDescriptor.execute(TestMethodTestDescriptor.java:69)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:135)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:125)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.Node.around(Node.java:135)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:123)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:122)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:80)\n" +
                "\tat java.util.ArrayList.forEach(ArrayList.java:1257)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:38)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:139)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:125)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.Node.around(Node.java:135)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:123)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:122)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:80)\n" +
                "\tat java.util.ArrayList.forEach(ArrayList.java:1257)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.invokeAll(SameThreadHierarchicalTestExecutorService.java:38)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$5(NodeTestTask.java:139)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$7(NodeTestTask.java:125)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.Node.around(Node.java:135)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.lambda$executeRecursively$8(NodeTestTask.java:123)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.ThrowableCollector.execute(ThrowableCollector.java:73)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.executeRecursively(NodeTestTask.java:122)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.NodeTestTask.execute(NodeTestTask.java:80)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.SameThreadHierarchicalTestExecutorService.submit(SameThreadHierarchicalTestExecutorService.java:32)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestExecutor.execute(HierarchicalTestExecutor.java:57)\n" +
                "\tat org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine.execute(HierarchicalTestEngine.java:51)\n" +
                "\tat org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:229)\n" +
                "\tat org.junit.platform.launcher.core.DefaultLauncher.lambda$execute$6(DefaultLauncher.java:197)\n" +
                "\tat org.junit.platform.launcher.core.DefaultLauncher.withInterceptedStreams(DefaultLauncher.java:211)\n" +
                "\tat org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:191)\n" +
                "\tat org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:128)\n" +
                "\tat com.intellij.junit5.JUnit5IdeaTestRunner.startRunnerWithArgs(JUnit5IdeaTestRunner.java:69)\n" +
                "\tat com.intellij.rt.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:33)\n" +
                "\tat com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:230)\n" +
                "\tat com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:58)";

        logReader.readLogFromString(log1);
    }

}