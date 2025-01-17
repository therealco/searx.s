
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchEngine {
    private Directory index = new RAMDirectory();

    @PostConstruct
    public void initialize() throws Exception {
        indexDocument("Hello world");
        indexDocument("Hello Lucene");
    }

    public void indexDocument(String text) throws Exception {
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter writer = new IndexWriter(index, config);
        Document doc = new Document();
        doc.add(new TextField("content", text, Field.Store.YES));
        writer.addDocument(doc);
        writer.close();
    }

    public List<String> search(String queryString) throws Exception {
        Query query = new QueryParser("content", new StandardAnalyzer()).parse(queryString);
        DirectoryReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        ScoreDoc[] hits = searcher.search(query, 10).scoreDocs;

        List<String> results = new ArrayList<>();
        for (ScoreDoc hit : hits) {
            Document doc = searcher.doc(hit.doc);
            results.add(doc.get("content"));
        }
        reader.close();
        return results;
    }
}
