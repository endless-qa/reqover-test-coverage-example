import Constants.BASE_PATH
import Constants.BASE_URI
import io.reqover.rest.assured.SwaggerCoverage
import io.restassured.RestAssured
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.specification.RequestSpecification

object RestClient {
    fun createBaseSpecification(): RequestSpecification {
        RestAssured.baseURI = BASE_URI
        RestAssured.basePath = BASE_PATH

        return RestAssured.given().filters(RequestLoggingFilter(), ResponseLoggingFilter(), SwaggerCoverage())
    }
}