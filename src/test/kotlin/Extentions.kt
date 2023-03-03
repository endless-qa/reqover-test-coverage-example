import io.kotest.assertions.json.shouldContainJsonKeyValue
import io.restassured.response.Response

fun Response.shouldContainPathWithValue(path: String, value: String) {
    this.body.asString().shouldContainJsonKeyValue(path, value)
}