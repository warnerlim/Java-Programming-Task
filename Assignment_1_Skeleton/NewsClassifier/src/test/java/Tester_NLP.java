import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import uob.oop.NLP;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Tester_NLP {

    @Test
    @Order(1)
    void textCleaning(){
        String strTest = "!\"$%&^%H).,e+ll~'/o/Wor.l,d!";
        assertEquals("helloworld", NLP.textCleaning(strTest));
    }

    @Test
    @Order(2)
    void textLemmatization(){
        String strTest = "bananas ending pained stuffs";
        assertEquals("banana end pain stuff", NLP.textLemmatization(strTest));
    }

    @Test
    @Order(3)
    void removeStopWords(){
        String _content = "The apple is large and not far from the tree in the forest";
        String[] _stopWords = {"and", "the", "is", "in"};
        assertEquals("The apple large not far from tree forest", NLP.removeStopWords(_content, _stopWords));
    }

}
