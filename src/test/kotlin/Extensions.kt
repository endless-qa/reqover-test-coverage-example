import io.kotest.assertions.json.shouldContainJsonKeyValue
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.restassured.response.Response

/**
 * Asserts that a specific path with a specific value is present in the response body
 * @param path a desired JSON path to look for
 * @param value a specific value o path
 */
fun Response.shouldContainPathWithValue(path: String, value: String) {
    this.body.asString().shouldContainJsonKeyValue(path, value)
}

/**
 * Asserts that the list by the specified path is not empty
 * @param path a desired JSON path to look for a list
 */
fun Response.shouldNotHaveEmptyList(path: String) {
    this.jsonPath().getList<String>(path).shouldNotBeEmpty()
}

/**
 * Asserts that the list by the specified path has a specific size
 * @param path a desired JSON path to look for a list
 * @param expectedSize the expected size of the list
 */
fun Response.shouldHaveListWithSize(path: String, expectedSize: Int) {
    this.jsonPath().getList<String>(path).size.shouldBe(expectedSize)
}