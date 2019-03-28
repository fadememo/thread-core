package test.util;

import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SpeLUtil {
    public static void main(String[] args) {

    }
    @Test
    public void testEle(){
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("6+2");
        Integer integer = (Integer) expression.getValue();
        System.out.println(integer);
    }
}
