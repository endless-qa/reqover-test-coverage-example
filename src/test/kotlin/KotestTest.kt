import Constants.SELF_ENDPOINT
import Constants.WELCOME_MESSAGE
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.apache.http.HttpStatus

class KotestTest : DescribeSpec({

    isolationMode = IsolationMode.InstancePerLeaf
    val provincesIds = listOf("AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT")
    val specification = RestClient.createBaseSpecification()

    describe("When user fetches general information") {
        val response = specification.get()
        it("should return welcome message and available links") {
            response.statusCode.shouldBe(HttpStatus.SC_OK)
            response.shouldContainPathWithValue("message", WELCOME_MESSAGE)
            response.shouldContainPathWithValue("_links.self.href", SELF_ENDPOINT)
        }
    }

    describe("When user fetches the schema") {
        it("should return the schema as a YAML file") {
            specification.get("/spec").statusCode.shouldBe(HttpStatus.SC_OK)
        }
    }

    describe("When user requests all holidays") {
        it("should return the holidays of the current year") {
            specification.get("/holidays").statusCode.shouldBe(HttpStatus.SC_OK)
        }
    }

    describe("When user requests a holiday by ID") {
        it("should return the requested holiday for the current year") {
            specification.get("/holidays/1").statusCode.shouldBe(HttpStatus.SC_OK)
        }
    }

    describe("When user requests all provinces") {
        it("should return the provinces with associated holidays of the current year") {
            specification.get("/provinces").statusCode.shouldBe(HttpStatus.SC_OK)
        }
    }

    describe("When user requests a province by ID") {
        provincesIds.forEach { id ->
            it("should return the $id province with associated holidays of the current year") {
                specification.get("/provinces/$id").statusCode.shouldBe(HttpStatus.SC_OK)
            }
        }
    }

    describe("When user requests a province by unknown ID") {
        it("should return 404 response code") {
            specification.get("/provinces/XYZ").statusCode.shouldBe(HttpStatus.SC_BAD_REQUEST)
        }
    }
})