package com.hanna.clearscore.application.test.ui

import android.os.Build
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import at.grabner.circleprogress.CircleProgressView
import com.google.common.truth.Truth.assertThat
import com.hanna.clearscore.application.test.R
import com.hanna.clearscore.application.test.datasource.network.Resource
import com.hanna.clearscore.application.test.extensions.replace
import com.hanna.clearscore.application.test.models.CreditReportInfo
import com.hanna.clearscore.application.test.viewmodel.CreditScoreViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CreditScoreFragmentTest {

    lateinit var scenario: FragmentScenario<CreditScoreDisplayFragment>
    lateinit var factory: CreditScoreFragmentTestFactory

    private val creditScoreFlowValue =
        MutableStateFlow<Resource<CreditReportInfo>>(Resource.loading(null))
    private val viewModelMock: CreditScoreViewModel = mock {
        onBlocking { getCreditScore } doReturn creditScoreFlowValue
    }

    @Before
    fun setupScenario() {
        factory = CreditScoreFragmentTestFactory(viewModelMock)
        scenario = FragmentScenario.launchInContainer(
            CreditScoreDisplayFragment::class.java,
            null, R.style.Theme_AppCompat_NoActionBar, factory
        )
    }

    @Test
    fun `when fragment displayed, viewModel calls getCreditScore()`() {
        scenario.onFragment {
            verify(viewModelMock).getCreditScore
        }
    }

    @Test
    fun `when getCreditScore returns loading resource, donut view is 0 percent`() {
        creditScoreFlowValue.value = Resource.loading(null)
        scenario.onFragment {
            val pb = it.view!!.findViewById<CircleProgressView>(R.id.progress_circular)
            assertThat(pb.currentValue).isEqualTo(0)
        }
    }

    @Test
    fun `when getCreditScore returns loading resource, loading text is displayed`() {
        creditScoreFlowValue.value = Resource.loading(null)
        scenario.onFragment {
            val scoreTv = it.view!!.findViewById<TextView>(R.id.score_tv)
            assertThat(scoreTv.text).isEqualTo("Loading…")
        }
    }

    @Test
    fun `when getCreditScore returns loading resource, credit score data is invisible`() {
        creditScoreFlowValue.value = Resource.loading(null)
        scenario.onFragment {
            val outOfTv = it.view!!.findViewById<TextView>(R.id.credit_score_out_of_tv)
            val titleTv = it.view!!.findViewById<TextView>(R.id.credit_score_title)
            assertThat(outOfTv.isVisible).isFalse()
            assertThat(titleTv.isVisible).isFalse()
        }
    }

    @Test
    fun `when getCreditScore returns success resource, with data, credit score data is visible`() {
        creditScoreFlowValue.value = Resource.loading(null)
        scenario.onFragment {
            val outOfTv = it.view!!.findViewById<TextView>(R.id.credit_score_out_of_tv)
            val titleTv = it.view!!.findViewById<TextView>(R.id.credit_score_title)
            assertThat(outOfTv.isVisible).isFalse()
            assertThat(titleTv.isVisible).isFalse()
        }
    }

    @Test
    fun `when getCreditScore returns success resource, with data, score is displayed`() {
        val data = CreditReportInfo(10, 700, 44)
        creditScoreFlowValue.value = Resource.success(data)
        scenario.onFragment {
            val scoreTv = it.view!!.findViewById<TextView>(R.id.score_tv)
            assertThat(scoreTv.text).isEqualTo(data.score.toString())
        }
    }

    @Test
    fun `when getCreditScore returns success resource, with data, max score is reflected in out of score text`() {
        val data = CreditReportInfo(10, 700, 44)
        creditScoreFlowValue.value = Resource.success(data)
        scenario.onFragment {
            val outOfTv = it.view!!.findViewById<TextView>(R.id.credit_score_out_of_tv)
            assertThat(outOfTv.text).isEqualTo("out of 700")
        }
    }

    @Test
    fun `when getCreditScore returns error resource, with data, max score is reflected in out of score text`() {
        val data = CreditReportInfo(10, 700, 44)
        creditScoreFlowValue.value = Resource.error("This is an error", data)
        scenario.onFragment {
            assertDisplayed("This is an error")
        }
    }
}

class CreditScoreFragmentTestFactory constructor(
    var viewModelMock: CreditScoreViewModel
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
        CreditScoreDisplayFragment().apply {
            replace(CreditScoreDisplayFragment::viewModel, viewModelMock)
        }
}