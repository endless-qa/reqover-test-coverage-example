import Constants.HOLIDAYS_ENDPOINT
import Constants.PROVINCES_ENDPOINT
import Constants.SELF_ENDPOINT
import Constants.SPEC_ENDPOINT
import Constants.WELCOME_MESSAGE
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.apache.http.HttpStatus

class CanadaHolidaysTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val specification = RestClient.createBaseSpecification()

    val provincesIds = listOf("AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT")

    describe("When user requests a province by ID") {
        provincesIds.forEach { id ->
            val response = specification.get("/provinces/$id")

            it("should return the $id province with associated holidays of the current year") {
                response.statusCode.shouldBe(HttpStatus.SC_OK)
                response.shouldContainPathWithValue("province.id", id)
                response.shouldNotHaveEmptyList("province.holidays")
                // extra assertions for the holidays in the province
            }
        }
    }

    describe("When user requests a province by unknown ID") {
        it("should return 400 response code") {
            specification.get("/provinces/XYZ").statusCode.shouldBe(HttpStatus.SC_BAD_REQUEST)
        }
    }

    describe("When user fetches general information") {
        val response = specification.get()
        it("should return welcome message and available links") {
            response.statusCode.shouldBe(HttpStatus.SC_OK)
            response.shouldContainPathWithValue("message", WELCOME_MESSAGE)
            response.shouldContainPathWithValue("_links.self.href", SELF_ENDPOINT)
            response.shouldContainPathWithValue("_links.holidays.href", HOLIDAYS_ENDPOINT)
            response.shouldContainPathWithValue("_links.provinces.href", PROVINCES_ENDPOINT)
            response.shouldContainPathWithValue("_links.spec.href", SPEC_ENDPOINT)
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
})