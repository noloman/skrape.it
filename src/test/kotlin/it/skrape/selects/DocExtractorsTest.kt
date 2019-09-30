package it.skrape.selects

import assertk.assertThat
import assertk.assertions.isEqualTo
import it.skrape.exceptions.DivNotFoundException
import it.skrape.expect
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class DocExtractorsTest {

    @Test
    internal fun `can read div from document`() {
        expect("<html><div>divs inner text</div></html>") {
            div {
                assertThat(text()).isEqualTo("divs inner text")
            }
        }
    }

    @Test
    internal fun `can read div with selector from document`() {
        expect("<html><div class=\"existent\">divs inner text</div></html>") {
            div(".existent") {
                assertThat(text()).isEqualTo("divs inner text")
            }
        }
    }

    @Test
    internal fun `will throw custom exception if div could not be found via lambda`() {
        Assertions.assertThrows(DivNotFoundException::class.java) {
            expect("") {
                div(".nonExistent") {}
            }

        }
    }

    @Test
    internal fun `can read divs from document`() {
        expect("<html><div>first</div><div>second</div></html>") {
            divs {
                assertThat(size).isEqualTo(2)
                assertThat(get(0).text()).isEqualTo("first")
                assertThat(get(1).text()).isEqualTo("second")
            }
        }
    }

    @Test
    internal fun `can read divs with selector from document`() {
        expect("<html><div class=\"foo\">with class</div><div class=\"foo\">with class</div><div>without class</div></html>") {
            divs(".foo") {
                assertThat(size).isEqualTo(2)
                forEach {
                    assertThat(it.text()).isEqualTo("with class")
                }
            }
        }
    }

    @Test
    internal fun `will throw custom exception if divs could not be found via lambda`() {
        Assertions.assertThrows(DivNotFoundException::class.java) {
            expect("") {
                divs {}
            }

        }
    }
}
