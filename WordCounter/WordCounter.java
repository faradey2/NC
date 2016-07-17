import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public interface WordCounter
{
    /**
     * Принимает текст для анализа
     * @param text текст для анализа
     */
    void setText (String text);
    /**
     * @return текст, переданный для анализа при последнем вызове метода
     * {@link #setText(java.lang.String) setText}, или <code>null</code>,
     * если указанный метод еще не вызывался или последний раз вызывался
     * с параметром <code>null</code>
     */
    String getText ();
    /**
     * Возвращает {@link Map}&lt;{@link String}, {@link Long}&gt;,
     * сопоставляющую каждому слову количество его вхождений в анализируемый текст.
     * Все возвращаемые слова должны быть приведены к нижнему регистру.
     * @return результат подсчета количеств вхождений слов
     * @throws IllegalStateException если не задан текст для анализа
     * (метод {@link #setText(java.lang.String) setText} еще не вызывался
     * или последний раз вызывался с параметром <code>null</code>)
     */
    Map<String, Long> getWordCounts() throws IllegalStateException;
    /**
     * Возвращает список из {@link Entry Map.Entry}&lt;{@link String}, {@link Long}&gt;,
     * сопоставляющий каждому слову количество его вхождений в анализируемый текст
     * и упорядоченный в прядке убывания количества вхождений слова.<br/>
     * Слова с одинаковым количеством вхождений упорядочиваются в алфавитном порядке (без учета регистра!).<br/>
     * Все возвращаемые слова должны быть приведены к нижнему регистру.
     * @return упорядоченный результат подсчета количеств вхождений слов
     * @throws IllegalStateException если не задан текст для анализа
     * (метод {@link #setText(java.lang.String) setText} еще не вызывался
     * или последний раз вызывался с параметром <code>null</code>)
     */
    List<Map.Entry<String, Long>> getWordCountsSorted() throws IllegalStateException;
    /**
     * Упорядочивает результат подсчета количеств вхождений слов в порядке убывания количества вхождений.<br/>
     * Слова с одинаковым количеством вхождений упорядочиваются в алфавитном порядке (без учета регистра!).<br/>
     * Реализация этого метода не является обязательной, но если он будет реализован, то реализация
     *  метода {@link #getWordCountsSorted()} будет тривиальной.
     * @param orig неупорядоченный результат подсчета
     * @return упорядоченный результат подсчета или <code>null</code>, если <code>orig == null</code>
     */
    List<Map.Entry<String, Long>> sortWordCounts (Map<String, Long> orig);
}
