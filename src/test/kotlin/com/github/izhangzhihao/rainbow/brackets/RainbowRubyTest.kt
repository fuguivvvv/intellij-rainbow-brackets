package com.github.izhangzhihao.rainbow.brackets

import com.intellij.psi.PsiDocumentManager
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import io.kotlintest.matchers.shouldBe
import org.jetbrains.plugins.ruby.ruby.lang.RubyFileType

class RainbowRubyTest : LightCodeInsightFixtureTestCase() {
    fun testRainbowForRuby() {
        val code =
                """
class Test
  a = (1 + 2 + (3))
end
                """.trimIndent()
        myFixture.configureByText(RubyFileType.RUBY, code)
        PsiDocumentManager.getInstance(project).commitAllDocuments()
        val doHighlighting = myFixture.doHighlighting()
        assertFalse(doHighlighting.isEmpty())
        doHighlighting.filter { brackets.contains(it.text.toChar()) }
                .map { it.forcedTextAttributes.foregroundColor }
                .toTypedArray()
                .shouldBe(
                        arrayOf(
                                roundLevel(0),
                                roundLevel(1),
                                roundLevel(1),
                                roundLevel(0)
                        )
                )
    }

    fun `testRainbowFor#53`() {
        val code =
                """
foobar(p1: "", p2: false, p3: 1)
                """.trimIndent()
        myFixture.configureByText(RubyFileType.RUBY, code)
        PsiDocumentManager.getInstance(project).commitAllDocuments()
        val doHighlighting = myFixture.doHighlighting()
        assertFalse(doHighlighting.isEmpty())
        doHighlighting.filter { brackets.contains(it.text.toChar()) }
                .map { it.forcedTextAttributes.foregroundColor }
                .toTypedArray()
                .shouldBe(
                        arrayOf(
                                roundLevel(0),
                                roundLevel(0)
                        )
                )
    }
}