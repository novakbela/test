package it;

import com.flexionmobile.codingchallenge.integration.IntegrationTestRunner;
import main.java.challenge.IntegrationImpl;

public class IntegrationTest {

    @Test
    public void integrationTest() {
        IntegrationTestRunner tester = new IntegrationTestRunner();
        tester.runTests(new IntegrationImpl());
    }
}
