import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private SearchEngine searchEngine;

    @GetMapping("/api/search")
    public List<String> search(@RequestParam String query) throws Exception {
        return searchEngine.search(query);
    }
}
