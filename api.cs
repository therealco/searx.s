using System.Collections.Generic;
using System.Web.Http;

public class SearchController : ApiController
{
    [HttpGet]
    [Route("api/search")]
    public IHttpActionResult Search(string query)
    {
        // Call the Java search engine and get results
        var results = CallJavaSearchEngine(query);
        return Ok(results);
    }

    private List<string> CallJavaSearchEngine(string query)
    {
        // Here you would call the Java backend (e.g., through an HTTP request or other IPC mechanism)
        // For simplicity, we're returning dummy data
        return new List<string> { "Result 1", "Result 2", "Result 3" };
    }
}
