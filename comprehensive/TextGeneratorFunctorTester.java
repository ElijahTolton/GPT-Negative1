package comprehensive;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Contains tests for TexGeneratorFunctor
 *
 * @author Canon Curtis and Elijah Tolton
 * @version 4/18/2024
 */
public class TextGeneratorFunctorTester {

    @Test
    void test3ParamHelloWorld() throws FileNotFoundException {
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\helloWorld.txt", "hello", "4");
        BinaryMaxHeap<KeyValuePair> heap = func.no4thParameter();
        assertEquals("world",heap.extractMax().getWord());
        assertTrue(heap.isEmpty());
    }

    @Test
    void test3ParamABC() throws FileNotFoundException {
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\abc.txt", "a", "5");
        BinaryMaxHeap<KeyValuePair> heap = func.no4thParameter();
        assertEquals("b", heap.extractMax().getWord());
        assertEquals("c", heap.extractMax().getWord());
        assertEquals("d", heap.extractMax().getWord());
        assertEquals("e", heap.extractMax().getWord());
        assertEquals("f", heap.extractMax().getWord());
        assertEquals(20, heap.size());
    }

    @Test
    void test3ParamATrickyFormatting() throws FileNotFoundException {
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\trickyFormatting.txt", "a", "3");
        BinaryMaxHeap<KeyValuePair> heap = func.no4thParameter();
        assertEquals("few", heap.extractMax().getWord());
        assertTrue(heap.isEmpty());
    }

    @Test
    void test3ParamFileTrickyFormatting() throws FileNotFoundException {
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\trickyFormatting.txt", "file", "3");
        BinaryMaxHeap<KeyValuePair> heap = func.no4thParameter();
        assertEquals("iirc", heap.extractMax().getWord());
        assertTrue(heap.isEmpty());
    }

    @Test
    void test3ParamFomatTrickyFormatting() throws FileNotFoundException {
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\trickyFormatting.txt", "fomatting", "3");
        BinaryMaxHeap<KeyValuePair> heap = func.no4thParameter();
        assertEquals("things", heap.extractMax().getWord());
        assertTrue(heap.isEmpty());
    }

    @Test
    void test3ParamThingsTrickyFormatting() throws FileNotFoundException{
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\trickyFormatting.txt", "things", "3");
        BinaryMaxHeap<KeyValuePair> heap = func.no4thParameter();
        assertEquals("yup", heap.extractMax().getWord());
        assertTrue(heap.isEmpty());
    }

    @Test
    void test3ParamFewTrickyFormatting() throws FileNotFoundException{
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\trickyFormatting.txt", "few", "10");
        BinaryMaxHeap<KeyValuePair> heap = func.no4thParameter();
        assertEquals("them", heap.extractMax().getWord());
        assertTrue(heap.isEmpty());
    }

    @Test
    void test3ParamSeedAfterSeed() throws FileNotFoundException{
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\SeedAfterSeed", "hi", "4");
        BinaryMaxHeap<KeyValuePair> heap = func.no4thParameter();
        assertEquals("hi", heap.extractMax().getWord());
        assertEquals("tim", heap.extractMax().getWord());
        assertTrue(heap.isEmpty());

    }
    @Test
    void test4ParamOneHelloWorld() throws FileNotFoundException {
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\helloWorld.txt", "hello", "4");
        assertEquals("[hello, world, hello, world]", func.fourthArgumentOne().toString());
    }
    @Test
    void test4ParamOneWorldWorld() throws FileNotFoundException {
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\helloWorld.txt", "world", "5");
        assertEquals("[world, world, world, world, world]", func.fourthArgumentOne().toString());
    }
    @Test
    void test4ParamOneABC() throws FileNotFoundException {
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\abc.txt", "a", "4");
        assertEquals("[a, b, a, b]", func.fourthArgumentOne().toString());
    }
    @Test
    void test4ParamABCF() throws FileNotFoundException{
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\abc.txt", "f", "4");
        assertEquals("[f, a, b, a]", func.fourthArgumentOne().toString());
    }
    @Test
    void test4ParamABCZ() throws FileNotFoundException{
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\abc.txt", "z", "4");
        assertEquals("[z, z, z, z]", func.fourthArgumentOne().toString());
    }
    @Test
    void test4ParamOneTrickyFormatting() throws FileNotFoundException {
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\trickyFormatting.txt", "has", "10");
        assertEquals("[has, a, few, them, punctuation, or, fomatting, things, yup, has]", func.fourthArgumentOne().toString());
    }
    @Test
    void test4ParamOneTrickyFormattingFile() throws FileNotFoundException{
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\trickyFormatting.txt", "file", "4");
        assertEquals("[file, iirc, has, a]", func.fourthArgumentOne().toString());
    }
    @Test
    void test4ParamOneSeedAfterSeed() throws FileNotFoundException{
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\SeedAfterSeed", "hi", "5");
        assertEquals("[hi, hi, hi, hi, hi]", func.fourthArgumentOne().toString());
    }

    @Test
    void testSeedAfterSeeDifferentWord() throws FileNotFoundException {
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\SeedAfterSeed", "bye", "4");
        assertEquals("[bye, hello, bye, hello]", func.fourthArgumentOne().toString());
    }

    @Test
    void testHelloWorldAll() throws FileNotFoundException {
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\helloWorld.txt", "hello", "4");
        assertEquals("[hello, world, hello, world]", func.fourthArgumentAll().toString());
    }

    @Test
    void testABCAll() throws FileNotFoundException {
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\abc.txt", "g", "5");
        System.out.println(func.fourthArgumentAll().toString());
    }

    @Test
    void testTrickyFormattingAll() throws FileNotFoundException{
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\trickyFormatting.txt", "has", "9");
        assertEquals("[has, a, few, them, punctuation, or, fomatting, things, yup]", func.fourthArgumentAll().toString());
    }
    @Test
    void testSeedAfterSeedAll() throws FileNotFoundException{
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\SeedAfterSeed", "hi", "6");
        System.out.println(func.fourthArgumentAll().toString());
    }

    @Test
    void testMobyDick() throws FileNotFoundException {
        TextGeneratorFunctor func = new TextGeneratorFunctor("C:\\Users\\u1433029\\IdeaProjects\\CS 2420\\src\\comprehensive\\MobyDick.txt", "swallowed", "10");
        System.out.println(func.fourthArgumentAll().toString());
    }
}
