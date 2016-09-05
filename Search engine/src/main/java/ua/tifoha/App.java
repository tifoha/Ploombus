package ua.tifoha;

//import jdk.internal.org.objectweb.asm.tree.analysis.Analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Vitaly on 03.09.2016.
 */
public class App {
    public static void main(String[] args) throws IOException, ParseException {
        Analyzer analyzer = new StandardAnalyzer();
//        Directory directory = new RAMDirectory(new SingleInstanceLockFactory());
        Directory directory = FSDirectory.open(Paths.get("/tmp/testindex"));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iWriter = new IndexWriter(directory, config);

        Document doc = new Document();
        doc.add(new Field("title", "Покерный софт - Покер Вики", TextField.TYPE_STORED));
        doc.add(new Field("body", text, TextField.TYPE_STORED));
        iWriter.addDocument(doc);
        iWriter.close();

        DirectoryReader iReader = DirectoryReader.open(directory);
        IndexSearcher iSearcher = new IndexSearcher(iReader);
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser("body", analyzer);
        Query query = parser.parse("разд~");
        ScoreDoc[] hits = iSearcher.search(query, 1000).scoreDocs;

        for (ScoreDoc hit :hits) {
            Document document = iSearcher.doc(hit.doc);
            System.out.println(document);
        }
        iReader.close();
        directory.close();

    }

    public static String text = "\n" +
            "Покерный софт\n" +
            "Материал из Poker-wiki.ru, свободной энциклопедии по покеру.\n" +
            "\n" +
            "Покерный софт - программное обеспечение, позволяющее играть в покер через сеть Интернет с людьми со всего мира. По сути, это программа, скачиваемая игроками, которая является клиентской частью системы, обеспечивающей игру в онлайн покер. Именно клиентскую часть и принято называть покерным софтом.\n" +
            "В каждом покерном софте присутствуют, такие составные части, как Лобби, Кассир и Игровой (виртуальный) стол.\n" +
            "\n" +
            "Лобби\n" +
            "\n" +
            "\n" +
            "\n" +
            "Лобби PokerStars\n" +
            "PS120x60.gif\n" +
            "Лобби (англ. \"Lobby\" - дословно, \"Вестибюль, приемная\") - главное меню покерного клиента. Обычно большую часть лобби занимает список столов, отобранных по установленному фильтру. Также в лобби находится информация о последних событиях покер рума и основное меню, содержащее настройки и функции доступа к кассиру.\n" +
            "Чтобы не просматривать список всех столов покер рума в поисках столов с необходимыми параметрами, были введены специальные фильтры. Сначала выбирается вид покера (Холдем, Омаха и т.п.), затем его тип (Limit, No-Limit) и лимит ставок. После чего, список столов можно отсортировать по таким показателям, как количество игроков, процент игроков на флопе, размер среднего пота, количество рук в час и некоторым другим.\n" +
            "Для того чтобы сесть за игровой стол, достаточно просто кликнуть по нему. Если свободных мест за столом нет, игроку предлагается занять место в очереди за этот стол (Wait list).\n" +
            "\n" +
            "Кассир\n" +
            "\n" +
            "В Кассире (англ. Cashier) находятся основные функции управления покерным счетом и очками игрока. Здесь можно сделать депозит, вывести деньги, просмотреть историю транзакций. В кассире отображается текущий баланс реальных/виртуальных денег и набранных очков. Иногда кассир покер рума выведен на защищенные страницы сайта, но всё больше и больше покер румов отказываются от этого, предпочитая размещать кассир внутри покерного клиента.\n" +
            "Strategybig-adv.gif\n" +
            "Игровой стол\n" +
            "\n" +
            "Игровой стол является виртуальным покерным столом, за которым идет игра в покер между игроками, подключившимся к нему. Главное преимущество виртуальных покерных столов в том, что есть возможность открыть несколько столов сразу, таким образом, повышая сыгранное за одинаковое время количество рук, данная функция была названа термином мультитейблинг, а игроков, которые играют больше чем на одном столе, называют - мультитейблеры. При ходе игрока, ему предоставляются кнопки, позволяющие принять ставку, повысить ставку или сбросить карты, аналогично игровым ходам в оффлайн покере. Кроме того, существует возможность указать свой ход заранее, установив галочку на нужное действие. В этом случае, когда ход дойдет до игрока, установившего свое действие заранее, оно будет сразу же совершено (если это возможно). Эта функция помогает при игре за множеством столов, определяя простые решения заранее.\n" +
            "Многие покер румы предоставляют широкий выбор \"скинов\" (оформлений) для своих столов. Во многих покерных клиентах размер окна с игровым столом может произвольно изменяться пользователем.\n" +
            "Категория: Покерный софт\n" +
            "Создать учётную записьПредставиться системеСтатьяОбсуждениеЧитатьПросмотрИстория\n" +
            "\n" +
            "Поиск\n" +
            "\t Перейти\t Найти\n" +
            "Навигация\n" +
            "Заглавная страница\n" +
            "Текущие события\n" +
            "Новые статьи\n" +
            "Свежие правки\n" +
            "Случайная статья\n" +
            "Все страницы\n" +
            "Справка\n" +
            "Инструменты\n" +
            "Ссылки сюда\n" +
            "Связанные правки\n" +
            "Спецстраницы\n" +
            "Версия для печати\n" +
            "Постоянная ссылка\n" +
            "Последнее изменение этой страницы: 14:56, 27 февраля 2016.\n" +
            "К этой странице обращались 6337 раз.\n" +
            "По вопросам сотрудничества обращайтесь на marketing@poker-wiki.ru.\n" +
            "Политика конфиденциальностиОписание Покер ВикиОтказ от ответственностиGPWA";
}


